package com.app.utlcontroller.Presenter;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.utlcontroller.Adapter.AdapterAlarms;
import com.app.utlcontroller.Adapter.DisplayAdapter_FactorySettings;
import com.app.utlcontroller.Adapter.MyAdapter;
import com.app.utlcontroller.Contract.HomeContractor;
import com.app.utlcontroller.Controller.Config;
import com.app.utlcontroller.Controller.MainActivity;
import com.app.utlcontroller.Controller.PositionCallback;
import com.app.utlcontroller.Model.HelperClass;
import com.app.utlcontroller.Model.ModelAlarm;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.R;
import com.app.utlcontroller.interfaces.SuccessInterface;
import com.app.utlcontroller.services.BluetoothService;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import static android.R.attr.data;
import static android.R.id.list;

/**
 * Created by mukeshgarg on 11/15/2017.
 */

public class HomePresenter {
    MainActivity activity;
    HomeContractor.HomeView homeView;
    public HomePresenter(MainActivity activity,HomeContractor.HomeView homeView){
        this.homeView=homeView;
        this.activity=activity;
    }

    public void noBluetoothConnection() {
        activity.imgPacketStatus.setImageResource(R.drawable.bluetooth_disconnect);
        noCurrentFlow();
    }

    public void checkPowerMode(String[] data) {
        activity.txtPowerMode.setText(data[Config.Mon_ByPassSource].contains("1") ? "UPS MODE" : data[Config.Mon_ByPassSource].contains("2") ? "BATTERY MODE" : data[Config.Mon_ByPassSource].contains("3") ? "BYPASS MODE" : "UPS MODE");
    }

    public void saveEventsIntoFile(String powerMode) {
        if (Config.isBluetoothConnected_UPS()) {
            Config.generateEventLogFile(activity, "Event Name", powerMode);
        }
    }

