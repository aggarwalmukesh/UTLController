package com.app.utlcontroller.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.utlcontroller.Adapter.DisplayAdapter;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.R;
import com.app.utlcontroller.services.BluetoothService;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Mints on 5/11/2017.
 */

public class TestFragment extends Fragment implements View.OnClickListener{

    View imgRelay;
    View firstLayout,secondLayout,thirdLayout;
    Button btnTestAllRelay,btnTestIndividual;
    private ArrayList<ModelData> listSystem=new ArrayList<>();
    private ListView listViewSystem,listViewRelays;
    private final int VALUE=1;

    String[] tests={"Test all Relays","Test Individual Relays","Transfer Test","Battery Test","Reset Alarms"};
    // String[] relays={"Test all Relays","Test Individual","Battery Test","Reset Alarms"};
    String[] transferTest={"Transfer Bypass to Inverter"};

    HashMap<String,String[]> map=new HashMap<>();

    Button btnAcFailure,btnRectifier,btnLowBattery,btnRectOverTemp,btnInverterF,btnInverterOverload,btnInvRange,
            btnStaticSwitch,btnAcRange,btnHighDc,btnRectifierOverload,btnInputAcBreakdown,btnBatteryCkt,btnFanFailure,
            btnInverterOverTemp,btnOutputAcBreakdown;
    ExpandableListView expandableListView;

