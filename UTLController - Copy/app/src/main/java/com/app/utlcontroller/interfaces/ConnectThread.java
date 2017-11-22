package com.app.utlcontroller.interfaces;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.IOException;
import java.util.UUID;

/**
 * Created by m on 5/10/2016.
 */
class ConnectThread extends Thread {
    private final BluetoothSocket mmSocket;
    private final BluetoothDevice mmDevice;
    BluetoothAdapter mBluetoothAdapter;

    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    public ConnectThread(BluetoothDevice device) {
        // Use a temporary object that is later assigned to mmSocket,
        // because mmSocket is final
        BluetoothSocket tmp = null;
        mmDevice = device;
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        // Get a BluetoothSocket to connect with the given BluetoothDevice
        try {
            // MY_UUID is the app's UUID string, also used by the server code
            tmp = device.createInsecureRfcommSocketToServiceRecord(MY_UUID_SECURE);
            System.out.println("tmp =+ " + tmp);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("tmp =_ " + "connected eror");
        }
        mmSocket = tmp;
    }

    public void run() {
        // Cancel discovery because it will slow down the connection
        mBluetoothAdapter.cancelDiscovery();

        try {
            // Connect the device through the socket. This will block
            // until it succeeds or throws an exception
            mmSocket.connect();
            System.out.println("mmSocket = " + "Socket connected");

            ConnectedThread mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();

            byte[] myvar = "PARMITKUMAR11@".getBytes();

            /*final byte[] CDRIVES = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
                    0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
                    0x30, 0x30, (byte)0x9d };*/

            mConnectedThread.write(myvar);
        } catch (IOException connectException) {
            // Unable to connect; close the socket and get out
            System.out.println("mmSocket = " + "Socket Unable to connected");
            try {
                mmSocket.close();
            } catch (IOException closeException) {
            }
            return;
        }

        // Do work to manage the connection (in a separate thread)
        //manageConnectedSocket(mmSocket);
    }

    /**
     * Will cancel an in-progress connection, and close the socket
     */
    public void cancel() {
        try {
            mmSocket.close();
        } catch (IOException e) {
        }
    }
}
