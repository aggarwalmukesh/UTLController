package com.app.utlcontroller.Controller;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.app.utlcontroller.Adapter.DisplayAdapter;
import com.app.utlcontroller.Adapter.DisplayAdapter_BigFont;
import com.app.utlcontroller.Adapter.DisplayAdapter_CheckBox;
import com.app.utlcontroller.Adapter.DisplayAdapter_Step;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelData_CheckBox;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.R;
import com.app.utlcontroller.services.BluetoothService;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by Mints on 5/5/2017.
 */

public class ConfigurationFragment extends Fragment {

    ArrayList<ModelData> listSystem = new ArrayList<>();
    ArrayList<ModelData> listAlarm = new ArrayList<>();
    ArrayList<Model_Step_Checkbox> listBatteryAlarms = new ArrayList<>();
    ArrayList<ModelData_CheckBox> listAlarmsCheckBox = new ArrayList<>();
    ListView listViewSystem, listViewAlarm, layout_system_listview;
    View secondaryLayout, thirdLayout;
    View restoreSettingsSecondary;
    Dialog d;
    View bottomLayout, txtSave;
    boolean isLeadBattery = true;
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm a");
    View layout_system_layout;
    TextView txtHeader_layout_system;
    String eventHeaderNames = "12/03/2017,12:22,220,1,F1,P2,CP2,FP2,VP3,CP3,FP3,RT,BV,BC,DC,SOC,BT,IV,IC,IF,IT,SYNC Status,BV,BF,BS,OV,OC,OF,EName";


    Model_Step_Checkbox modelWithStep;
    ModelData_CheckBox modelWithCheckBox;
    ModelData modelData;
    private DisplayAdapter_Step adapterWithStep;