    public void updateAlarmData(String data) {
        activity.inverterFault = activity.rectifierFault = activity.gridFault = activity.byPassFault = false;
        activity.outPutOn = activity.mbsOn = activity.chargerOn = activity.byPassOn = activity.inverterOn = false;
        activity.listAlarms = data.split(",");
        StringBuffer s = new StringBuffer();
        ArrayList<ModelAlarm> updatedAlarmList = new ArrayList<>();
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[1])));
        s.reverse();

        Log.v("S===[0]", s + "   " + activity.listAlarms[1]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Overheated", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Overloading", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Overload", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. feedback open", true));
            activity.inverterFault = true;
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[2])));
        s.reverse();
        Log.v("S===[1]", s + "   " + activity.listAlarms[2]);
        if (s.length() >= 3 && s.charAt(2) == '1')
            if (s.length() >= 4 && s.charAt(3) == '1') {
                updatedAlarmList.add(new ModelAlarm("Inv. Output is High", true));
                activity.inverterFault = true;
            }
        if (s.length() >= 5 && s.charAt(4) == '1') {
            updatedAlarmList.add(new ModelAlarm("Phase reverse Err", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. output High", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            updatedAlarmList.add(new ModelAlarm("Phase reverse Err ", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 8 && s.charAt(7) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Mode ON", false));
            activity.byPassOn = true;
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[3])));
        s.reverse();
        Log.v("S===[2]", s + "   " + activity.listAlarms[3]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inverter On", false));
            activity.inverterOn = true;
        }
        if (s.length() >= 1 && s.charAt(0) == '0') {
            updatedAlarmList.add(new ModelAlarm("Inverter Off", true));
        }
        if (s.length() >= 2 && s.charAt(1) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Batt Low", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Off Batt. Low", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 4 && s.charAt(3) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Batt. High", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 5 && s.charAt(4) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Off Batt. High", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Overheated", true));
            activity.inverterFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            //   updatedAlarmList.add(new ModelAlarm("INVERTER_SwitchIsON",true));
        }
        if (s.length() >= 8 && s.charAt(7) == '1') {
            updatedAlarmList.add(new ModelAlarm("Inv. Short Circuit", true));
            activity.inverterFault = true;
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[4])));
        s.reverse();
        Log.v("S===[3]", s + "   " + activity.listAlarms[4]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Chg. Overheated", true));
            activity.rectifierFault = true;
        }
        if (s.length() >= 2 && s.charAt(1) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input Frq. Err", true));
            activity.rectifierFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input Absent", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 4 && s.charAt(3) == '1') {
            updatedAlarmList.add(new ModelAlarm("Chg. Short circuit", true));
            activity.rectifierFault = true;
        }
        if (s.length() >= 5 && s.charAt(4) == '1') {
            //updatedAlarmList.add(new ModelAlarm("CHARGER_GRID_CHARGER_EN",true));
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Chg. Off output High", true));
            activity.rectifierFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            updatedAlarmList.add(new ModelAlarm("Chg. Off Input fail", true));
            activity.rectifierFault = true;
        }
        if (s.length() >= 8 && s.charAt(7) == '1') {
            updatedAlarmList.add(new ModelAlarm("Batt. Full Charge", false));
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[5])));
        s.reverse();
        Log.v("S===[4]", s + "   " + activity.listAlarms[5]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Charger On", false));
            activity.chargerOn = true;
        }
        if (s.length() >= 1 && s.charAt(0) == '0') {
            updatedAlarmList.add(new ModelAlarm("Charger Off", true));
        }

        if (s.length() >= 2 && s.charAt(1) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH1 Low", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH2 low", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 4 && s.charAt(3) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH3 Low", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 5 && s.charAt(4) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH1 High", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH2 High", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            updatedAlarmList.add(new ModelAlarm("Input PH3 High", true));
            activity.rectifierFault = true;
            activity.gridFault = true;
        }
        if (s.length() >= 8 && s.charAt(7) == '1') {
            updatedAlarmList.add(new ModelAlarm("Chg. Feedback Open", true));
            activity.rectifierFault = true;
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[6])));
        s.reverse();
        Log.v("S===[5]", s + "   " + activity.listAlarms[6]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Freq.Err.", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 2 && s.charAt(1) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Low", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass High", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 4 && s.charAt(3) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Absent.", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 6 && s.charAt(5) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Fail", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 7 && s.charAt(6) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Synced", false));
        }
        if (s.length() >= 8 && s.charAt(7) == '1') {
            updatedAlarmList.add(new ModelAlarm("Load On Bypass", false));
        }

        s.delete(0, s.length());
        s.append(Integer.toBinaryString(Integer.parseInt(activity.listAlarms[7])));
        s.reverse();
        Log.v("S===[6]", s + "   " + activity.listAlarms[7]);
        if (s.length() >= 1 && s.charAt(0) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Overload", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 2 && s.charAt(1) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Overloaded", true));
            activity.byPassFault = true;
        }
        if (s.length() >= 3 && s.charAt(2) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass Output On", true));
            activity.outPutOn = true;
        }
        if (s.length() >= 4 && s.charAt(3) == '1') {
            updatedAlarmList.add(new ModelAlarm("Bypass MBS On", true));
            activity.mbsOn = true;
        }

        activity.adapterAlarm = new AdapterAlarms(activity, updatedAlarmList);
        activity.listViewAlarms.setAdapter(activity.adapterAlarm);
        activity.adapterAlarm.notifyDataSetChanged();
        updateAlarmFaultData();
    }

    public void updateAlarmFaultData() {
        // data = defaultMontoringData.split(",");
        Log.v("updateAlarmFaultData==", activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData));
        activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");

        if (activity.data[Config.Mon_InvSYNCSTATUS].contains("1")) {
            activity.txtSyncStatus.setText("IN SYNC");
            activity.txtSyncStatus.setBackgroundResource(R.drawable.drawable_app_green);
            activity.showOutOfSynTimer = false;
        } else {
            activity.txtSyncStatus.setText("OUT OF SYNC");
            activity.txtSyncStatus.setBackgroundResource(R.drawable.drawable_round);

            if (activity.showOutOfSynTimer) {
                activity.showOutOfSynTimer = true;
                AlertDialog.Builder ab = new AlertDialog.Builder(activity);
                ab.setTitle("Warning!");
                ab.setMessage("System Out Of Sync");
                ab.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activity.outOfSyncDialog.dismiss();
                    }
                });
                activity.outOfSyncDialog = ab.create();
                activity.outOfSyncDialog.show();
            }
        }
        try {
            int temp = Integer.parseInt(activity.data[Config.Mon_SYS_TEMP]);
            activity.txtTemperature.setText("TEMP: " + temp + " " + (char) 0x00B0 + "C");

            if (temp < 40) {
                activity.txtTemperature.setBackgroundResource(R.drawable.drawable_app_green);
            } else if (temp <= 60 && temp >= 40) {
                activity.txtTemperature.setBackgroundResource(R.drawable.drawable_app_yellow);
            } else if (temp >= 60) {
                activity.txtTemperature.setBackgroundResource(R.drawable.drawable_round);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (activity.lastStaterectifierFault == activity.rectifierFault && activity.lastStatebyPassFault == activity.byPassFault
                && activity.lastStateinverterFault == activity.inverterFault
                && activity.lastStategridFault == activity.gridFault && activity.lastStatechargerOn == activity.chargerOn
                && activity.lastStateinverterOn == activity.inverterOn &&
                activity.lastStatebyPassOn == activity.byPassOn && activity.lastStatembsOn == activity.mbsOn
                && activity.data[Config.Mon_BatteryContactor].equals(activity.lastBatteryContactor)
                && activity.data[Config.Mon_InputContactor].equals(activity.lastInputContactor) && activity.data[Config.Mon_ByPassContactor].equals(activity.lastByPasscontactor)) {
        } else {
            noCurrentFlow();
        }

        checkPowerMode(activity.data);
        /*rectifierFault=false;
        chargerOn=false;
        outPutOn=true;
        byPassOn=true;
        mbsOn=true;
        inverterOn=true;
        data[Config.Mon_ByPassContactor]="1";*/
        if (!activity.gridFault && activity.chargerOn && activity.data[Config.Mon_InputContactor].contains("1")) {
            //imgGridClose.setVisibility(View.GONE);
            showGrid_RectifierFlow();
        } else if (activity.data[Config.Mon_InputContactor].contains("1")) {
            stopGrid_RectifierFlow();
        } else {
            activity.imgMCBGrid.setImageResource(R.drawable.switch_open);
            stopGrid_RectifierFlow();
        }

        if (!activity.inverterFault && activity.inverterOn && activity.chargerOn) {
            Log.v("Flow", "inverterOn && chargerOn");
            activity.tView.rectifierToInverterFlow();
            activity.seperatorSwitchBattery.stopAnimation();
        }
        if (activity.chargerOn && activity.data[Config.Mon_BatteryContactor].contains("1")) {
            Log.v("Flow", "chargerOn && data[Config.Mon_BatteryContactor].contains(\"1\")");
            activity.tView.rectifierToBattery();
            activity.seperatorSwitchBattery.startGridToBatteryAnimation();
        }
        if (!activity.inverterFault && !activity.chargerOn && activity.inverterOn && activity.data[Config.Mon_BatteryContactor].contains("1")) {
            Log.v("Flow", "!chargerOn && inverterOn && data[Config.Mon_BatteryContactor].contains(\"1\")");
            activity.tView.flowBatteryToLoad();
            activity.seperatorSwitchBattery.startBatteryToLoadAnimation();
        }
        if (!activity.inverterFault && activity.inverterOn) {
            activity.seperatorInverter_STS.startAnimation();
            activity.lView.startSts_Load();
        }

        if (activity.byPassOn || activity.mbsOn) {
            activity.seperatorInverter_STS.stopAnimation();
            activity.lView.disableSts_Load();
        }

        if (!activity.byPassFault && activity.outPutOn && activity.data[Config.Mon_ByPassContactor].contains("1") &&
                activity.byPassOn) {
            activity.seperatorIsolation_Bypass.startAnimation();
            activity.seperatorMCBByPassToIsolation.startAnimation();
            activity.seperatorMCBByPassToSwitch.startAnimation();
            //imgBypassClose.setVisibility(View.GONE);
        }
        if (!activity.byPassFault && activity.outPutOn && activity.data[Config.Mon_ByPassContactor].contains("1") &&
                activity.byPassOn && !activity.mbsOn) {
            activity.lView.startByPass_Sts();
            activity.lView.startSts_Load();
            activity.imgswitch1input2output.setImageResource(R.drawable.mbs_1_top);
            activity.imgSwitch2input1output.setImageResource(R.drawable.mbs_2_top);
        }
        if (!activity.byPassFault && activity.outPutOn && activity.data[Config.Mon_ByPassContactor].contains("1") &&
                activity.byPassOn && activity.mbsOn) {
            activity.lView.startMBS1_MBS2();
            activity.imgswitch1input2output.setImageResource(R.drawable.mbs_1_bottom);
            activity.imgSwitch2input1output.setImageResource(R.drawable.mbs_2_bottom);
        }

        if (activity.outPutOn) {
            activity.seperatorOutput.startAnimation();
        }

        if (activity.data[Config.Mon_InputContactor].contains("1")) {
            activity.imgMCBGrid.setImageResource(R.drawable.close_switch);
        } else {
            activity.imgMCBGrid.setImageResource(R.drawable.switch_open);
        }

        if (activity.data[Config.Mon_BatteryContactor].contains("1")) {
            activity.batterySwitch.setImageResource(R.drawable.close_switch);
        } else {
            activity.batterySwitch.setImageResource(R.drawable.switch_open);
        }

        if (activity.data[Config.Mon_ByPassContactor].contains("1")) {
            activity.imgMCBByPass.setImageResource(R.drawable.close_switch);
        } else {
            activity.imgMCBByPass.setImageResource(R.drawable.switch_open);
        }
        //if(outPutOn)

        if (!activity.inverterOn && !activity.chargerOn) {
            activity.tView.stopAnimation();
            activity.imgInverter.setBackgroundResource(R.drawable.background_switch);
        }

        if (activity.inverterFault) {
            activity.imgInverter.setBackgroundResource(R.drawable.background_switch);
        } else {
            activity.imgInverter.setBackgroundResource(R.drawable.background_switch_green);
        }

        if (activity.rectifierFault) {
            activity.imgRectifier.setBackgroundResource(R.drawable.background_switch);
        } else {
            activity.imgRectifier.setBackgroundResource(R.drawable.background_switch_green);
        }

        if (activity.gridFault) {
            activity.imgPowerGrid.setBackgroundResource(R.drawable.background_switch);
        } else {
            activity.imgPowerGrid.setBackgroundResource(R.drawable.background_switch_green);
        }

        if (activity.byPassFault) {
            activity.imgPowerByPass.setBackgroundResource(R.drawable.background_switch);
        } else {
            activity.imgPowerByPass.setBackgroundResource(R.drawable.background_switch_green);
        }

        activity.lastStaterectifierFault = activity.rectifierFault;
        activity.lastStatebyPassFault = activity.byPassFault;
        activity.lastStateinverterFault = activity.inverterFault;
        activity.lastStategridFault = activity.gridFault;
        activity.lastStatechargerOn = activity.chargerOn;
        activity.lastStateinverterOn =activity. inverterOn;
        activity.lastStatebyPassOn = activity.byPassOn;
        activity.lastStatembsOn = activity.mbsOn;
        activity.lastBatteryContactor = activity.data[Config.Mon_BatteryContactor];
        activity.lastInputContactor = activity.data[Config.Mon_InputContactor];
        activity.lastByPasscontactor = activity.data[Config.Mon_ByPassContactor];
    }

    public void showGrid_RectifierFlow() {
        activity.seperatorMCBGrid.startAnimation();
        activity.seperatorGridPower_Switch.startAnimation();
    }

    public void stopGrid_RectifierFlow() {
        activity.seperatorMCBGrid.stopAnimation();
        activity.seperatorGridPower_Switch.stopAnimation();
    }

    public void noCurrentFlow() {
        activity.imgMCBByPass.setImageResource(R.drawable.switch_open);
        activity.imgMCBGrid.setImageResource(R.drawable.switch_open);
        activity.imgMCBByPass.setImageResource(R.drawable.switch_open);
        activity.batterySwitch.setImageResource(R.drawable.switch_open);
        activity.imgSwitch2input1output.setImageResource(R.drawable.mbs_2_top);
        activity.imgswitch1input2output.setImageResource(R.drawable.mbs_1_top);
        activity.seperatorMCBGrid.stopAnimation();
        activity.tView.stopAnimation();
        activity.seperatorSwitchBattery.stopAnimation();
        activity.seperatorInverter_STS.stopAnimation();
        activity.lView.noFlow();
        stopByPassFlow();
        activity.seperatorOutput.stopAnimation();
    }

    public void stopByPassFlow() {
        activity.seperatorMCBByPassToIsolation.stopAnimation();
        activity.seperatorMCBByPassToSwitch.stopAnimation();
        activity.seperatorIsolation_Bypass.stopAnimation();
    }

    public void startByPassflow() {
        activity.seperatorMCBByPassToIsolation.startAnimation();
        activity.seperatorMCBByPassToSwitch.startAnimation();
        activity.seperatorIsolation_Bypass.startAnimation();
    }


    public void clearLogsDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("Are you sure to clear Event Logs? ");
        ab.setTitle("Clear Logs");

        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                File f = Config.getEventLogsFile();
                if (f.exists()) {
                    f.delete();
                    Toast.makeText(activity, "Event Logs Cleared", Toast.LENGTH_LONG).show();
                }
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.d.dismiss();

            }
        });
        activity.d = ab.create();
        activity.d.show();
    }

    public void sendLogsToUsbDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("Are you sure to send Logs to Usb device? ");
        ab.setTitle("Send Logs ");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.d.dismiss();
            }
        });
        activity.d = ab.create();
        activity.d.show();
    }

    public void configureLogDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("Do you want to save latest configuration? ");
        ab.setTitle("Configure Logs ");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.generateConfigLogFile(activity, "Event Name", activity.powerMode);
            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.d.dismiss();
            }
        });
        activity.d = ab.create();
        activity.d.show();
    }

    public void settingsLogDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(activity);
        ab.setMessage("Do you want to save latest configuration? ");
        ab.setTitle("Settings Logs ");
        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Config.generateSettingLogFile(activity, "Event Name", activity.powerMode);
            }
        });
        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                activity.d.dismiss();
            }
        });
        activity.d = ab.create();
        activity.d.show();
    }

    public void updateCallbackData(final String data) {
        if (data.contains(Config.RECEIVING_DATA)) {
            activity.receiving = true;
            checkDataStatus();
        }

        if (data.contains(Config.MONITORING_SDATA) && data.contains(Config.MONITORING_EDATA)) {
            activity.receiving = false;
            activity.lastPacketReceived = Calendar.getInstance().getTimeInMillis();
            activity.packetReceived = true;
            checkDataStatus();
            if (!activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).equals(data)) {
                activity.getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.MonitoringReading, data).commit();
                Log.v("previousMonitoringData", activity.previousMonitoringData);
                Log.v("Data", data);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (activity.monitoringFragment != null && activity.monitoringFragment.isVisible()) {
                            activity.monitoringFragment.updatedData(data);
                        }
                        Log.v("Dialog Diaplayed=", Config.dialogDisplayed + "   aaa");
                        if (Config.dialogDisplayed.length() > 0) {
                            prepareData(Config.dialogDisplayed);
                            if (Config.dialogDisplayed.toLowerCase().contains("input")) {
                                activity.config.updateReadingDialog_RecyclerView(activity, "INPUT", activity.listEvent);
                            } else {
                                activity.config.updateReadingDialog(activity, activity.config.dialogDisplayed.toUpperCase(), activity.listData,activity.rectifierFault);
                            }
                        }
                        updateAlarmFaultData();
                        if (activity.isSettingsVisible) {
                            showSettingDialog();
                        }
                    }
                });
            }
        } else if (data.contains(Config.CONFIGURATION_SETTINGS_SDATA) && data.contains(Config.CONFIGURATION_SETTINGS_EDATA)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("Configuration data=", data);
                    activity.receiving = true;
                    checkDataStatus();
                    if (!activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.configurationData_Format).equals(data)) {
                        activity.getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.Configuration_Reading, data).commit();

                        if (activity.configurationFragment != null && activity.configurationFragment.isVisible()) {
                            activity.configurationFragment.updatedData(data);
                        }
                    }
                }
            });

        } else if (data.contains(Config.FACTORY_SETTINGS_SDATA) && data.contains(Config.FACTORY_SETTINGS_EDATA)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("Configuration data=", data);
                    activity.receiving = true;
                    checkDataStatus();
                    if (!activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.FACTORY_READING, Config.factoryData_Format).equals(data)) {
                        activity.getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.FACTORY_READING, data).commit();
                    }

                }
            });

        } else if (data.contains(Config.STATUS_SDATA) && data.contains(Config.STATUS_EDATA)) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.v("Alarm data=", data);
                    activity.receiving = true;
                    checkDataStatus();
                    if (!activity.previousAlarmData.equals(data)) {
                        Log.v("Previous Alarm data=", activity.previousAlarmData);
                        Log.v("New Alarm data=", data);
                        activity.previousAlarmData = data;
                        updateAlarmData(data);
                    }
                }
            });
        }
    }

    public void checkDataStatus() {
        Log.v("Log", "Check Status");
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (activity.receiving) {
                    activity.imgPacketStatus.setImageResource(R.drawable.bluetooth_transfer);
                } else if (activity.packetReceived) {
                    activity.imgPacketStatus.setImageResource(R.drawable.bluetooth_connected);
                }
                if (activity.h != null && activity.runnable != null) {
                    activity.h.removeCallbacks(activity.runnable);
                }
                activity.h.postDelayed(activity.runnable, Config.COMM_UPDATE_TIME);
            }
        });
    }


    public void showSettingDialog() {
        Log.v("UpdateSettingDialog", "Display");
        if (!activity.isSettingsVisible) {
            activity.d = new Dialog(activity);
            activity.d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(activity).inflate(R.layout.setting_popup, null);
            activity.d.setContentView(v);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(activity.d.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            activity.listFactorySettings = new ArrayList<>();
            activity.d.getWindow().setAttributes(lp);
            activity.d.show();
        }
        final View factorySettingsView = activity.d.findViewById(R.id.factorySettingsView);
        RadioGroup radioGroup = (RadioGroup) activity.d.findViewById(R.id.radioGroup);
        final TextView txtSave = (TextView) activity.d.findViewById(R.id.txtSave);
        final TextView txtSend = (TextView) activity.d.findViewById(R.id.txtSend);
        final View debugSettings = activity.d.findViewById(R.id.debugSettings);
        activity.imgInverterOff = (ImageView) activity.d.findViewById(R.id.imgInverterOff);
        activity.d.findViewById(R.id.imgClearFaults).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(activity, "CLEAR ALL FAULTS");
            }
        });

        activity.imgInverterOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(activity, "INVERTER OFF");
                //imgInverterOn.setVisibility(View.VISIBLE);
                //imgInverterOff.setVisibility(View.GONE);
            }
        });

        final ListView factorySettingsListView = (ListView) activity.d.findViewById(R.id.factorySettingsListView);
        final ListView listViewStatic = (ListView) activity.d.findViewById(R.id.listViewStatic);
        final ListView listViewInverter = (ListView) activity.d.findViewById(R.id.listViewInverter);
        ArrayList<Model_Step_Checkbox> listDebugSettings = new ArrayList<>();
        ArrayList<Model_Step_Checkbox> listDebugInverterSettings = new ArrayList<>();
        String[] data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.FACTORY_READING, Config.factoryData_Format).split(",");
        String[] monitoringData = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
        Log.v("Ara", Arrays.asList(data).toString());
        RadioGroup radioGroupDebug = (RadioGroup) activity.d.findViewById(R.id.radioGroupDebug);
        radioGroupDebug.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioStatic) {
                    listViewStatic.setVisibility(View.VISIBLE);
                    listViewInverter.setVisibility(View.GONE);
                } else {
                    listViewStatic.setVisibility(View.GONE);
                    listViewInverter.setVisibility(View.VISIBLE);
                }
            }
        });

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer resultString = new StringBuffer();
                resultString.append("FSD");
                for (int i = 0; i < activity.listFactorySettings.size(); i++) {
                    if (activity.listFactorySettings.get(i).viewType == 1) {
                        resultString.append("," + activity.listFactorySettings.get(i).type1Value);
                    } else {
                        if (activity.listFactorySettings.get(i).viewType == 4)
                            resultString.append("," + (int) (activity.listFactorySettings.get(i).value));
                        else
                            resultString.append("," + activity.listFactorySettings.get(i).value);
                    }
                }
                activity.getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.FACTORY_READING, resultString.toString()).commit();
                Config.testCommand(activity, resultString.toString());
            }
        });

        //listDebugSettings.add(new Model_Step_Checkbox("Factory Setting Data String Identifier", "",false));
        if (!activity.isSettingsVisible) {
            /*activity.listFactorySettings.add(new Model_Step_Checkbox("Parallel operation Status", data[1].contains("1") ? 1 : 0, "", new String[]{"OFF", "ON"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("System Rating", data[2], true, "DECIMAL NUMBER", "KVA"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Output Mode", data[3].contains("0") ? 0 : data[3].contains("1") ? 1 : 2, "", new String[]{"120", "180", "240"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Input Mode", 0, data[4], new String[]{"120", "180", "240"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("DC Charging Current Gain ", data[5], true, "DECIMAL NUMBER", "V/A"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("DC Dis-Charging Current Gain ", data[6], true, "DECIMAL NUMBER", "V/A"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("DC Battery Voltage Gain ", data[7], true, "DECIMAL NUMBER", "V/V"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Operating Frequency", data[8].contains("0") ? 0 : 1, "", new String[]{"60", "50"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("External Communication", data[9].contains("0") ? 0 : data[9].contains("1") ? 1 : 2, "", new String[]{"Modbus", "Ethernet", "CAN"}));
            */

            activity.listFactorySettings.add(new Model_Step_Checkbox("Model Number", data[1], true,""));
            activity.listFactorySettings.add(new Model_Step_Checkbox("System Serial Number", data[2], true,""));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Parallel operation Status", data[3].contains("1") ? 1 : 0, "", new String[]{"OFF", "ON"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Out of Sync Transfer", data[4].contains("1") ? 1 : 0, "", new String[]{"Immediate", "1 Cycle Delay"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("System Rating", data[5], true, "DECIMAL NUMBER", "KVA"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Output Frequency", data[6].contains("0") ? 0 :1, "", new String[]{"50", "60"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Output Voltage", data[7].contains("0") ? 0 : 1, "", new String[]{"1PH", "3PH"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Input Type", data[8].contains("0") ? 0 : data[8].contains("1") ? 1 : 2, "", new String[]{"120", "180", "240"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Input Voltage", data[9].contains("0") ? 0 : data[9].contains("1") ? 1 : 2, "", new String[]{"120", "180", "240"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("DC Bus Voltage", data[10].contains("0") ? 0 : data[10].contains("1") ? 1 : data[10].contains("2") ? 2 : 3, "", new String[]{"120", "240", "360","480"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Charger Capacity", data[11], true, "DECIMAL NUMBER", "V/A"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Rectifier Shunt Size", data[12].contains("0") ? 0 : 1, "", new String[]{"50", "100"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox(" Battery Shunt Size", data[13], true, "DECIMAL NUMBER", "Amps"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("External Communication", data[14].contains("0") ? 0 : data[14].contains("1") ? 1 : 2, "", new String[]{"Modbus", "Ethernet", "CAN"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Inverter Output Breaker", data[15], true, "DECIMAL NUMBER", "V/A"));
            activity.listFactorySettings.add(new Model_Step_Checkbox("Rectifier Output Breaker", data[16].contains("0") ? 0 : 1, "", new String[]{"Enable", "Disable"}));
            activity.listFactorySettings.add(new Model_Step_Checkbox("UPS Output Breaker", data[17].contains("0") ? 0 : 1, "", new String[]{"Enable", "Disable"}));

            DisplayAdapter_FactorySettings factoryAdapter = new DisplayAdapter_FactorySettings(activity, activity.listFactorySettings, new PositionCallback() {
                @Override
                public void clickPosition(int position) {

                }

                @Override
                public void clickPosition(int position, int spinnerIndex) {
                    Log.v("Spnner Callback", position + "  " + spinnerIndex);
                    activity.listFactorySettings.get(position).value = spinnerIndex;
                }
            });
            factorySettingsListView.setAdapter(factoryAdapter);
        }
        listDebugInverterSettings.add(new Model_Step_Checkbox("Input VA", monitoringData[Config.Mon_InputVA] + "", false, "VA"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Input Power", monitoringData[Config.Mon_InputPower], false, "W"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Input Contactor", monitoringData[Config.Mon_InputContactor], false));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Battery Voltage", monitoringData[Config.Mon_BatterVol], false, " V"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Battery Current", " ", false, "A"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Dis Elapsed Time", monitoringData[Config.Mon_BatteryDisElapsedTime], false, "Minutes"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Removed AH", monitoringData[Config.Mon_RemovedAH], false, "AH"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Battery SOC", monitoringData[Config.Mon_BatterySOC], false, "%"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Battery Temp", monitoringData[Config.Mon_BatTemp], false, "C"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Last Battery Test", "", false, ""));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Last Test Result", monitoringData[Config.Mon_BatteryResult], false, ""));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Battery Contactor", "", false));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter Voltage", monitoringData[Config.Mon_InvVoltage], false, "V"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter Current", monitoringData[Config.Mon_InvCurrent], false, "A"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter  Frequency", monitoringData[Config.Mon_InvFrequency], false, "Hz"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter  VA", monitoringData[Config.Mon_InvVA], false, "VA"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter  Power", monitoringData[Config.Mon_InvPower], false, "W"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Inverter Power factor", monitoringData[Config.Mon_OutputPF], false, ""));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Sync Status", monitoringData[Config.Mon_InvSYNCSTATUS], false, ""));
        listDebugInverterSettings.add(new Model_Step_Checkbox("ByPass Voltage", monitoringData[Config.Mon_ByPassVoltage], false, "V"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("ByPass Frequency", monitoringData[Config.Mon_ByPassFrequency], false, "Hz"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("ByPass Contactor", monitoringData[Config.Mon_ByPassContactor], false, ""));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output Voltage", monitoringData[Config.Mon_OutputVoltage], false, "V"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output Current", monitoringData[Config.Mon_OutputCurrent], false, "A"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output Frequency", monitoringData[Config.Mon_OutputFrequency], false, "Hz"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output VA", monitoringData[Config.Mon_OutputVA], false, "VA"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output Power", monitoringData[Config.Mon_OutputPower], false, "W"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Output Power factor", monitoringData[Config.Mon_OutputPF], false));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Rectifier Voltage", monitoringData[Config.Mon_RectifierVoltage], false, "V"));
        listDebugInverterSettings.add(new Model_Step_Checkbox("Rectifier Current", monitoringData[Config.Mon_RectifierCurrent], false, "A"));

        listDebugSettings.add(new Model_Step_Checkbox("System Mode", monitoringData[Config.Mon_PowerMode], false));
        listDebugSettings.add(new Model_Step_Checkbox("Inverter Voltage", monitoringData[Config.Mon_InvVoltage], false, "V"));
        listDebugSettings.add(new Model_Step_Checkbox("Inverter Current", monitoringData[Config.Mon_InvCurrent], false, "A"));
        listDebugSettings.add(new Model_Step_Checkbox("Inverter  Frequency", monitoringData[Config.Mon_InvFrequency], false, "Hz"));
        listDebugSettings.add(new Model_Step_Checkbox("Inverter  VA", monitoringData[Config.Mon_InvVA], false, "VA"));
        listDebugSettings.add(new Model_Step_Checkbox("Inverter  Power", monitoringData[Config.Mon_InvPower], false, "W"));
        listDebugSettings.add(new Model_Step_Checkbox("Sync Status", monitoringData[Config.Mon_InvSYNCSTATUS], false));
        listDebugSettings.add(new Model_Step_Checkbox("ByPass Voltage", monitoringData[Config.Mon_ByPassVoltage], false, "V"));
        listDebugSettings.add(new Model_Step_Checkbox("ByPass Frequency", monitoringData[Config.Mon_ByPassFrequency], false, "Hz"));
        listDebugSettings.add(new Model_Step_Checkbox("ByPass Contactor", monitoringData[Config.Mon_ByPassContactor], false, ""));
        listDebugSettings.add(new Model_Step_Checkbox("Load Source", monitoringData[Config.Mon_ByPassSource], false));
        listDebugSettings.add(new Model_Step_Checkbox("Maintainance Switch", "", false));
        listDebugSettings.add(new Model_Step_Checkbox("Output Voltage", monitoringData[Config.Mon_OutputVoltage], false, "V"));
        listDebugSettings.add(new Model_Step_Checkbox("Output Current", monitoringData[Config.Mon_OutputCurrent], false, "Amp"));
        listDebugSettings.add(new Model_Step_Checkbox("Output Frequency", monitoringData[Config.Mon_OutputFrequency], false, "Hz"));
        listDebugSettings.add(new Model_Step_Checkbox("Output VA", monitoringData[Config.Mon_OutputVA], false, "VA"));
        listDebugSettings.add(new Model_Step_Checkbox("Output Power", monitoringData[Config.Mon_OutputPower], false, "W"));
        listDebugSettings.add(new Model_Step_Checkbox("Output Power factor", monitoringData[Config.Mon_OutputPF], false, ""));


        DisplayAdapter_FactorySettings debugAdapter = new DisplayAdapter_FactorySettings(activity, listDebugSettings, new PositionCallback() {
            @Override
            public void clickPosition(int position) {

            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        listViewStatic.setAdapter(debugAdapter);
        DisplayAdapter_FactorySettings debugInverterAdapter = new DisplayAdapter_FactorySettings(activity, listDebugInverterSettings, new PositionCallback() {
            @Override
            public void clickPosition(int position) {

            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        listViewInverter.setAdapter(debugInverterAdapter);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radioFactorySettings) {
                    if (!activity.passwordAlreadyAsked) {
                        new Config().showPasswordDialog(activity, new SuccessInterface() {
                            @Override
                            public void onSuccess() {
                                activity.passwordAlreadyAsked = true;
                                factorySettingsView.setVisibility(View.VISIBLE);
                                debugSettings.setVisibility(View.GONE);
                                txtSave.setVisibility(View.VISIBLE);
                                txtSend.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    } else {
                        factorySettingsView.setVisibility(View.VISIBLE);
                        debugSettings.setVisibility(View.GONE);
                        txtSave.setVisibility(View.VISIBLE);
                        txtSend.setVisibility(View.VISIBLE);
                    }


                } else {
                    factorySettingsView.setVisibility(View.GONE);
                    debugSettings.setVisibility(View.VISIBLE);
                    txtSave.setVisibility(View.GONE);
                    txtSend.setVisibility(View.GONE);
                }
            }
        });

        activity.d.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.d.dismiss();
            }
        });
        activity.isSettingsVisible = true;

        activity.d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                activity.isSettingsVisible = false;
                activity.passwordAlreadyAsked = false;
            }
        });
    }


    public void showDeviceDialog() {
        final Dialog d = new Dialog(activity);
        View v = activity.getLayoutInflater().inflate(R.layout.layout_devicelist, null);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(v);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(d.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        d.getWindow().setAttributes(lp);
        //d.getWindow().setGravity(Gravity.BOTTOM);
        System.out.println("parmit" + "Opening Dialog");
        ListView listView = (ListView) d.findViewById(R.id.listview);
        activity.pairedDevices = activity.mBluetoothAdapter.getBondedDevices();
        activity.list.clear();
        if (activity.pairedDevices.size() > 0) {
            for (BluetoothDevice device : activity.pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println("parmit" + device.getName() + "\n" + device.getAddress());
                activity.helperClass = new HelperClass();
                activity.helperClass.setMac(device.getAddress());
                activity.helperClass.setName(device.getName());
                activity.list.add(activity.helperClass);
            }

            activity.myAdapter = new MyAdapter(activity, activity.list);
            listView.setAdapter(activity.myAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    TextView textView = (TextView) view.findViewById(R.id.mac);
                    String info = textView.getText().toString();
                    String address = info.substring(info.length() - 17);

                    Log.v("Address= ", address);
                    BluetoothDevice device = null;
                    try {
                        device = activity.mBluetoothAdapter.getRemoteDevice(address);
                        BluetoothService.getInstance().connectBluetooth(device);

                        if (BluetoothService.getInstance() != null) {
                            BluetoothService.getInstance().addReportListener(activity.callback);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    /*ConnectThread mConnectThread = new ConnectThread(device);
                    mConnectThread.start();*/
                    d.dismiss();
                }
            });
        }
        d.show();
    }

    public void prepareData(String value) {
        activity.dialog = value;
        if (value.equalsIgnoreCase("input")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listEvent.clear();
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"", "PHASE-1", "PHASE-2", "PHASE-3", "UNIT"})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Voltage", activity.data[Config.Mon_InputVPhase1], activity.data[Config.Mon_InputVPhase2], activity.data[Config.Mon_InputVPhase3], "Vac"})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Current", activity.data[Config.Mon_InputCPhase1], activity.data[Config.Mon_InputCPhase2], activity.data[Config.Mon_InputCPhase3], "Amps"})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Frequency", activity.data[Config.Mon_InputFPhase1], activity.data[Config.Mon_InputFPhase2], activity.data[Config.Mon_InputFPhase3], "Hz"})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"VA", ""})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Power", activity.data[Config.Mon_InputPower] + " Watt"})));
            activity.listEvent.add(new ModelEvents(Arrays.asList(new String[]{"Breaker", activity.data[Config.Mon_InputContactor].contains("1") ? "Close" : "Open"})));
            activity.config.updateReadingDialog_RecyclerView(activity, "INPUT", activity.listEvent);

            /*listData.add(new ModelData("Grid voltage Phase 1", data[Config.Mon_InputVPhase1] + " Vac"));
            listData.add(new ModelData("Grid voltage Phase 2", data[Config.Mon_InputVPhase2] + " Vac"));
            listData.add(new ModelData("Grid voltage Phase 3", data[Config.Mon_InputVPhase3] + " Vac"));
            listData.add(new ModelData("Grid frequency Phase 1", data[Config.Mon_InputFPhase1] + " Hz"));
            listData.add(new ModelData("Grid frequency Phase 2", data[Config.Mon_InputFPhase2] + " Hz"));
            listData.add(new ModelData("Grid frequency Phase 3", data[Config.Mon_InputFPhase3] + " Hz"));
            listData.add(new ModelData("Grid Current Phase 1", data[Config.Mon_InputCPhase1] + " Amps"));
            listData.add(new ModelData("Grid Current Phase 2", data[Config.Mon_InputCPhase2] + " Amps"));
            listData.add(new ModelData("Grid Current Phase 3", data[Config.Mon_InputCPhase3] + " Amps"));
            listData.add(new ModelData("Grid Power", data[Config.Mon_InputPower] + " KVA"));
            listData.add(new ModelData("Grid Energy", " KWHR"));*/
        }
        if (value.equalsIgnoreCase("transformer")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("ByPass Voltage", activity.data[Config.Mon_ByPassVoltage] + " Vac"));
            activity.listData.add(new ModelData("ByPass Frequency", activity.data[Config.Mon_ByPassFrequency] + " Hz"));
        } else if (value.equalsIgnoreCase("rectifier")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("Rectifier voltage", activity.data[Config.Mon_RectifierVoltage] + " Vdc"));
            activity.listData.add(new ModelData("Rectifier Current", activity.data[Config.Mon_RectifierCurrent] + " Amps"));
        } else if (value.equalsIgnoreCase("inverter")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("Inverter voltage", activity.data[Config.Mon_InvVoltage] + " Vac"));
            activity.listData.add(new ModelData("Inverter Frequency", activity.data[Config.Mon_InvFrequency] + " Hz"));
        } else if (value.equalsIgnoreCase("battery")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("Battery voltage", activity.data[Config.Mon_BatterVol] + " Vdc"));
            activity.listData.add(new ModelData("Battery Current", activity.data[Config.Mon_BatChrgDiscCurrent] + " Amps"));
            activity.listData.add(new ModelData("SOC", activity.data[Config.Mon_BatterySOC] + " %"));
            activity.listData.add(new ModelData("Battery Temp", activity.data[Config.Mon_BatTemp] + " 'C"));
        } else if (value.equalsIgnoreCase("byPass")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("ByPass Source Voltage", activity.data[Config.Mon_ByPassVoltage] + " Vac"));
            activity.listData.add(new ModelData("ByPass Source Current", activity.data[Config.Mon_ByPassSource] + " Hz"));
        } else if (value.equalsIgnoreCase("output")) {
            activity.data = activity.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
            activity.listData = new ArrayList<>();
            activity.listData.add(new ModelData("Output Voltage", activity.data[Config.Mon_OutputVoltage] + " Vac"));
            activity.listData.add(new ModelData("Output Frequency", activity.data[Config.Mon_OutputFrequency] + " Hz"));
            activity.listData.add(new ModelData("Output Current", activity.data[Config.Mon_OutputCurrent] + " Amps"));
            //listData.add(new ModelData("Load Percent", data[Config.Mon_ByPassVoltage]+"%"));
            activity.listData.add(new ModelData("Output Power", activity.data[Config.Mon_OutputPower] + " KVA"));
            //listData.add(new ModelData("Output Energy", data[Config.Mon_out]+"KWHr"));
            activity.listData.add(new ModelData("Output P.F", activity.data[Config.Mon_OutputPF] + " XX"));
        }
    }

    public void createRootDirectoryIfNotExist() {
        File f = new File(Config.getFilePath());
        if (f.exists() && f.isDirectory()) {
        } else {
            try {
                Log.v("Creating Directory", "Yes");
                f.mkdir();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void enableBluetooth() {
        activity.mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (activity.mBluetoothAdapter == null) {
            System.out.println("message" + "Bluetooth not supported");
        }else {
            if (!activity.mBluetoothAdapter.enable()) {
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                activity.startActivityForResult(enableBtIntent, activity.REQUEST_ENABLE_BT);
            }
        }
    }
}
