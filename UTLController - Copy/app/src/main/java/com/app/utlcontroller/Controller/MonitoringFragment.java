package com.app.utlcontroller.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.app.utlcontroller.Adapter.DisplayAdapter;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.R;
import com.app.utlcontroller.interfaces.ReportsCallback;
import com.app.utlcontroller.services.BluetoothService;

import java.util.ArrayList;
import java.util.Arrays;

public class MonitoringFragment extends Fragment implements View.OnTouchListener {

    ArrayList<ModelData> listBattery = new ArrayList<>();
    ArrayList<ModelData> listInput = new ArrayList<>();
    ArrayList<ModelData> listInverter = new ArrayList<>();
    ArrayList<ModelData> listByPass = new ArrayList<>();
    ArrayList<ModelData> listLoad = new ArrayList<>();
    ArrayList<ModelData> listSystem = new ArrayList<>();
    ListView listViewBattery, listViewInput, listViewInverter, listViewByPass, listViewLoad, listViewSystem;
    private String LOGTAG = MonitoringFragment.class.getCanonicalName();
/*

##Model,Name,Loc,SN,R,R,R,Vp1,Cp1,FP1,VP2,CP2,FP2,VP3,CP3,FP3,IVA,IPW,ICon,R,R,R,BV,BC,ELT,RAH,SOC,AHR,BTemp,Date,Con,R,R,R,
R,InV,InC,InF,InVA,INPW,InPF,Sync,R,R,R,Volt,Freq,ContBYPSC R,MBBSC1,MBBSC2,R,R,LS,LV,LC,LF,LO,PFR,R,R,R,R,R,R,R,R,R,R,R,R,R,R,R@@
 */