    public int module;
    String[] configurationData, tempConfigurationData;
    private StringBuffer resultString = new StringBuffer();


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_configuration, null);
        listViewAlarm = (ListView) v.findViewById(R.id.listViewAlarm);
        configurationData = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.configurationData_Format).split(",");
        tempConfigurationData = new String[configurationData.length];

        //  Config.generateEventLogFile(eventHeaderNames);
        v.findViewById(R.id.imgBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
        listViewSystem = (ListView) v.findViewById(R.id.listViewSettings);
        layout_system_listview = (ListView) v.findViewById(R.id.layout_system_listview);
        secondaryLayout = v.findViewById(R.id.layoutSecondary);
        thirdLayout = v.findViewById(R.id.thirdLayout);
        layout_system_layout = v.findViewById(R.id.layout_system_layout);
        bottomLayout = v.findViewById(R.id.bottomLayout);
        txtSave = v.findViewById(R.id.txtSaveMain);
        restoreSettingsSecondary = v.findViewById(R.id.restoreSettingsSecondary);
        txtHeader_layout_system = (TextView) v.findViewById(R.id.txtHeader_layout_system);
        displaySecondaryLayout();

        v.findViewById(R.id.imgIncrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseValue();
            }
        });

        v.findViewById(R.id.imgDecrement).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseValue();
            }
        });

        restoreSettingsSecondary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restoreDefault();
            }
        });
        return v;
    }

    private void showDatePickerDialog() {
        d = new Dialog(getActivity());
        View v = getActivity().getLayoutInflater().inflate(R.layout.layout_selectdatetime, null);
        d.setContentView(v);
        d.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });

        final DatePicker datePicker = (DatePicker) v.findViewById(R.id.datePicker);
        final TimePicker timePicker = (TimePicker) v.findViewById(R.id.timePicker);


        d.getWindow().hasFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

        d.show();
        d.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                Calendar c = Calendar.getInstance();
                c.set(datePicker.getYear(), datePicker.getMonth(), datePicker.getDayOfMonth(), timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                //editTimeDate.setText(sdf.format(c.getTime()));
                listBatteryAlarms.get(2).type1Value = (sdf.format(c.getTime()));
                d.dismiss();
                adapterWithStep.notifyDataSetChanged();
            }
        });
    }

    private void restoreDefault() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());
        ab.setMessage("Are you sure to restore default? ");
        ab.setTitle("Restore Default? ");

        ab.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.Configuration_Reading, Config.configurationData_Format).commit();
                configurationData = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.configurationData_Format).split(",");
                if (configurationData[Config.Conf_Battery_Type_Index].toUpperCase().equalsIgnoreCase("LA")) {
                    isLeadBattery = true;
                } else {
                    isLeadBattery = false;
                }
            }
        });

        ab.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        d = ab.create();
        d.show();
    }

    private void prepareBatteryAlarmData() {
        listBatteryAlarms.clear();
        copyConfigurationData();

        if (isLeadBattery) {
            listBatteryAlarms.add(new Model_Step_Checkbox("High Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_High_Voltage_Index]), " V/Cell", 2.20f, 2.69f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Low Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_Low_Voltage_Index]), " V/Cell", 1.78f, 2.17f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Battery Over Temp", Float.parseFloat(tempConfigurationData[Config.Conf_Battery_over_temp_Index]), " " + (char) 0x00B0 + "C", 50, 90, 1f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("End of Discharge", Float.parseFloat(tempConfigurationData[Config.Conf_End_Discharge_Index]), " V/Cell", 1.60f, 1.84f, 0.01f, 2));
        } else {
            listBatteryAlarms.add(new Model_Step_Checkbox("High Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_High_Voltage_Index]), " V/Cell", 1.44f, 1.75f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Low Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_Low_Voltage_Index]), " V/Cell", 1.08f, 1.77f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Battery Over Temp", Float.parseFloat(tempConfigurationData[Config.Conf_Battery_over_temp_Index]), " " + (char) 0x00B0 + "C", 50, 90, 1f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("End of Discharge", Float.parseFloat(tempConfigurationData[Config.Conf_End_Discharge_Index]), " V/Cell", 0.95f, 1.09f, 0.01f, 2));
        }
        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestBatteryAlarmsSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "BatteryAlarm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void copyConfigurationData() {
        String[] data;

        if(isLeadBattery) {
            data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_LEAD, Config.configurationData_LEAD).split(",");
        }else {
            data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_NI, Config.configurationData_NI).split(",");
        }

        configurationData[Config.Conf_High_Voltage_Index] = data[Config.Conf_High_Voltage_Index];
        configurationData[Config.Conf_Low_Voltage_Index]=data[Config.Conf_Low_Voltage_Index];
        configurationData[Config.Conf_Battery_over_temp_Index]=data[Config.Conf_Battery_over_temp_Index];
        configurationData[Config.Conf_End_Discharge_Index]=data[Config.Conf_End_Discharge_Index];
        configurationData[Config.Conf_Battery_Cells_Index]=data[Config.Conf_Battery_Cells_Index];
        configurationData[Config.Conf_FloatVoltage_Index]=data[Config.Conf_FloatVoltage_Index];
        configurationData[Config.Conf_Equalize_Voltage_Index] = data[Config.Conf_Equalize_Voltage_Index];
        configurationData[Config.Conf_FloatVoltage_Index] = data[Config.Conf_FloatVoltage_Index];
        configurationData[Config.Conf_High_Voltage_Index] = data[Config.Conf_High_Voltage_Index];
        configurationData[Config.Conf_Low_Voltage_Index] = data[Config.Conf_Low_Voltage_Index];
        configurationData[Config.Conf_Battery_over_temp_Index] = data[Config.Conf_Battery_over_temp_Index];
        configurationData[Config.Conf_End_Discharge_Index] = data[Config.Conf_End_Discharge_Index];
        configurationData[Config.Conf_High_DC_Volt_Shutdown] = data[Config.Conf_High_DC_Volt_Shutdown];

        for (int i = 0; i < configurationData.length; i++) {
            tempConfigurationData[i] = configurationData[i];
        }
    }

    private void prepareRectifierAlarmData() {
        copyConfigurationData();
        listBatteryAlarms.clear();
        listBatteryAlarms.add(new Model_Step_Checkbox("AC Fail Upper", Float.parseFloat(tempConfigurationData[Config.Conf_AC_Fail_UPPER_Index]), " %", 5, 10, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("AC Fail Lower", Float.parseFloat(tempConfigurationData[Config.Conf_AC_Fail_2_Index]), " %", 5, 15, 1, 0));
        if (isLeadBattery) {
            listBatteryAlarms.add(new Model_Step_Checkbox("High DC Shut Down", Float.parseFloat(tempConfigurationData[Config.Conf_High_DC_Volt_Shutdown]), " V/Cell", 2.40f, 2.74f, 0.01f, 2));
        } else {
            listBatteryAlarms.add(new Model_Step_Checkbox("High DC Shut Down", Float.parseFloat(tempConfigurationData[Config.Conf_High_DC_Volt_Shutdown]), " V/Cell", 1.45f, 1.79f, 0.01f, 2));
        }
        listBatteryAlarms.add(new Model_Step_Checkbox("Low DC Current", Float.parseFloat(tempConfigurationData[Config.Conf_Low_DC_Current_Index]), " Amps", 0.1f, 2.0f, 0.1f, 1));
        listBatteryAlarms.add(new Model_Step_Checkbox("Rectifier Over Temp", Float.parseFloat(tempConfigurationData[Config.Conf_Rectifier_over_temp_Index]), " " + (char) 0x00B0 + "C", 50, 90, 1, 0));
        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestRectifierSettings();
                saveSettings();

                //Toast.makeText(getActivity(), "BatteryAlarm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLatestBatteryAlarmsSettings() {
        tempConfigurationData[Config.Conf_High_Voltage_Index] = (listBatteryAlarms.get(0).stepSize>=1 ?(int)listBatteryAlarms.get(0).value:listBatteryAlarms.get(0).value) + "";
        tempConfigurationData[Config.Conf_Low_Voltage_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(1).value) + "";
        tempConfigurationData[Config.Conf_Battery_over_temp_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
        tempConfigurationData[Config.Conf_End_Discharge_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(3).value) + "";
    }

    private void getLatestRectifierSettings() {
        tempConfigurationData[Config.Conf_AC_Fail_UPPER_Index] = (listBatteryAlarms.get(0).stepSize>=1 ?(int)listBatteryAlarms.get(0).value:listBatteryAlarms.get(0).value) + "";
        tempConfigurationData[Config.Conf_AC_Fail_2_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(1).value) + "";
        tempConfigurationData[Config.Conf_High_DC_Volt_Shutdown] = NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
        tempConfigurationData[Config.Conf_Low_DC_Current_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(3).value) + "";
        tempConfigurationData[Config.Conf_Rectifier_over_temp_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(4).value) + "";
    }

    private void prepareInverterAlarmData() {
        copyConfigurationData();
        listBatteryAlarms.clear();
        listBatteryAlarms.add(new Model_Step_Checkbox("Low AC Output", Float.parseFloat(tempConfigurationData[Config.Conf_Low_AC_Output_Index]), "%", -15, -5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("High AC Output", Float.parseFloat(tempConfigurationData[Config.Conf_High_AC_Output_Index]), "%", 5, 10, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Inverter Over Temperature", Float.parseFloat(tempConfigurationData[Config.Conf_Inverter_Overtemp_Index]), " " + (char) 0x00B0 + "C", 50, 90, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Current Limit", Float.parseFloat(tempConfigurationData[Config.Conf_Current_Limit_Index]), "%", 70, 115, 1, 0));

        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestInverterAlarmsSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "Inverter Alarm", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLatestInverterAlarmsSettings() {
        tempConfigurationData[Config.Conf_Low_AC_Output_Index] = (listBatteryAlarms.get(0).stepSize>=1 ?(int)listBatteryAlarms.get(0).value:listBatteryAlarms.get(0).value) + "";
        tempConfigurationData[Config.Conf_High_AC_Output_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(1).value) + "";
        tempConfigurationData[Config.Conf_Inverter_Overtemp_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
        tempConfigurationData[Config.Conf_Current_Limit_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(3).value) + "";
    }

    private void prepareStaticSwitchData() {
        copyConfigurationData();
        listBatteryAlarms.clear();
        Log.v("data=", Arrays.asList(configurationData) + "");
        listBatteryAlarms.add(new Model_Step_Checkbox("Load voltage high limit", Float.parseFloat(tempConfigurationData[Config.Conf_Load_Voltage_High_Limit_Index]), "%", 5, 20, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Load voltage Low Limit", Float.parseFloat(tempConfigurationData[Config.Conf_Load_Voltage_Low_LIMIT_Index]), "%", -20f, -5f, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Inverter voltage range", Float.parseFloat(tempConfigurationData[Config.Conf_Inverter_Voltage_Range_Index].trim()), "%", 0, 5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Bypass voltage range", Float.parseFloat(tempConfigurationData[Config.Conf_Bypass_Voltage_RangeIndex].trim()), "%", 1, 5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Inverter Frequency range", Float.parseFloat(tempConfigurationData[Config.Conf_Inverter_Frequency_Range_Index]), "%", 1, 5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Bypass Frequency range", Float.parseFloat(tempConfigurationData[Config.Conf_Bypass_Frequency_Range_Index]), "%", 1, 5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Out of Sync check", Float.parseFloat(tempConfigurationData[Config.Conf_Out_of_Sync_Check_Index]), " Degree", 0, 10, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Transfer delay", Float.parseFloat(tempConfigurationData[Config.Conf_Retransfer_Delay_Index]), " Sec", 0, 60, 1, 0));
        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestStaticSwitchSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "Static Switch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareCommunicationData() {
        copyConfigurationData();
        listBatteryAlarms.clear();
        listBatteryAlarms.add(new Model_Step_Checkbox("Address", Float.parseFloat(tempConfigurationData[Config.Conf_Load_Voltage_High_Limit_Index]), "", 1, 255, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Baudrate", 0, "", new String[]{"9600", "14400", "115200"}));
        listBatteryAlarms.add(new Model_Step_Checkbox("Parity", 2, "", new String[]{"EVEN", "ODD", "NONE"}));
        listBatteryAlarms.add(new Model_Step_Checkbox("Stopbits", 0, "", new String[]{"1", "1.5", "2"}));


        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });

        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestCommunicationSwitchSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "communication Switch", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getLatestStaticSwitchSettings() {
        try {
            Log.v("High voltage=",listBatteryAlarms.get(0).stepSize+"   "+(listBatteryAlarms.get(0).stepSize>=1)+"  "+ NumberFormat.getInstance().format(listBatteryAlarms.get(0).value)+"");
            tempConfigurationData[Config.Conf_Load_Voltage_High_Limit_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(0).value) + "";
            tempConfigurationData[Config.Conf_Load_Voltage_Low_LIMIT_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(1).value) + "";
            tempConfigurationData[Config.Conf_Inverter_Voltage_Range_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
            tempConfigurationData[Config.Conf_Bypass_Voltage_RangeIndex] = NumberFormat.getInstance().format(listBatteryAlarms.get(3).value) + "";
            tempConfigurationData[Config.Conf_Inverter_Frequency_Range_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(4).value) + "";
            tempConfigurationData[Config.Conf_Bypass_Frequency_Range_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(5).value) + "";
            tempConfigurationData[Config.Conf_Out_of_Sync_Check_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(6).value) + "";
            tempConfigurationData[Config.Conf_Retransfer_Delay_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(7).value) + "";
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLatestCommunicationSwitchSettings() {
        try {
            tempConfigurationData[Config.Conf_Load_Voltage_High_Limit_Index] = listBatteryAlarms.get(0).value + "";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLatestSystemSettings() {
        try {
            tempConfigurationData[Config.Conf_SystemNameIndex] = listBatteryAlarms.get(0).type1Value;
            tempConfigurationData[Config.Conf_Location_Index] = listBatteryAlarms.get(1).type1Value;
            tempConfigurationData[Config.Conf_Time_Date_Index] = listBatteryAlarms.get(2).type1Value;
            //tempConfigurationData[Config.Conf_Time_Date_Index] = listBatteryAlarms.get(2).type1Value;
            Settings.System.putInt(getActivity().getContentResolver(),
                    Settings.System.SCREEN_OFF_TIMEOUT, (int) (listBatteryAlarms.get(3).value * 60000));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getLatest_SystemInverterSettings() {
        try {
            try {
                tempConfigurationData[Config.Conf_intOutput_Voltage_Index] = (listBatteryAlarms.get(0).stepSize>=1 ?(int)listBatteryAlarms.get(0).value:listBatteryAlarms.get(0).value) + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
            /*try {
                tempConfigurationData[Config.Conf_Location_Index] = listBatteryAlarms.get(1).status ? "1" : "2";
            } catch (Exception e) {
                e.printStackTrace();
            }*/
            try {
                tempConfigurationData[Config.Conf_Output_Phase_Shift] = NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLatest_SystemBatterySettings() {
        try {
            tempConfigurationData[Config.Conf_Battery_Type_Index] = listBatteryAlarms.get(0).status ? "NI" : "LA";
            tempConfigurationData[Config.Conf_Battery_Cells_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(1).value) + "";;
            tempConfigurationData[Config.Conf_Battery_capacity_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(2).value) + "";
            tempConfigurationData[Config.Conf_NumStrings_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(3).value) + "";
            tempConfigurationData[Config.Conf_FloatVoltage_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(4).value) + "";
            tempConfigurationData[Config.Conf_Equalize_Voltage_Index] =NumberFormat.getInstance().format(listBatteryAlarms.get(5).value) + "";
            tempConfigurationData[Config.Conf_Equalize_Timer_Hours_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(6).value) + "";
            tempConfigurationData[Config.Conf_Equalize_Timer_Mode_Index] = listBatteryAlarms.get(7).status ? "P1" : "P2";
            tempConfigurationData[Config.Conf_Enable_Remote_Eq_Index] = listBatteryAlarms.get(8).status ? "1" : "0";
            tempConfigurationData[Config.Conf_Temperature_Compensation_Index] = NumberFormat.getInstance().format(listBatteryAlarms.get(9).value) + "";
            tempConfigurationData[Config.Conf_Low_Voltage_Shut_Index] = listBatteryAlarms.get(10).status ? "1" : "0";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareUPSSystemAlarmData() {
        copyConfigurationData();
        listAlarmsCheckBox.clear();
        listAlarmsCheckBox.add(new ModelData_CheckBox("AC Input Breaker Trip", "Enable", "Disable", (tempConfigurationData[Config.Conf_AC_Input_Breaker_Trip_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Chg. Output Breaker Trip", "Enable", "Disable", (tempConfigurationData[Config.Conf_Charger_Output_Breaker_Trip_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Battery Breaker Trip", "Enable", "Disable", (tempConfigurationData[Config.Conf_Battery_breaker_trip_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("AC Output Breaker Trip", "Enable", "Disable", (tempConfigurationData[Config.Conf_AC_Output_Breaker_Trip_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("ByPass Breaker Trip", "Enable", "Disable", (tempConfigurationData[Config.Conf_Bypass_Breaker_Trip]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("LCD Fail", "Enable", "Disable", (tempConfigurationData[Config.Conf_LCD_Fail_Index]).contains("1")));

        layout_system_listview.setAdapter(new DisplayAdapter_CheckBox(getActivity(), listAlarmsCheckBox, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithCheckBox = listAlarmsCheckBox.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        }));

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestUpsSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "UPS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLatestUpsSettings() {
        try {
            if (listAlarmsCheckBox.get(0).status) {
                tempConfigurationData[Config.Conf_AC_Input_Breaker_Trip_Index] = "1";
            } else {
                tempConfigurationData[Config.Conf_AC_Input_Breaker_Trip_Index] = "0";
            }

            if (listAlarmsCheckBox.get(1).status) {
                tempConfigurationData[Config.Conf_Charger_Output_Breaker_Trip_Index] = "1";
            } else {
                tempConfigurationData[Config.Conf_Charger_Output_Breaker_Trip_Index] = "0";
            }

            if (listAlarmsCheckBox.get(2).status) {
                tempConfigurationData[Config.Conf_Battery_breaker_trip_Index] = "1";
            } else {
                tempConfigurationData[Config.Conf_Battery_breaker_trip_Index] = "0";
            }

            if (listAlarmsCheckBox.get(3).status) {
                tempConfigurationData[Config.Conf_AC_Output_Breaker_Trip_Index] = "1";
            } else {
                tempConfigurationData[Config.Conf_AC_Output_Breaker_Trip_Index] = "0";
            }

            if (listAlarmsCheckBox.get(4).status) {
                tempConfigurationData[Config.Conf_Bypass_Breaker_Trip] = "1";
            } else {
                tempConfigurationData[Config.Conf_Bypass_Breaker_Trip] = "0";
            }

            if (listAlarmsCheckBox.get(5).status) {
                tempConfigurationData[Config.Conf_LCD_Fail_Index] = "1";
            } else {
                tempConfigurationData[Config.Conf_LCD_Fail_Index] = "0";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void prepareSummaryAlarmData() {
        copyConfigurationData();
        listAlarmsCheckBox.clear();
        listAlarmsCheckBox.add(new ModelData_CheckBox("AC Fail", "Yes", "No", (tempConfigurationData[Config.Conf_AC_Fail_2_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Low DC Current", "Yes", "No", (tempConfigurationData[Config.Conf_Low_DC_Current_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Overload", "Yes", "No", (tempConfigurationData[Config.Conf_Overload_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Ground Detection", "Yes", "No", (tempConfigurationData[Config.Conf_Ground_Detection_Index]).contains("1")));
        listAlarmsCheckBox.add(new ModelData_CheckBox("Battery Over Temp", "Yes", "No", (tempConfigurationData[Config.Conf_Battery_Over_Temperature_Index]).contains("1")));

        layout_system_listview.setAdapter(new DisplayAdapter_CheckBox(getActivity(), listAlarmsCheckBox, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithCheckBox = listAlarmsCheckBox.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        }));

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestSummaryData();
                saveSettings();
                //Toast.makeText(getActivity(), "Summary", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getLatestSummaryData() {
        if (listAlarmsCheckBox.get(0).status) {
            tempConfigurationData[Config.Conf_AC_Fail_2_Index] = "1";
        } else {
            tempConfigurationData[Config.Conf_AC_Fail_2_Index] = "0";
        }

        if (listAlarmsCheckBox.get(1).status) {
            tempConfigurationData[Config.Conf_Low_DC_Current_Index] = "1";
        } else {
            tempConfigurationData[Config.Conf_Low_DC_Current_Index] = "0";
        }

        if (listAlarmsCheckBox.get(2).status) {
            tempConfigurationData[Config.Conf_Overload_Index] = "1";
        } else {
            tempConfigurationData[Config.Conf_Overload_Index] = "0";
        }

        if (listAlarmsCheckBox.get(3).status) {
            tempConfigurationData[Config.Conf_Ground_Detection_Index] = "1";
        } else {
            tempConfigurationData[Config.Conf_Ground_Detection_Index] = "0";
        }

        if (listAlarmsCheckBox.get(4).status) {
            tempConfigurationData[Config.Conf_Battery_Over_Temperature_Index] = "1";
        } else {
            tempConfigurationData[Config.Conf_Battery_Over_Temperature_Index] = "0";
        }

    }

    private void prepareSystemAlarmData() {
        listSystem.clear();
        listSystem.add(new ModelData("SYSTEM", ""));
        listSystem.add(new ModelData("INVERTER", ""));
        listSystem.add(new ModelData("BATTERY", ""));
        listSystem.add(new ModelData("STATIC SWITCH", ""));
        listSystem.add(new ModelData("COMMUNICATION", ""));

        listAlarm.clear();
        listAlarm.add(new ModelData("BATTERY ALARM", ""));
        listAlarm.add(new ModelData("RECIFIER ALARM", ""));
        listAlarm.add(new ModelData("INVERTER ALARM", ""));
        listAlarm.add(new ModelData("UPS SYSTEM ALARM", ""));
        listAlarm.add(new ModelData("INCL. IN SUMMARY", ""));
        listViewSystem.setAdapter(new DisplayAdapter_BigFont(getActivity(), listSystem, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                Log.v("Item Clicked= ", listSystem.get(position).name);
                thirdLayout.setVisibility(View.VISIBLE);
                secondaryLayout.setVisibility(View.GONE);

                if (position == 0) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS");
                    module = Config.SYSTEM_SETTING;
                    try {
                        prepareSystemSettingsData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else if (position == 1) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / INVERTER");
                    module = Config.INVERTER_SETTING;
                    try {
                        prepareSystemInverterData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 2) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / BATTERY");
                    module = Config.BATTERY_SETTING;
                    try {
                        prepareSystemBatteryData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 3) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / STATIC SWITCH");
                    module = Config.STATIC_SWITCH;
                    try {
                        prepareStaticSwitchData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 4) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / COMMUNICATION");
                    module = Config.COMMUNICATION_SETTING;
                    try {
                        prepareCommunicationData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        }));

        listViewAlarm.setAdapter(new DisplayAdapter_BigFont(getActivity(), listAlarm, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                Log.v("Item Clicked= ", listAlarm.get(position).name);
                thirdLayout.setVisibility(View.VISIBLE);
                secondaryLayout.setVisibility(View.GONE);
                if (position == 0) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / BATTERY ALARM");
                    module = Config.BATTERY_ALARM;
                    try {
                        prepareBatteryAlarmData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 1) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / RECTIFIER ALARM");
                    module = Config.RECTIFIER_ALARM;
                    try {
                        prepareRectifierAlarmData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 2) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / INVERTER ALARM");
                    module = Config.INVERTER_ALARM;
                    try {
                        prepareInverterAlarmData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 3) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / UPS SYSTEM ALARM");
                    module = Config.UPS_ALARM;
                    try {
                        prepareUPSSystemAlarmData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (position == 4) {
                    layout_system_layout.setVisibility(View.VISIBLE);
                    txtHeader_layout_system.setText("SYSTEM SETTINGS / INCL. IN SUMMARY");
                    module = Config.SUMMARY_ALARM;
                    try {
                        prepareSummaryAlarmData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        }));
    }

    private void saveSettings() {

        try {
            resultString.delete(0, resultString.length());
            for (int i = 0; i < tempConfigurationData.length; i++) {
                if (i == tempConfigurationData.length - 1) {
                    resultString.append(tempConfigurationData[i]);
                } else {
                    resultString.append(tempConfigurationData[i] + ",");
                }
            }
            for (int i = 0; i < tempConfigurationData.length; i++) {
                configurationData[i] = tempConfigurationData[i];
            }
            getActivity().getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.Configuration_Reading, resultString.toString()).commit();

            saveLead_NISettings();


            if (configurationData[Config.Conf_Battery_Type_Index].toUpperCase().equalsIgnoreCase("LA")) {
                isLeadBattery = true;
            } else {
                isLeadBattery = false;
            }

            if (BluetoothService.getInstance() != null) {
                Log.v("Command=  ", resultString + "");
                byte[] data = resultString.toString().getBytes();
                if (BluetoothService.getInstance() != null && BluetoothService.getInstance().mConnectedThread != null) {
                    BluetoothService.getInstance().mConnectedThread.write(data);

                    // getActivity().getSharedPreferences(Config.Prefernces, 0).edit().putString(Config.Configuration_Reading, resultString.toString()).commit();
                } else {
                    // Toast.makeText(getActivity(), "Please connect with device to apply settings", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        displaySecondaryLayout();
    }

    private void saveLead_NISettings() {
        String[] data;
        if(isLeadBattery) {
            data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_LEAD, Config.configurationData_LEAD).split(",");
        }else {
            data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_NI, Config.configurationData_NI).split(",");
        }

        data[Config.Conf_High_Voltage_Index] = configurationData[Config.Conf_High_Voltage_Index];
        data[Config.Conf_Low_Voltage_Index] = configurationData[Config.Conf_Low_Voltage_Index];
        data[Config.Conf_Battery_over_temp_Index] = configurationData[Config.Conf_Battery_over_temp_Index];
        data[Config.Conf_End_Discharge_Index] = configurationData[Config.Conf_End_Discharge_Index];
        data[Config.Conf_Battery_Cells_Index] = configurationData[Config.Conf_Battery_Cells_Index];
        data[Config.Conf_FloatVoltage_Index] = configurationData[Config.Conf_FloatVoltage_Index];
        data[Config.Conf_Equalize_Voltage_Index] = configurationData[Config.Conf_Equalize_Voltage_Index];
        data[Config.Conf_FloatVoltage_Index] = configurationData[Config.Conf_FloatVoltage_Index];
        data[Config.Conf_High_Voltage_Index] = configurationData[Config.Conf_High_Voltage_Index];
        data[Config.Conf_Low_Voltage_Index] = configurationData[Config.Conf_Low_Voltage_Index];
        data[Config.Conf_Battery_over_temp_Index] = configurationData[Config.Conf_Battery_over_temp_Index];
        data[Config.Conf_End_Discharge_Index] = configurationData[Config.Conf_End_Discharge_Index];
        data[Config.Conf_High_DC_Volt_Shutdown] = configurationData[Config.Conf_High_DC_Volt_Shutdown];

        resultString.delete(0, resultString.length());

        for (int i = 0; i < tempConfigurationData.length; i++) {
            if (i == tempConfigurationData.length - 1) {
                resultString.append(tempConfigurationData[i]);
            } else {
                resultString.append(tempConfigurationData[i] + ",");
            }
        }

        getActivity().getSharedPreferences(Config.Prefernces, 0).edit().putString(isLeadBattery?Config.Configuration_Reading_LEAD:Config.Configuration_Reading_NI, resultString.toString()).commit();

    }


    private void prepareSystemBatteryData() {
        copyConfigurationData();

        Log.v("IsLead Battery", isLeadBattery + "");
        listBatteryAlarms.clear();

        listBatteryAlarms.add(new Model_Step_Checkbox("Battery/Cell Type", "NiCad", "Lead Acid", !isLeadBattery));
        if (isLeadBattery) {
            listBatteryAlarms.add(new Model_Step_Checkbox("Battery Cells/String", Float.parseFloat(tempConfigurationData[Config.Conf_Battery_Cells_Index]), "", 120, 122, 1, 0));
        } else {
            listBatteryAlarms.add(new Model_Step_Checkbox("Battery Cells/String", Float.parseFloat(tempConfigurationData[Config.Conf_Battery_Cells_Index]), "", 180, 186, 1, 0));
        }
        listBatteryAlarms.add(new Model_Step_Checkbox("Battery Capacity", tempConfigurationData[Config.Conf_Battery_capacity_Index], true,"DECIMAL NUMBER",""));


        //listBatteryAlarms.add(new Model_Step_Checkbox("Battery Capacity", Float.parseFloat(tempConfigurationData[Config.Conf_Battery_capacity_Index]), " AH", 10, 250, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("No. of String", Float.parseFloat(tempConfigurationData[Config.Conf_NumStrings_Index]), "", 1, 10, 1, 0));

        if (isLeadBattery) {
            listBatteryAlarms.add(new Model_Step_Checkbox("Float Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_FloatVoltage_Index]), " Volt/Cell", 2.12f, 2.30f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Equalize Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_Equalize_Voltage_Index]), " Volt/Cell", 2.25f, 2.40f, 0.01f, 2));
        } else {
            listBatteryAlarms.add(new Model_Step_Checkbox("Float Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_FloatVoltage_Index]), " Volt/Cell", 1.39f, 1.45f, 0.01f, 2));
            listBatteryAlarms.add(new Model_Step_Checkbox("Equalize Voltage", Float.parseFloat(tempConfigurationData[Config.Conf_Equalize_Voltage_Index]), " Volt/Cell", 1.5f, 1.60f, 0.01f, 2));
        }

        listBatteryAlarms.add(new Model_Step_Checkbox("Equalize Timer", Float.parseFloat(tempConfigurationData[Config.Conf_Equalize_Timer_Hours_Index]), " Hour", 1, 24, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Equalize Mode", 0, "", new String[]{"Daily", "7 Days", "14 Days","30 Days"}));
        int position=0;
        try {
            position=Integer.parseInt(tempConfigurationData[Config.Conf_Equalize_Timer_Mode_Index]);
            if(position>4){
                position=0;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        listBatteryAlarms.add(new Model_Step_Checkbox("Start Equalize Cycle","button"));

        //listBatteryAlarms.add(new Model_Step_Checkbox("Equalize Mode", "P1", "P2", tempConfigurationData[Config.Conf_Equalize_Timer_Mode_Index].toUpperCase().contains(" LA")));
        listBatteryAlarms.add(new Model_Step_Checkbox("Remote Equalize", "Enabled", "Disabled", tempConfigurationData[Config.Conf_Equalize_Timer_Mode_Index].contains("1")));
        listBatteryAlarms.add(new Model_Step_Checkbox("Temp Comp. Rate", Float.parseFloat(tempConfigurationData[Config.Conf_Temperature_Compensation_Index]), "", 1, 5, 1, 0));
        listBatteryAlarms.add(new Model_Step_Checkbox("Low Voltage Shut Down", "Enable", "Disable", true));  //pending

        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
                String[] data;

                if (position == 0) {
                    if (listBatteryAlarms.get(0).status) {
                        data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_LEAD, Config.configurationData_LEAD).split(",");
                        isLeadBattery = false;
                    } else {
                        data = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading_NI, Config.configurationData_NI).split(",");
                        isLeadBattery = true;
                    }

                    data[Config.Conf_High_Voltage_Index] = configurationData[Config.Conf_High_Voltage_Index];
                    data[Config.Conf_Low_Voltage_Index] = configurationData[Config.Conf_Low_Voltage_Index];
                    data[Config.Conf_Battery_over_temp_Index] = configurationData[Config.Conf_Battery_over_temp_Index];
                    data[Config.Conf_End_Discharge_Index] = configurationData[Config.Conf_End_Discharge_Index];
                    data[Config.Conf_Battery_Cells_Index] = configurationData[Config.Conf_Battery_Cells_Index];
                    data[Config.Conf_FloatVoltage_Index] = configurationData[Config.Conf_FloatVoltage_Index];
                    data[Config.Conf_Equalize_Voltage_Index] = configurationData[Config.Conf_Equalize_Voltage_Index];
                    data[Config.Conf_FloatVoltage_Index] = configurationData[Config.Conf_FloatVoltage_Index];
                    data[Config.Conf_High_Voltage_Index] = configurationData[Config.Conf_High_Voltage_Index];
                    data[Config.Conf_Low_Voltage_Index] = configurationData[Config.Conf_Low_Voltage_Index];
                    data[Config.Conf_Battery_over_temp_Index] = configurationData[Config.Conf_Battery_over_temp_Index];
                    data[Config.Conf_End_Discharge_Index] = configurationData[Config.Conf_End_Discharge_Index];
                    data[Config.Conf_High_DC_Volt_Shutdown] = configurationData[Config.Conf_High_DC_Volt_Shutdown];

                    resultString.delete(0, resultString.length());

                    for (int i = 0; i < tempConfigurationData.length; i++) {
                        if (i == tempConfigurationData.length - 1) {
                            resultString.append(tempConfigurationData[i]);
                        } else {
                            resultString.append(tempConfigurationData[i] + ",");
                        }
                    }

                    getActivity().getSharedPreferences(Config.Prefernces, 0).edit().putString(isLeadBattery?Config.Configuration_Reading_NI:Config.Configuration_Reading_LEAD, resultString.toString()).commit();
                    Log.v("IsLead Battery", isLeadBattery + "");
                    prepareSystemBatteryData();
                }else if(position==8){
                    Config.testCommand(getActivity(),"START EQUALIZE");
                }
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatest_SystemBatterySettings();
                saveSettings();
                //Toast.makeText(getActivity(), "System Battery Settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareSystemSettingsData() {
        copyConfigurationData();

        listBatteryAlarms.clear();
        listBatteryAlarms.add(new Model_Step_Checkbox("Name/ID", tempConfigurationData[Config.Conf_SystemNameIndex], true));
        listBatteryAlarms.add(new Model_Step_Checkbox("System Location", tempConfigurationData[Config.Conf_Location_Index], true));
        listBatteryAlarms.add(new Model_Step_Checkbox("Time and Date", tempConfigurationData[Config.Conf_Time_Date_Index], false));
        listBatteryAlarms.add(new Model_Step_Checkbox("Display Timeout", 20, " Min", 0, 60, 5, 0));
        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
                if (position == 2) {
                    showDatePickerDialog();
                } else if (position == 3) {
                    android.provider.Settings.System.putInt(getActivity().getContentResolver(),
                            Settings.System.SCREEN_OFF_TIMEOUT, (int) (listBatteryAlarms.get(position).value * 60000));
                    // modelWithStep = listBatteryAlarms.get(position);
                }
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatestSystemSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "System Settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void prepareSystemInverterData() {
        copyConfigurationData();

        listBatteryAlarms.clear();
        try {
            listBatteryAlarms.add(new Model_Step_Checkbox("Output Voltage", Float.parseFloat((tempConfigurationData[Config.Conf_intOutput_Voltage_Index].trim().length() > 0) ? tempConfigurationData[Config.Conf_intOutput_Voltage_Index].trim() : "110"), " Vac", 110, 130, 10, 0));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {
            listBatteryAlarms.add(new Model_Step_Checkbox("Output Frequency", "50 Hz", "60 Hz", false, false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            listBatteryAlarms.add(new Model_Step_Checkbox("Output Phase Shift", Float.parseFloat(tempConfigurationData[Config.Conf_Output_Phase_Shift]), " Degree", 0, 5, 1, 0));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        adapterWithStep = new DisplayAdapter_Step(getActivity(), listBatteryAlarms, new PositionCallback() {
            @Override
            public void clickPosition(int position) {
                modelWithStep = listBatteryAlarms.get(position);
            }

            @Override
            public void clickPosition(int position, int spinnerIndex) {

            }
        });
        layout_system_listview.setAdapter(adapterWithStep);

        txtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getLatest_SystemInverterSettings();
                saveSettings();
                //Toast.makeText(getActivity(), "System Inverter Settings", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void displaySecondaryLayout() {
        secondaryLayout.setVisibility(View.VISIBLE);
        thirdLayout.setVisibility(View.GONE);
        prepareSystemAlarmData();
    }

    public void increaseValue() {
        try {
            Log.v("Increment=", "STep=" + modelWithStep.stepSize + "   Value= " + modelWithStep.value + "   Max=  " + modelWithStep.maxValue);
            if (modelWithStep == null) {
                //Toast.makeText(getActivity(), "Please select row", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((modelWithStep.stepSize + modelWithStep.value) <= modelWithStep.maxValue) {
                BigDecimal roundfinalPrice = new BigDecimal(modelWithStep.value + modelWithStep.stepSize).setScale(modelWithStep.decimalPlaces, BigDecimal.ROUND_UP);
                modelWithStep.value = roundfinalPrice.floatValue();
                Log.v("roundfinalPrice Up= ", roundfinalPrice + "   value  " + modelWithStep.value);
                adapterWithStep.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void decreaseValue() {
        try {
            Log.v("Decrement=", "STep=" + modelWithStep.stepSize + "   Value= " + modelWithStep.value + "   Min=  " + modelWithStep.minValue);

            if (modelWithStep == null) {
                //Toast.makeText(getActivity(), "Please select row", Toast.LENGTH_SHORT).show();
                return;
            }
            if ((modelWithStep.value - modelWithStep.stepSize) >= modelWithStep.minValue) {
                BigDecimal roundfinalPrice = new BigDecimal(modelWithStep.value - modelWithStep.stepSize).setScale(modelWithStep.decimalPlaces, BigDecimal.ROUND_DOWN);
                modelWithStep.value = roundfinalPrice.floatValue();
                Log.v("roundfinalPrice Down = ", roundfinalPrice + "   value  " + modelWithStep.value);
                adapterWithStep.notifyDataSetChanged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatedData(String data) {
        //Toast.makeText(getActivity(),"UpdatedData",Toast.LENGTH_SHORT).show();
        configurationData = data.split(",");
        //tempConfigurationData = data.split(",");
        /*if(getActivity().getSharedPreferences(Config.Prefernces,0).contains(Config.Configuration_Reading) && getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.Configuration_Reading,Config.configurationData_Format)!=null && getActivity().getSharedPreferences(Config.Prefernces,0).getString(Config.Configuration_Reading,Config.configurationData_Format).length()>20) {
            configurationData = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.configurationData_Format).split(",");
        }*/
        //Log.v("Refresh Config Data",data);
        if (configurationData[Config.Conf_Battery_Type_Index].toUpperCase().equalsIgnoreCase("LA")) {
            isLeadBattery = true;
        } else {
            isLeadBattery = false;
        }
    }

    public void showConfirmationDialog() {
        AlertDialog.Builder ab = new AlertDialog.Builder(getActivity());

        ab.setMessage("Do you want to save changes?");
        ab.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                d.dismiss();

                Log.v("Module==", module + "");
                switch (module) {
                    case Config.SYSTEM_SETTING:
                        getLatestSystemSettings();
                        break;

                    case Config.INVERTER_SETTING:
                        getLatest_SystemInverterSettings();
                        break;

                    case Config.BATTERY_SETTING:
                        getLatest_SystemBatterySettings();
                        break;

                    case Config.STATIC_SWITCH:
                        getLatestStaticSwitchSettings();
                        break;

                    case Config.COMMUNICATION_SETTING:
                        getLatestCommunicationSwitchSettings();
                        break;

                    case Config.BATTERY_ALARM:
                        getLatestBatteryAlarmsSettings();
                        break;

                    case Config.RECTIFIER_ALARM:
                        getLatestRectifierSettings();
                        break;

                    case Config.INVERTER_ALARM:
                        getLatestInverterAlarmsSettings();
                        break;

                    case Config.UPS_ALARM:
                        getLatestUpsSettings();
                        break;

                    case Config.SUMMARY_ALARM:
                        getLatestSummaryData();
                        break;


                }
                saveSettings();

            }
        });

        ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                d.dismiss();
                displaySecondaryLayout();
            }
        });
        d = ab.create();
        d.getWindow().addFlags(Window.FEATURE_NO_TITLE);
        d.show();
    }
}
