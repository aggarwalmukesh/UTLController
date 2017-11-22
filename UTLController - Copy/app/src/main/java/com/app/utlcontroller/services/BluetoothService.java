package com.app.utlcontroller.services;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.app.utlcontroller.Controller.Config;
import com.app.utlcontroller.interfaces.BluetoothCallback;
import com.app.utlcontroller.interfaces.ReportsCallback;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService extends Service {
    public static BluetoothService bluetooth;
    BluetoothCallback bluetoothCallback;
    ReportsCallback reportCallback;
    ConnectThread mConnectThread;
    private static final UUID MY_UUID_SECURE =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    public BluetoothService() {
        bluetooth=this;
    }

    @Override
    public void onCreate() {
        bluetooth=this;
        super.onCreate();
    }

    public ConnectedThread mConnectedThread;
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bluetooth=this;
        return START_STICKY;
    }

    public static  BluetoothService getInstance(){
        return  bluetooth;
    }

    public void attachActivity(BluetoothCallback bluetoothCallback){
        this.bluetoothCallback=bluetoothCallback;
    }

    public void addReportListener(ReportsCallback reportCallback){
        this.reportCallback=reportCallback;
    }
    public void connectBluetooth(BluetoothDevice device){
        mConnectThread = new ConnectThread(device);
        mConnectThread.start();
    }

    @Override
    public void onDestroy() {
        if(mConnectedThread!=null){
            mConnectedThread.cancel();
            mConnectedThread=null;
        }
        if(mConnectThread!=null) {
            // mConnectedThread.
            mConnectThread.cancel();
            mConnectThread=null;
        }
        bluetoothCallback=null;

        super.onDestroy();
    }

    class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;
        BluetoothAdapter mBluetoothAdapter;


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
                String deviceName=mmDevice.getName();
                if(bluetoothCallback!=null){
                    bluetoothCallback.getResult(deviceName);
                }
                Log.v("Device Name=",deviceName);

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

                mConnectedThread = new ConnectedThread(mmSocket);
                mConnectedThread.start();

                // byte[] myvar = "PARMITKUMAR11@".getBytes();

            /*final byte[] CDRIVES = new byte[] { (byte)0xe0, 0x4f, (byte)0xd0,
                    0x20, (byte)0xea, 0x3a, 0x69, 0x10, (byte)0xa2, (byte)0xd8, 0x08, 0x00, 0x2b,
                    0x30, 0x30, (byte)0x9d };*/

                //mConnectedThread.write(myvar);
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                System.out.println("mmSocket = " + "Socket Unable to connected");
                try {
                    mmSocket.close();
                } catch (IOException closeException) {
                }
                catch(Exception e){

                }
                return;
            }
            catch(Exception e){

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
            StringBuilder sb = new StringBuilder();
            int star=-1;

            String oldSwitch1Record="";
            String oldSwitch2Record="";


            // Keep listening to the InputStream until an exception occurs
            while (true) {
                try {
                    // Read from the InputStream
                    bytes = mmInStream.read(buffer);
                    byte[] readBuf = (byte[]) buffer;
                    String readMessage = new String(readBuf, 0, bytes);

                    if(readMessage.startsWith(Config.MONITORING_SDATA)){
                        star=1;
                        if(reportCallback!=null){
                            reportCallback.getResult(Config.RECEIVING_DATA);
                        }
                    }
                    if(readMessage.startsWith(Config.CONFIGURATION_SETTINGS_SDATA)){
                        star=1;
                    }
                    if(readMessage.startsWith(Config.EVENT_LOG_SDATA)){
                        star=1;
                    }

                    if(readMessage.startsWith(Config.STATUS_SDATA)){
                        star=1;
                    }

                    if(star>0){
                        sb.append(readMessage);
                    }

                    if(readMessage.contains(Config.MONITORING_EDATA)){
                        Log.v("Monitoring Logs=",sb+"");
                        //sb.append(readMessage);
                        star=-1;
                        if(reportCallback!=null){
                            reportCallback.getResult(sb.toString());
                        }
                        sb.delete(0,sb.length());
                    }else if(readMessage.contains(Config.STATUS_EDATA)){
                        Log.v("STATUS Logs=",sb+"");
                        sb.append(readMessage);
                        star=-1;
                        if(reportCallback!=null){
                            reportCallback.getResult(sb.toString()+"&");
                        }
                        sb.delete(0,sb.length());
                    }else if(readMessage.contains(Config.CONFIGURATION_SETTINGS_EDATA)){
                        sb.append(readMessage);
                        Log.v("Configuration Logs=",sb+"");
                        star=-1;

                        //sendBroadcast(new Intent("data").putExtra());
                        if(reportCallback!=null){
                            reportCallback.getResult(sb.toString());
                        }
                        sb.delete(0,sb.length());
                    }
                    else if(readMessage.contains(Config.EVENT_LOG_EDATA)){
                        Log.v("EVENT Logs=",sb+"");
                        sb.append(readMessage);
                        star=-1;
                        if(reportCallback!=null){
                            reportCallback.getResult(sb.toString());
                        }
                        sb.delete(0,sb.length());
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                    mConnectedThread.cancel();
                    break;
                    // break;
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
}