    String[] defaultData;
    private Config config;
    View layoutInput,layoutBattery,layoutInverter,layoutByPass,layoutLoad,layoutSystem;
    private ArrayList<ModelEvents> listEvent=new ArrayList<>();
    private boolean mBooleanIsPressed;
    Handler handler=new Handler();
    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if(mode.equalsIgnoreCase("INPUT")){
                showInputDialog();
            }else if(mode.equalsIgnoreCase("BATTERY")){
                config.showReadingDialog(getActivity(),"BATTERY",listBattery,false);
            }else if(mode.equalsIgnoreCase("INVERTER")){
                config.showReadingDialog(getActivity(),"INVERTER",listInverter,false);
            }else if(mode.equalsIgnoreCase("SYSTEM")){
                config.showReadingDialog(getActivity(),"SYSTEM",listSystem,false);
            }else if(mode.equalsIgnoreCase("LOAD")){
                config.showReadingDialog(getActivity(),"LOAD",listLoad,false);
            }else if(mode.equalsIgnoreCase("BYPASS")){
                config.showReadingDialog(getActivity(),"BYPASS",listByPass,false);
            }
            mode="";
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_monitoring, null);
        listViewBattery = (ListView) v.findViewById(R.id.listBattery);
        listViewInput = (ListView) v.findViewById(R.id.listInput);
        listViewInverter = (ListView) v.findViewById(R.id.listInverter);
        listViewByPass = (ListView) v.findViewById(R.id.listByPass);
        listViewLoad = (ListView) v.findViewById(R.id.listLoad);
        listViewSystem = (ListView) v.findViewById(R.id.listSystem);
        layoutInput = v.findViewById(R.id.layoutInput);
        layoutBattery = v.findViewById(R.id.layoutBattery);
        layoutInverter = v.findViewById(R.id.layoutInverter);
        layoutByPass = v.findViewById(R.id.layoutByPass);
        layoutLoad = v.findViewById(R.id.layoutLoad);
        layoutSystem = v.findViewById(R.id.layoutSystem);
        config=new Config();

        applyListeners();
        /*BluetoothService.getInstance().addReportListener(new ReportsCallback() {
            @Override
            public void getResult(String reports) {
                Log.v(LOGTAG,reports);
            }
        });*/
        defaultData = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, ((MainActivity) getActivity()).config.defaultMontoringData).split(",");
        displayData();
        return v;
    }

    private void applyListeners() {
        /*listViewInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showInputDialog();
                //config.showReadingDialog(getActivity(),"INPUT",listInput);
                return false;
            }
        });

        listViewBattery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"BATTERY",listBattery);
                return false;
            }
        });

        listViewInverter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"INVERTER",listInverter);
                return false;
            }
        });

        listViewByPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"BYPASS",listByPass);
                return false;
            }
        });

        listViewLoad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"LOAD",listLoad);
                return false;
            }
        });

        layoutInput.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                showInputDialog();
                //config.showReadingDialog(getActivity(),"INPUT",listInput);
                return false;
            }
        });

        layoutBattery.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"BATTERY",listBattery);
                return false;
            }
        });

        layoutInverter.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"INVERTER",listInverter);
                return false;
            }
        });

        layoutByPass.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"BYPASS",listByPass);
                return false;
            }
        });

        layoutLoad.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"LOAD",listLoad);
                return false;
            }
        });

        layoutSystem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"SYSTEM",listSystem);
                return false;
            }
        });

        listViewSystem.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                config.showReadingDialog(getActivity(),"SYSTEM",listSystem);
                return false;
            }
        });
*/

        listViewInput.setOnTouchListener(this);

        listViewBattery.setOnTouchListener(this);

        listViewInverter.setOnTouchListener(this);

        listViewByPass.setOnTouchListener(this);

        listViewLoad.setOnTouchListener(this);

        layoutInput.setOnTouchListener(this);

        layoutBattery.setOnTouchListener(this);

        layoutInverter.setOnTouchListener(this);

        layoutByPass.setOnTouchListener(this);

        layoutLoad.setOnTouchListener(this);

        layoutSystem.setOnTouchListener(this);

        listViewSystem.setOnTouchListener(this);

    }

    private void showInputDialog() {
        listEvent.clear();
        Log.v("Update Monitoring", "Input ");
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"", "PHASE-1", "PHASE-2", "PHASE-3", "UNIT"})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Voltage", defaultData[Config.Mon_InputVPhase1], defaultData[Config.Mon_InputVPhase2], defaultData[Config.Mon_InputVPhase3], "Vac"})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Current", defaultData[Config.Mon_InputCPhase1], defaultData[Config.Mon_InputCPhase2], defaultData[Config.Mon_InputCPhase3], "Amps"})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Frequency", defaultData[Config.Mon_InputFPhase1], defaultData[Config.Mon_InputFPhase2], defaultData[Config.Mon_InputFPhase3], "Hz"})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"VA", ""})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Power", defaultData[Config.Mon_InputPower] + " Watt"})));
        listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Breaker", defaultData[Config.Mon_InputContactor].contains("1") ? "Close" : "Open"})));
        config.updateReadingDialog_RecyclerView(getActivity(), "INPUT", listEvent);
    }

    private void prepareBatteryReadings() {
        listBattery.clear();
        listBattery.add(new ModelData("Voltage", defaultData[23] + " Vdc"));
        listBattery.add(new ModelData("Current", defaultData[24] + " Amps"));
        listBattery.add(new ModelData("Dischg. Elapse Time", defaultData[25] + ""));
        listBattery.add(new ModelData("Removed Ah", defaultData[26] + ""));
        listBattery.add(new ModelData("SOC", defaultData[27] + ""));
        listBattery.add(new ModelData("Ah Rating", defaultData[28] + ""));
        listBattery.add(new ModelData("Battery Temp", defaultData[Config.Mon_BatTemp] + " "+(char) 0x00B0+"C"));
        //listBattery.add(new ModelData("Date", defaultData[30] + defaultData[31] + ""));
        listBattery.add(new ModelData("Last Test Date", defaultData[Config.Mon_BatteryResultDate] + ""));
        listBattery.add(new ModelData("Last Test Result", defaultData[Config.Mon_BatteryResult].contains("0")?"Failed": defaultData[Config.Mon_BatteryResult].contains("1")?"Passed":""));
        listBattery.add(new ModelData("Breaker", defaultData[Config.Mon_BatteryContactor].contains("1")?"Close":"Open"));
        listViewBattery.setAdapter(new DisplayAdapter(getActivity(), listBattery));
    }

    private void prepareInputReadings() {
        listInput.clear();
        listInput.add(new ModelData("Voltage", defaultData[8] + " Vac"));
        listInput.add(new ModelData("Current", defaultData[9] + " Amps"));
        listInput.add(new ModelData("Frequency", defaultData[10] + " Hz"));
        listInput.add(new ModelData("VA", defaultData[17] + ""));
        listInput.add(new ModelData("Power", defaultData[18] + "Watt"));
        listInput.add(new ModelData("Breaker", defaultData[Config.Mon_InputContactor].contains("1")?"Close":"Open"));
        listViewInput.setAdapter(new DisplayAdapter(getActivity(), listInput));
    }

    private void prepareInverterReadings() {
        listInverter.clear();
        listInverter.add(new ModelData("Voltage", defaultData[37] + " Vac"));
        listInverter.add(new ModelData("Current", defaultData[38] + " Amps"));
        listInverter.add(new ModelData("Frequency", defaultData[39] + " Hz"));
        listInverter.add(new ModelData("VA", defaultData[40] + ""));
        listInverter.add(new ModelData("Power", defaultData[41] + " Watt"));
        listInverter.add(new ModelData("Power Factor", defaultData[42] + ""));
        listInverter.add(new ModelData("Sync Status", defaultData[Config.Mon_InvSYNCSTATUS].contains("1")?"In Sync": "Out of Sync"));
        listViewInverter.setAdapter(new DisplayAdapter(getActivity(), listInverter));
    }

    private void prepareByPassReadings() {
        listByPass.clear();
        listByPass.add(new ModelData("Voltage", defaultData[47] + " Vac"));
        listByPass.add(new ModelData("Frequency", defaultData[48] + " Hz"));
        listByPass.add(new ModelData("Braker", defaultData[Config.Mon_ByPassContactor].contains("1")?"Close":"Open"));
        listViewByPass.setAdapter(new DisplayAdapter(getActivity(), listByPass));
    }

    private void prepareLoadReadings() {
        listLoad.clear();
        listLoad.add(new ModelData("Source", defaultData[Config.Mon_ByPassSource].contains("1")?"Input":defaultData[Config.Mon_ByPassSource].contains("2")?"Battery":defaultData[Config.Mon_ByPassSource].contains("3")?"ByPass":""));
        listLoad.add(new ModelData("Voltage", defaultData[55] + " Vac"));
        listLoad.add(new ModelData("Current", defaultData[56] + " Amps"));
        listLoad.add(new ModelData("Frequency", defaultData[57] + " Hz"));
        listLoad.add(new ModelData("Output Power", defaultData[58] + " Watt"));
        listLoad.add(new ModelData("Power factor", defaultData[59] + ""));
        listViewLoad.setAdapter(new DisplayAdapter(getActivity(), listLoad));

    }

    private void prepareSystemReadings() {
        listSystem.clear();
        listSystem.add(new ModelData("Model Number", defaultData[1] + ""));
        listSystem.add(new ModelData("Name/ID", defaultData[2] + ""));
        listSystem.add(new ModelData("Location", defaultData[3] + ""));
        listSystem.add(new ModelData("Serial Number", defaultData[4] + ""));

        listViewSystem.setAdapter(new DisplayAdapter(getActivity(), listSystem));
    }

    public void updatedData(String data) {
        defaultData = data.split(",");
        displayData();
        Log.v("Update Monitoring", "aaa ");
        if (Config.dialogDisplayed.length() > 0) {
            switch (Config.dialogDisplayed.toLowerCase()){
                case "input":{
                    showInputDialog();
                    //config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listInput);
                    break;
                }
                case "battery":{
                    config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listBattery,false);
                    break;
                }
                case "inverter":{
                    config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listInverter,false);
                    break;
                }
                case "bypass":{
                    config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listByPass,false);
                    break;
                }
                case "load":{
                    config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listLoad,false);
                    break;
                }
                case "system":{
                    config.updateReadingDialog(getActivity(), config.dialogDisplayed.toUpperCase(), listSystem,false);
                    break;
                }
            }
        }
    }

    public void updatedInitialData(String data) {
        defaultData = data.split(",");
        displayData();
    }

    public void displayData() {
        if (defaultData != null) {
            Log.v("DefaultData=", defaultData.toString());
            if (listViewBattery != null) {
                try {
                    prepareBatteryReadings();
                    prepareByPassReadings();
                    prepareInputReadings();
                    prepareInverterReadings();
                    prepareLoadReadings();
                    prepareSystemReadings();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    String mode="";
    @Override
    public boolean onTouch(View v, MotionEvent event) {

        if(v.getId()==R.id.listBattery || v.getId()==R.id.layoutBattery){
            mode="BATTERY";
        }else if(v.getId()==R.id.listInput || v.getId()==R.id.layoutInput){
            mode="INPUT";
        }else if(v.getId()==R.id.listInverter || v.getId()==R.id.layoutInverter){
            mode="INVERTER";
        }else if(v.getId()==R.id.listLoad || v.getId()==R.id.layoutLoad){
            mode="LOAD";
        }else if(v.getId()==R.id.listByPass || v.getId()==R.id.layoutByPass){
            mode="BYPASS";
        }else if(v.getId()==R.id.listSystem || v.getId()==R.id.layoutSystem){
            mode="SYSTEM";
        }

        Log.v("Action= ",event.getAction()+"  a");


        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            // Execute your Runnable after 5000 milliseconds = 5 seconds.
            handler.postDelayed(runnable, 800);
            mBooleanIsPressed = true;
            return true;
        }

        if(event.getAction() == MotionEvent.ACTION_UP) {
            if(mBooleanIsPressed) {
                mBooleanIsPressed = false;
                handler.removeCallbacks(runnable);
            }
        }
        return false;
    }
}