    RadioGroup radioGroup;
    View transferTestLayout,batterytestLayout;
    TextView textByPass2Inv;
    TextView textInv2Bypass;
    View btnBatteryTest,txtStartBatteryTest,allRelaystestLayout,allResetAlarm;
    TextView txtBatteryTestResult;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.testlayout,null);
        firstLayout=v.findViewById(R.id.firstLayout);
        //secondLayout=v.findViewById(R.id.secondLayout);
        thirdLayout=v.findViewById(R.id.layout_third);
        btnBatteryTest=v.findViewById(R.id.btnBatteryTest);
        batterytestLayout=v.findViewById(R.id.batterytestLayout);
        transferTestLayout=v.findViewById(R.id.transferTestLayout);
        listViewSystem= (ListView) v.findViewById(R.id.listView);
        listViewRelays= (ListView) v.findViewById(R.id.listViewRelays);
        imgRelay=v.findViewById(R.id.imgRelay);
        allRelaystestLayout=v.findViewById(R.id.allRelaystestLayout);
        allResetAlarm=v.findViewById(R.id.allResetAlarm);
        radioGroup= (RadioGroup) v.findViewById(R.id.radioGroup);
        btnTestAllRelay= (Button) v.findViewById(R.id.btnTestAllRelay);
        textByPass2Inv= (TextView) v.findViewById(R.id.textByPass2Inv);
        textInv2Bypass= (TextView) v.findViewById(R.id.txtInv2ByPass);
        txtBatteryTestResult= (TextView) v.findViewById(R.id.txtBatteryTestResult);
        //btnTestIndividual= (Button) v.findViewById(R.id.btnTestIndividual);
        expandableListView= (ExpandableListView) v.findViewById(R.id.expandableListView);
        firstLayout.setVisibility(View.VISIBLE);
        btnAcFailure= (Button) v.findViewById(R.id.btnAcFailure);
        txtStartBatteryTest= v.findViewById(R.id.txtStartBatteryTest);
        btnRectifier= (Button) v.findViewById(R.id.btnRectifier);
        btnLowBattery= (Button) v.findViewById(R.id.btnLowBattery);
        btnRectOverTemp= (Button) v.findViewById(R.id.btnRectOverTemp);
        btnInverterF= (Button) v.findViewById(R.id.btnInverterF);
        btnInverterOverload= (Button) v.findViewById(R.id.btnInverterOverload);
        btnInvRange= (Button) v.findViewById(R.id.btnInvRange);
        btnStaticSwitch= (Button) v.findViewById(R.id.btnStaticSwitch);
        btnAcRange= (Button) v.findViewById(R.id.btnAcRange);
        btnHighDc= (Button) v.findViewById(R.id.btnHighDc);
        btnRectifierOverload= (Button) v.findViewById(R.id.btnRectifierOverload);
        btnInputAcBreakdown= (Button) v.findViewById(R.id.btnInputAcBreakdown);
        btnBatteryCkt= (Button) v.findViewById(R.id.btnBatteryCkt);
        btnFanFailure= (Button) v.findViewById(R.id.btnFanFailure);
        btnInverterOverTemp= (Button) v.findViewById(R.id.btnInverterOverTemp);
        btnOutputAcBreakdown= (Button) v.findViewById(R.id.btnOutputAcBreakdown);
        v.findViewById(R.id.relaysTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(getActivity(),"#0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
            }
        });

        v.findViewById(R.id.resetAlarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(getActivity(),"ResetTestAlarm");
            }
        });

        v.findViewById(R.id.allRelaystestLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        textByPass2Inv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(getActivity(),"STARTTRANSFERTEST");
            }
        });

        txtStartBatteryTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(getActivity(),"startBatteryTest");


            }
        });

        String[] individualAlarms={"AC Failure","Rectifier Failure","Low battery voltage","Rectifier Over temperature","Inverter Failure",
                "Inverter Overload","Inverter Volt out of Range","Static switch failure","AC volt out of range","High DC Voltage","Rectifier overload",
                "Input AC breaker open ","Battery Ckt Breaker Open","Fan Failure ","Inverter Overtemp","Output AC breaker open"};



        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId==R.id.btnTestIndiRelay){
                    batterytestLayout.setVisibility(View.GONE);
                    listViewRelays.setVisibility(View.VISIBLE);
                    transferTestLayout.setVisibility(View.GONE);
                    allRelaystestLayout.setVisibility(View.GONE);
                    allResetAlarm.setVisibility(View.GONE);
                }
                else if(checkedId==R.id.btnTestAllRelay){
                    batterytestLayout.setVisibility(View.GONE);
                    listViewRelays.setVisibility(View.GONE);
                    transferTestLayout.setVisibility(View.GONE);
                    allRelaystestLayout.setVisibility(View.VISIBLE);
                    allResetAlarm.setVisibility(View.GONE);
                }
                else if(checkedId==R.id.btnTransferTest){
                    batterytestLayout.setVisibility(View.GONE);
                    listViewRelays.setVisibility(View.GONE);
                    transferTestLayout.setVisibility(View.VISIBLE);
                    allRelaystestLayout.setVisibility(View.VISIBLE);
                    allResetAlarm.setVisibility(View.GONE);
                    /*if(((MainActivity)getActivity()).byPassOn){
                        textByPass2Inv.setBackgroundResource(R.drawable.drawable_round_green);
                        textInv2Bypass.setBackgroundResource(R.drawable.drawable_app_unselected);
                    }else{
                        textByPass2Inv.setBackgroundResource(R.drawable.drawable_app_unselected);
                        textInv2Bypass.setBackgroundResource(R.drawable.drawable_round_green);
                    }*/

                }else if(checkedId==R.id.btnResetAlarm){
                    batterytestLayout.setVisibility(View.GONE);
                    listViewRelays.setVisibility(View.GONE);
                    transferTestLayout.setVisibility(View.GONE);
                    allRelaystestLayout.setVisibility(View.GONE);
                    allResetAlarm.setVisibility(View.VISIBLE);

                }else if(checkedId==R.id.btnBatteryTest){
                    batterytestLayout.setVisibility(View.VISIBLE);
                    listViewRelays.setVisibility(View.GONE);
                    transferTestLayout.setVisibility(View.GONE);
                    allRelaystestLayout.setVisibility(View.GONE);
                    allResetAlarm.setVisibility(View.GONE);
                    String[] defaultData = getActivity().getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, ((MainActivity) getActivity()).config.defaultMontoringData).split(",");
                    txtBatteryTestResult.setText("Last Test Result : "+(defaultData[Config.Mon_BatteryResult].contains("0")?"Failed": defaultData[Config.Mon_BatteryResult].contains("1")?"Passed":""));
                }
            }
        });
        //map.put(tests[0],relays);
        map.put(tests[1],transferTest);
        map.put(tests[2],new String[]{});
        map.put(tests[3],new String[]{});

        MyExpandableAdapter adapter=new MyExpandableAdapter();
        expandableListView.setAdapter(adapter);

        listSystem.add(new ModelData("AC Failure",""));
        listSystem.add(new ModelData("Rectifier Failure",""));
        listSystem.add(new ModelData("Low battery voltage ",""));
        listSystem.add(new ModelData("Rectifier Over temperature.",""));
        listSystem.add(new ModelData("Inverter Failure",""));
        listSystem.add(new ModelData("Inverter Overload",""));
        listSystem.add(new ModelData("Inverter Volt out of Range",""));
        listSystem.add(new ModelData("Static switch failure",""));
        listSystem.add(new ModelData("AC volt out of range ",""));
        listSystem.add(new ModelData("High DC Voltage",""));
        listSystem.add(new ModelData("Rectifier overload",""));
        listSystem.add(new ModelData("Input AC breaker open ",""));
        listSystem.add(new ModelData("Rectifier DC breaker Open ",""));
        listSystem.add(new ModelData("Battery Ckt Breaker Open",""));
        listSystem.add(new ModelData("Fan Failure ",""));
        listSystem.add(new ModelData("Inverter Overtemp",""));
        listSystem.add(new ModelData("Output AC breaker open",""));

        MyAdapter adapterMain=new MyAdapter(tests);
        listViewSystem.setAdapter(adapterMain);

        MyAdapter adapterRelays=new MyAdapter(individualAlarms);
        listViewRelays.setAdapter(adapterRelays);

        btnAcFailure.setOnClickListener(this);
        btnRectifier.setOnClickListener(this);
        btnLowBattery.setOnClickListener(this);
        btnRectOverTemp.setOnClickListener(this);
        btnInverterF.setOnClickListener(this);
        btnInverterOverload.setOnClickListener(this);
        btnInvRange.setOnClickListener(this);
        btnStaticSwitch.setOnClickListener(this);
        btnAcRange.setOnClickListener(this);
        btnHighDc.setOnClickListener(this);
        btnRectifierOverload.setOnClickListener(this);
        btnInputAcBreakdown.setOnClickListener(this);
        btnBatteryCkt.setOnClickListener(this);
        btnFanFailure.setOnClickListener(this);
        btnInverterOverTemp.setOnClickListener(this);
        btnOutputAcBreakdown.setOnClickListener(this);

        imgRelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstLayout.setVisibility(View.GONE);
                secondLayout.setVisibility(View.VISIBLE);
            }
        });

        btnTestAllRelay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return v;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnAcFailure:
                testCommand("#0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnRectifier:
                testCommand("#0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnLowBattery:
                testCommand("#0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnRectOverTemp:
                testCommand("#0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnInverterF:
                testCommand("#0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnInverterOverload:
                testCommand("#0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnInvRange:
                testCommand("#0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnStaticSwitch:
                testCommand("#0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnAcRange:
                testCommand("#0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnHighDc:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnRectifierOverload:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnInputAcBreakdown:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnBatteryCkt:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnFanFailure:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnInverterOverTemp:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;

            case R.id.btnOutputAcBreakdown:
                testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                break;
        }
    }



    class MyAdapter extends BaseAdapter{

        String[] data;
        public MyAdapter(String[] data){
            this.data=data;
        }
        @Override
        public int getCount() {
            return data.length/2;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.layout_test,null);
            TextView txtView= (TextView) v.findViewById(R.id.textView);
            TextView txtView2= (TextView) v.findViewById(R.id.textView2);
            txtView.setText(data[position*2]);
            txtView2.setText(data[position*2+1]);

            txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0) {
                        testCommand("#0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","1");
                    }if(position==1) {
                        testCommand("#0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","3");
                    }if(position==2) {
                        testCommand("#0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","5");
                    }if(position==3) {
                        testCommand("#0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","7");
                    }if(position==4) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","9");
                    }if(position==5) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","11");
                    }if(position==6) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","13");
                    }if(position==7) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","15");
                    }
                }
            });
            txtView2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(position==0) {
                        testCommand("#0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","2");
                    }if(position==1) {
                        testCommand("#0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","4");
                    }if(position==2) {
                        testCommand("#0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","6");
                    }if(position==3) {
                        testCommand("#0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","8");
                    }if(position==4) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","10");
                    }if(position==5) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","12");
                    }if(position==6) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","14");
                    }if(position==7) {
                        testCommand("#0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0@");
                        Log.v("0","16");
                    }
                }
            });
            return v;
        }
    }

    private void testCommand(String command) {
        if (BluetoothService.getInstance() != null) {
            Log.v("Command=  ", command + "");
            byte[] date = command.getBytes();
            if(BluetoothService.getInstance()!=null && BluetoothService.getInstance().mConnectedThread!=null) {
                BluetoothService.getInstance().mConnectedThread.write(date);
            }
            else{
                Toast.makeText(getActivity(),"Please connect with device to apply settings",Toast.LENGTH_SHORT).show();
            }
        }
    }



    class MyExpandableAdapter extends BaseExpandableListAdapter{

        @Override
        public int getGroupCount() {
            return map.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            return map.get(tests[groupPosition]).length;
        }

        @Override
        public Object getGroup(int groupPosition) {
            return map.get(tests[groupPosition]);
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            return map.get(tests[groupPosition])[childPosition];
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.layout_test,null);
            TextView txtView= (TextView) v.findViewById(R.id.textView);
            txtView.setText(tests[groupPosition]);
            return v;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View v=getActivity().getLayoutInflater().inflate(R.layout.layout_child,null);
            TextView txtView= (TextView) v.findViewById(R.id.textView);
            txtView.setText(getChild(groupPosition,childPosition).toString());
            return v;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return false;
        }
    }

}
