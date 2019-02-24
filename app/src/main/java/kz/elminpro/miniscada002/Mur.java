package kz.elminpro.miniscada002;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AbsoluteLayout;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by mouse95 on 28.07.2015.
 */
public class Mur extends Activity {

    String TAG = "Mur";

    private boolean moved = false;
    private int m_counter = 0;
    float m_lastTouchX, m_lastTouchY, m_posX, m_posY, m_prevX, m_prevY, m_imgXB, m_imgYB, m_imgXC, m_imgYC, m_dx, m_dy;
    int chkM;
    private LinearLayout m_llTop;
    private AbsoluteLayout m_alTop;
    private Button m_btnAddView, m_btnRemove, m_btn;
    private Context m_context;

    private Button connectButton;

    ArrayList<Integer> ids;


    public static Project project;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mur);

        project = new Project();
        ids = new ArrayList<>();

        connectButton = (Button) findViewById(R.id.btnConnect);

        chkM = 1;
        m_context = this;
        m_prevX = 0;
        m_prevY = 0;
        m_imgXB = 50;
        m_imgYB = 100;
        m_imgXC = 150;
        m_imgYC = 100;


        m_btn = (Button) findViewById(R.id.butt);
        m_llTop = (LinearLayout) findViewById(R.id.llTop);
        m_alTop = (AbsoluteLayout) findViewById(R.id.alTop);
        m_btnAddView = (Button) findViewById(R.id.btnAdd);
        m_btnRemove = (Button) findViewById(R.id.btnRemove);

        m_btnAddView.setOnClickListener(m_onClickListener);
        m_btnRemove.setOnClickListener(m_onClickListener);
        m_btn.setOnClickListener(m_onClickListener);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        for (int i : ids) {
            if (v.getId() == i) {
                menu.add("Working");
            }
        }
    }


    OnClickListener m_onClickListener = new OnClickListener() {

        @Override
        public void onClick(View p_v) {
            switch (p_v.getId()) {
                case R.id.btnAdd:
                    addView();
                    break;
                case R.id.btnRemove:
                    removeView();
                    break;
                default:
                    break;
            }
        }
    };

    OnTouchListener m_onTouchListener = new OnTouchListener() {

        private static final int CLICK_DURATION = 200;
        private static final int MOVE_DURATION = 300;
        private long startClickTime;
        Intent intent;

        @Override
        public boolean onTouch(View p_v, MotionEvent p_event) {
            switch (p_event.getAction()) {
                case MotionEvent.ACTION_DOWN: {
                    startClickTime = Calendar.getInstance().getTimeInMillis();
                    m_lastTouchX = p_event.getX();
                    m_lastTouchY = p_event.getY();
                    break;
                }
                case MotionEvent.ACTION_UP: {
                    /*
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                    if (clickDuration < CLICK_DURATION) {
                        if (chkM == 1) {
                            p_v.setBackground(Mur.this.getResources().getDrawable(R.drawable.vikl));
                            chkM = 0;
                        } else {
                            p_v.setBackground(Mur.this.getResources().getDrawable(R.drawable.vkl));
                            chkM = 1;
                        }
                    }
                    */
                    if (!moved) {
                        showPopupMenu(p_v);
                        break;
                    } else moved = false;
                }

                case MotionEvent.ACTION_MOVE: {
                    long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                    if (clickDuration > CLICK_DURATION) {
                        moved = true;
                        m_dx = p_event.getX() - m_lastTouchX;
                        m_dy = p_event.getY() - m_lastTouchY;

                        m_posX = m_prevX + m_dx;
                        m_posY = m_prevY + m_dy;

                        if (m_posX > 0 && m_posY > 0 && (m_posX + p_v.getWidth()) < m_alTop.getWidth() && (m_posY + p_v.getHeight()) < m_alTop.getHeight()) {
                            p_v.setLayoutParams(new LayoutParams(p_v.getMeasuredWidth(), p_v.getMeasuredHeight(), (int) m_posX, (int) m_posY));

                            m_prevX = m_posX;
                            m_prevY = m_posY;

                        }

                        break;
                    }
                }

            }
            return true;
        }

    };

    private void showPopupMenu(final View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.inflate(R.menu.popup_menu);
        TestButton button = (TestButton) v;

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.edit: {
                        LayoutInflater li = LayoutInflater.from(Mur.this);
                        View editView = li.inflate(R.layout.edit, null);

                        AlertDialog.Builder builder = new AlertDialog.Builder(Mur.this);
                        builder.setView(editView);
                        final EditText modbusInput = (EditText) editView.findViewById(R.id.edit_modbus);
                        builder
                                .setCancelable(true)
                                .setTitle("Modbus Code")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        ModBusFunction function = new ModBusFunction(modbusInput.getText().toString());
                                        ((TestButton) v).setModBusFunction(function);
                                    }
                                })
                                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return true;
                    }
                    case R.id.send: {
                        if (ConnectColl.bluetoothAdapter != null) {
                            if (((TestButton) v).getModBusFunction() != null) {
                                try {
                                    ReaderWriter.write(((TestButton) v).getModBusFunction().toString());
                                    ReaderWriter.read();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                            return true;
                        }
                    }
                    default:
                        return false;
                }
            }

            /*
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu1:
                        Toast.makeText(getApplicationContext(), "Меню первое", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu2:
                        Toast.makeText(getApplicationContext(), "Меню второе", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.menu3:
                        Toast.makeText(getApplicationContext(), "Меню третье", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        return false;
                }
            }*/
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {
                Toast.makeText(getApplicationContext(), "onDismiss", Toast.LENGTH_SHORT).show();
            }
        });
        popupMenu.show();
    }

    /**
     * Add view dynamically for drag and drop
     */
    private void addView() {
        final TestButton[] m_b = new TestButton[1];
        for (int i = 0; i < 1; i++) {
            m_b[i] = new TestButton(this);
            m_b[i].setBackground(getResources().getDrawable(R.drawable.vkl));
            m_b[i].setId(i);
            if (m_counter < 100) {
                m_alTop.addView(m_b[i], new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, ((int) m_imgXB), ((int) m_imgYB)));
                m_counter++;
                if (m_counter == 100)
                    m_btnAddView.setEnabled(false);
            }
            m_b[i].setOnTouchListener(m_onTouchListener);
            //m_b[i].setOnCreateContextMenuListener(Mur.this);

        }
    }


    public void removeView() {
        m_counter = 0;
        m_alTop.removeAllViews();
        m_alTop.invalidate();
        m_btnAddView.setEnabled(true);
    }


    public void onClickConnect(View view) {
        Intent intent = new Intent(this, ConnectActivity.class);
        startActivity(intent);
    }
}