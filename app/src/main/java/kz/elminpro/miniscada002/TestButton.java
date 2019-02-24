package kz.elminpro.miniscada002;

import android.content.Context;
import android.support.v7.widget.AppCompatButton;

/**
 * Created by on 12.08.2015.
 */
public class TestButton extends AppCompatButton {

    private ModBusFunction modBusFunction = null;

    public TestButton(Context context) {
        super(context);
    }

    public ModBusFunction getModBusFunction() {
        return modBusFunction;
    }

    public void setModBusFunction(ModBusFunction modBusFunction) {
        this.modBusFunction = modBusFunction;
    }
}
