package kz.elminpro.miniscada002;

import android.view.View;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by  on 03.08.2015.
 */
public class Project {

    private String macAddress = null;
    private ArrayList<View> views;
    private InputStream inStream;
    private OutputStream outStream;

    public Project(String macAddress) {
        this.macAddress = macAddress;
    }

    public Project() {
    }

    public InputStream getInStream() {
        return inStream;
    }

    public void setInStream(InputStream inStream) {
        this.inStream = inStream;
    }

    public OutputStream getOutStream() {
        return outStream;
    }

    public void setOutStream(OutputStream outStream) {
        this.outStream = outStream;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public ArrayList<View> getViews() {
        return views;
    }

    public void setViews(ArrayList<View> views) {
        this.views = views;
    }
}
