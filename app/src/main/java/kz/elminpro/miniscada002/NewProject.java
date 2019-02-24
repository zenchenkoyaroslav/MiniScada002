package kz.elminpro.miniscada002;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by mouse95 on 22.07.2015.
 */
public class NewProject extends Activity {

    ImageButton back, murS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newproj);

        back = (ImageButton)findViewById(R.id.back);
        murS = (ImageButton)findViewById(R.id.murS);
    }

    public void murS(View view)
    {
        Intent intent = new Intent(NewProject.this, Mur.class);
        startActivity(intent);
    }
}
