package kz.elminpro.miniscada002;

import java.io.IOException;
import java.util.Formatter;

/**
 * Created by ������� on 03.08.2015.
 */
public class ReaderWriter {



    public static void write(String message) throws IOException {
        String TAG = "ReaderWriter";
        int len = message.length();
        byte[] msgBuffer = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            msgBuffer[i / 2] = (byte) ((Character.digit(message.charAt(i), 16) << 4)
                    + Character.digit(message.charAt(i + 1), 16));
        }

        ConnectColl.outStream.write(msgBuffer);
    }

    public static String read() throws IOException {
        byte [] msg = new byte[100];

        int numberOfBytes = ConnectColl.inStream.read(msg);
        byte[] bytes;
        bytes = new byte[numberOfBytes];
        for (int i = 0; i < numberOfBytes; i++) {
            bytes[i] = msg[i];
        }

        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }

        String res = formatter.toString();

        return res;
    }
}
