package kz.elminpro.miniscada002;

import android.bluetooth.BluetoothAdapter;

import java.io.InputStream;
import java.io.OutputStream;


public class ConnectColl {
    public static BluetoothAdapter bluetoothAdapter = null;
    public static String MacAddress = null;
    public static OutputStream outStream = null;
    public static InputStream inStream = null;

}
