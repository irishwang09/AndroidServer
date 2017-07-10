package com.example.hwangir_local.androidserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.net.SocketException;
import java.net.DatagramPacket;
import java.net.*;
import android.util.Log;
import android.widget.TextView;
/**
 * Created by hwangir-local on 6/21/2017.
 */
//change
public class SocketServerUDPThread extends Thread {
    TextView IPinfo;
    TextView message;
    boolean runWhileLoop;
    int port = 8080;
    byte[] buffer;
    DatagramPacket packet;
    DatagramSocket socket;
    String recievedData;
    MainActivity mainActivity;
    File file;
    private static final String fileName = "C:\\Users\\irish\\OneDrive\\Documents\\UROP\\collectData.txt";

    public SocketServerUDPThread(TextView ipinfo, TextView msg, MainActivity ma, File f) {
        IPinfo = ipinfo;
        message = msg;
        runWhileLoop = true;
        buffer = new byte[2048];
        packet = new DatagramPacket(buffer, buffer.length);
        mainActivity = ma;
        file = f;
    }

    public void run() {
        IPinfo.setText("I'm waiting here: 192.168.43.1");
        while (true) {
            try {
                socket = new DatagramSocket(port);
            } catch (SocketException e) {
                message.setText("EXCEPTION THROWN: could not create new DatagramSocket");
            }
            try {
                socket.receive(packet);
            } catch (IOException e) {
                recievedData = "EXCEPTION THROWN: could not receive packet";
            }
            recievedData = new String(buffer, 0, packet.getLength());
            //runWhileLoop = false;
            mainActivity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String ogText = message.getText().toString();
                    message.setText(ogText + " | " + recievedData);
                    Log.e("Data", recievedData);
                    //writeToFile(recievedData);
                }
            });
            socket.close();
        }
    }
}
