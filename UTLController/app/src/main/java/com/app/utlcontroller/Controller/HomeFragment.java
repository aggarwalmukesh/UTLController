package com.app.utlcontroller.Controller;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.R;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements View.OnClickListener{

    View imgPowerGrid,imgPowerByPass,rectifier,inverter,output,battery;
    SmallSeperatorView seperatorMCBByPassToSwitch,seperatorMCBGrid,seperatorMCBByPassToIsolation,seperatorGridPower_Switch,seperatorInverter_STS;
    SeperatorView seperatorIsolation_Bypass;
    SmallVerticalSeperatorView seperatorSwitchBattery;
    ImageView imgMCBGrid,imgMCBByPass,batterySwitch;
    ArrayList<ModelData> listData=new ArrayList<>();
    TView tView;
    Config config;
    String[] data;
    LView lView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_homepage_new,null);
        imgPowerByPass=v.findViewById(R.id.imgPowerByPass);
        imgPowerGrid=v.findViewById(R.id.imgPower);
        rectifier=v.findViewById(R.id.imgRectifier);
        inverter=v.findViewById(R.id.imgInverter);
        battery=v.findViewById(R.id.imgBattery);
        output=v.findViewById(R.id.output);
        tView= (TView) v.findViewById(R.id.tView);
        lView= (LView) v.findViewById(R.id.lView);
        seperatorMCBByPassToSwitch=(SmallSeperatorView)v.findViewById(R.id.seperatorMCBByPassToSwitch);
        seperatorInverter_STS=(SmallSeperatorView)v.findViewById(R.id.seperatorInverter_STS);
        seperatorMCBByPassToIsolation=(SmallSeperatorView)v.findViewById(R.id.seperatorMCBByPassToIsolation);
        seperatorMCBGrid= (SmallSeperatorView) v.findViewById(R.id.seperatorMCBGrid);
        seperatorGridPower_Switch= (SmallSeperatorView) v.findViewById(R.id.seperatorGridPower_Switch);
        seperatorSwitchBattery= (SmallVerticalSeperatorView) v.findViewById(R.id.seperatorSwitchBattery);
        seperatorIsolation_Bypass= (SeperatorView) v.findViewById(R.id.seperatorIsolation_Bypass);
        imgMCBGrid= (ImageView) v.findViewById(R.id.imgMCBGrid);
        imgMCBByPass= (ImageView) v.findViewById(R.id.imgMCBByPass);
        batterySwitch= (ImageView) v.findViewById(R.id.batterySwitch);
        config=new Config();
        imgPowerByPass.setOnClickListener(this);
        imgPowerGrid.setOnClickListener(this);
        output.setOnClickListener(this);
        inverter.setOnClickListener(this);
        rectifier.setOnClickListener(this);
        battery.setOnClickListener(this);
        updatedData();
        //showGridToLoad_BatteryFlow();
        // showBatteryFlow();
        //showByPassFlowSwitch_Switch_Load();
        //showByPassFlowSwitch_STS_Load();
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgPower:
                prepareData("power");

                config.showReadingDialog(getActivity(),"Power",listData,false);
                break;
            case R.id.imgRectifier:
                prepareData("rectifier");
                config.showReadingDialog(getActivity(),"Rectifier",listData,false);
                break;
            case R.id.output:
                prepareData("output");
                config.showReadingDialog(getActivity(),"Output",listData,false);
                break;
            case R.id.imgInverter:
                prepareData("inverter");
                config.showReadingDialog(getActivity(),"Inverter",listData,false);
                break;
            case R.id.imgBattery:
                prepareData("battery");
                config.showReadingDialog(getActivity(),"Battery",listData,false);
                break;
        }
    }

    public void prepareData(String value) {
        if(value.equalsIgnoreCase("power")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("Grid voltage Phase 1" ,data[Config.Mon_InputVPhase1]+" Vac"));
            listData.add(new ModelData("Grid voltage Phase 2", data[Config.Mon_InputVPhase2]+" Vac"));
            listData.add(new ModelData("Grid voltage Phase 3", data[Config.Mon_InputVPhase3]+" Vac"));
            listData.add(new ModelData("Grid frequency Phase 1", data[Config.Mon_InputFPhase1]+" Hz"));
            listData.add(new ModelData("Grid frequency Phase 2", data[Config.Mon_InputFPhase2]+" Hz"));
            listData.add(new ModelData("Grid frequency Phase 3", data[Config.Mon_InputFPhase3]+" Hz"));
            listData.add(new ModelData("Grid Current Phase 1", data[Config.Mon_InputCPhase1]+" Amps"));
            listData.add(new ModelData("Grid Current Phase 2", data[Config.Mon_InputCPhase2]+" Amps"));
            listData.add(new ModelData("Grid Current Phase 3", data[Config.Mon_InputCPhase3]+" Amps"));
            listData.add(new ModelData("Grid Power", data[Config.Mon_InputPower]+" KVA"));
            listData.add(new ModelData("Grid Energy", " KWHR"));
        }else if(value.equalsIgnoreCase("rectifier")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("Rectifier voltage" ,data[Config.Mon_RectifierVoltage]+" Vac"));
            listData.add(new ModelData("Rectifier Current", data[Config.Mon_RectifierCurrent]+" Amps"));
        }else if(value.equalsIgnoreCase("inverter")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("Inverter voltage" ,data[Config.Mon_InvVoltage]+" Vdc"));
            listData.add(new ModelData("Inverter Frequency", data[Config.Mon_InvFrequency]+" Hz"));
        }else if(value.equalsIgnoreCase("battery")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("Battery voltage" ,data[Config.Mon_BatterVol]+" Vdc"));
            listData.add(new ModelData("Battery Current", data[Config.Mon_BatChrgDiscCurrent]+" Amps"));
            listData.add(new ModelData("SOC", data[Config.Mon_BatterySOC]+" %"));
            listData.add(new ModelData("Battery Temp", data[Config.Mon_BatTemp]+" 'C"));
        }else if(value.equalsIgnoreCase("byPass")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("ByPass Source Voltage" ,data[Config.Mon_ByPassVoltage]+" Vac"));
            listData.add(new ModelData("ByPass Source Current", data[Config.Mon_ByPassSource]+" Hz"));
        }else if(value.equalsIgnoreCase("output")) {
            data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
            listData=new ArrayList<>();
            listData.add(new ModelData("Output Voltage" ,data[Config.Mon_OutputVoltage]+" Vac"));
            listData.add(new ModelData("Output Frequency", data[Config.Mon_OutputFrequency]+" Hz"));
            listData.add(new ModelData("Output Current", data[Config.Mon_OutputCurrent]+" Amps"));
            //listData.add(new ModelData("Load Percent", data[Config.Mon_ByPassVoltage]+"%"));
            listData.add(new ModelData("Output Power", data[Config.Mon_OutputPower]+" KVA"));
            //listData.add(new ModelData("Output Energy", data[Config.Mon_out]+"KWHr"));
            listData.add(new ModelData("Output P.F", data[Config.Mon_OutputPF]+" XX"));
        }
    }

    public void showGridToLoadFlow(){
        imgMCBGrid.setImageResource(R.drawable.close_connector);
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        seperatorMCBGrid.startAnimation();
        seperatorMCBByPassToSwitch.stopAnimation();
        seperatorGridPower_Switch.startAnimation();
        seperatorIsolation_Bypass.stopAnimation();
        batterySwitch.setImageResource(R.drawable.open_connector);
        seperatorSwitchBattery.stopAnimation();
        seperatorMCBByPassToIsolation.stopAnimation();
        seperatorInverter_STS.startAnimation();
        tView.stopAnimation();
        ((MainActivity)getActivity()).txtPowerMode.setText("UPS Mode");
        lView.blockByPass();
    }
    public void showGridToLoad_BatteryFlow(){
        imgMCBGrid.setImageResource(R.drawable.close_connector);
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        seperatorMCBGrid.startAnimation();
        seperatorMCBByPassToSwitch.stopAnimation();
        seperatorIsolation_Bypass.stopAnimation();
        seperatorGridPower_Switch.startAnimation();
        batterySwitch.setImageResource(R.drawable.close_connector);
        seperatorMCBByPassToIsolation.stopAnimation();
        seperatorInverter_STS.startAnimation();
        seperatorSwitchBattery.startGridToBatteryAnimation();
        tView.flowGridToBattery();
        ((MainActivity)getActivity()).txtPowerMode.setText("UPS Mode");
        lView.blockByPass();
    }

    public void showBatteryFlow(){
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        imgMCBGrid.setImageResource(R.drawable.open_connector);
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        batterySwitch.setImageResource(R.drawable.close_connector);
        seperatorMCBGrid.stopAnimation();
        seperatorGridPower_Switch.stopAnimation();
        seperatorMCBByPassToIsolation.stopAnimation();
        seperatorMCBByPassToSwitch.stopAnimation();
        seperatorIsolation_Bypass.stopAnimation();
        tView.flowBatteryToLoad();
        seperatorInverter_STS.startAnimation();
        seperatorSwitchBattery.startBatteryToLoadAnimation();
        ((MainActivity)getActivity()).txtPowerMode.setText("Battery Mode");
        lView.blockByPass();

    }

    public void showByPassFlowSwitch_Switch_Load(){
        imgMCBByPass.setImageResource(R.drawable.close_connector);
        imgMCBGrid.setImageResource(R.drawable.open_connector);
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        batterySwitch.setImageResource(R.drawable.open_connector);
        seperatorMCBGrid.stopAnimation();
        seperatorGridPower_Switch.stopAnimation();
        seperatorMCBByPassToIsolation.startAnimation();
        seperatorMCBByPassToSwitch.startAnimation();
        seperatorIsolation_Bypass.startAnimation();
        tView.stopAnimation();
        seperatorSwitchBattery.stopAnimation();
        seperatorInverter_STS.stopAnimation();
        ((MainActivity)getActivity()).txtPowerMode.setText("ByPass Mode");
        lView.byPassSwitch_Switch();
    }

    public void showByPassFlowSwitch_STS_Load(){
        imgMCBByPass.setImageResource(R.drawable.close_connector);
        imgMCBGrid.setImageResource(R.drawable.open_connector);
        imgMCBByPass.setImageResource(R.drawable.open_connector);
        batterySwitch.setImageResource(R.drawable.open_connector);
        seperatorMCBGrid.stopAnimation();
        seperatorGridPower_Switch.stopAnimation();
        seperatorMCBByPassToIsolation.startAnimation();
        seperatorMCBByPassToSwitch.startAnimation();
        seperatorIsolation_Bypass.startAnimation();
        tView.stopAnimation();
        seperatorSwitchBattery.stopAnimation();
        seperatorInverter_STS.stopAnimation();
        ((MainActivity)getActivity()).txtPowerMode.setText("ByPass Mode");
        lView.byPassToSTS_Load();
    }

    public void updatedData() {
        data=getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.MonitoringReading,((MainActivity)getActivity()).config.defaultMontoringData).split(",");
        try {
            Log.v("UpdatedData","aaa"+data[19]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(data[19].equalsIgnoreCase("1") && data[32].equalsIgnoreCase("1")){
            Log.v("UpdatedData=","data[19] "+ data[19] +" data[32]   "+data[32]);
            showGridToLoad_BatteryFlow();
        }
        else if(data[19].equalsIgnoreCase("1")){
            Log.v("UpdatedData=","data[19] "+ data[19]);
            showGridToLoadFlow();
        }
        else if(data[32].equalsIgnoreCase("1")){
            Log.v("UpdatedData=","data[32] "+ data[32]);
            showBatteryFlow();
        }
        else if(data[49].equalsIgnoreCase("1")){
            Log.v("UpdatedData=","data[49] "+ data[49]);

            showByPassFlowSwitch_STS_Load();
        }

        if(data[43].equalsIgnoreCase("1")){
            ((MainActivity)getActivity()).txtSyncStatus.setText("IN SYNC");
            ((MainActivity)getActivity()).txtSyncStatus.setBackgroundResource(R.drawable.drawable_round_green);
        }else{
            ((MainActivity)getActivity()).txtSyncStatus.setText("OUT OF SYNC");
            ((MainActivity)getActivity()).txtSyncStatus.setBackgroundResource(R.drawable.drawable_round);

        }
    }
}