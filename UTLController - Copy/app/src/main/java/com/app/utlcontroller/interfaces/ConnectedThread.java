package com.app.utlcontroller.interfaces;

import android.bluetooth.BluetoothSocket;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by m on 5/18/2016.
 */
public class ConnectedThread extends Thread {

    private final BluetoothSocket mmSocket;
    private final InputStream mmInStream;
    private final OutputStream mmOutStream;


    public ConnectedThread(BluetoothSocket socket) {
        mmSocket = socket;
        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final
        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
        }

        mmInStream = tmpIn;
        mmOutStream = tmpOut;
    }

    public void run() {
        byte[] buffer = new byte[1024];  // buffer store for the stream
        int bytes; // bytes returned from read()

        // Keep listening to the InputStream until an exception occurs
        while (true) {
            try {
                // Read from the InputStream
                bytes = mmInStream.read(buffer);
                // Send the obtained bytes to the UI activity
                Log.v("Stream Received=",bytes+""+mmSocket.getRemoteDevice());
            } catch (IOException e) {
                break;
            }
        }
    }

    public void write(byte[] bytes) {
        try {
            mmOutStream.write(bytes);
            System.out.println("write " + bytes);
        } catch (IOException e) {
            System.out.println("e = " + "Exception in write bytes");
        }
    }

    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }


}
