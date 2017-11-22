package com.app.utlcontroller.Controller;

import android.app.Dialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;
import com.app.utlcontroller.Adapter.AdapterAlarms;
import com.app.utlcontroller.Adapter.DisplayAdapter_Settings;
import com.app.utlcontroller.Adapter.EventAdapter;
import com.app.utlcontroller.Adapter.MyAdapter;
import com.app.utlcontroller.Contract.HomeContractor;
import com.app.utlcontroller.Model.HelperClass;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.Presenter.HomePresenter;
import com.app.utlcontroller.R;
import com.app.utlcontroller.interfaces.ReportsCallback;
import com.app.utlcontroller.services.BluetoothService;
import com.crashlytics.android.Crashlytics;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnTouchListener, HomeContractor.HomeView {
    public Handler h = new Handler();
    Handler eventsHandler = new Handler();
    Handler dateEventsHandler = new Handler();
    public String lastBatteryContactor = "";
    public String lastInputContactor = "";
    public String lastByPasscontactor = "";
    public boolean showOutOfSynTimer;
    public Dialog outOfSyncDialog;
    public boolean chargerOn, inverterOn, outPutOn, byPassOn, mbsOn;
    public boolean lastStatechargerOn, lastStateinverterOn, lastStateoutPutOn, lastStatebyPassOn, lastStatembsOn;
    public boolean inverterFault, rectifierFault, gridFault, byPassFault;
    public boolean lastStateinverterFault, lastStaterectifierFault, lastStategridFault, lastStatebyPassFault;
    public View btnResetAlarms;
    public ArrayList<ModelEvents> listEvent = new ArrayList();
    public boolean isSettingsVisible = false;
    View decorView;
    public String powerMode = "IDLE";
    Calendar cal;
    HomePresenter homePresenter;
    public SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss a,dd/MM/yyyy");
    public MonitoringFragment monitoringFragment;
    public ConfigurationFragment configurationFragment;
    public TestFragment testFragment;
    public SupportFragment supportFragment;
    public ImageView imgAboutCompany, imgPacketStatus;
    public long lastPacketReceived = 0;
    public boolean packetReceived = false;
    public View imgAlarm, imgCalendar;
    public View layoutHome, layoutMonitoring, layoutConfiguration, layoutTest, layoutSupport;
    public int selectedFragment = 1, selectedFragment_left = 1;
    public static final int REQUEST_ENABLE_BT = 1;
    private static final int REQUEST_CONNECT_DEVICE_SECURE = 2;
    private static final int REQUEST_CONNECT_DEVICE_INSECURE = 3;
    public Set<BluetoothDevice> pairedDevices;
    public BluetoothAdapter mBluetoothAdapter;
    public List<HelperClass> list = new ArrayList<>();
    public HelperClass helperClass;
    CheckBox checkEnableLogs;
    private boolean isDialogShown;
    public MyAdapter myAdapter;
    public ListView listViewAlarms;
    public AdapterAlarms adapterAlarm;
    public View layout_alarms, layout_logs;
    String alarmsData = "**,0,0,0,0,0,0,0,0,0,0,0,0,0,0,&&";
    public String previousAlarmData = "";
    public String previousMonitoringData = Config.defaultMontoringData;
    public ImageView imgPowerGrid, imgPowerByPass, imgRectifier, imgInverter, imgBattery;
    public View output;
    public SmallSeperatorView seperatorOutput, seperatorMCBByPassToSwitch, seperatorMCBGrid, seperatorMCBByPassToIsolation, seperatorGridPower_Switch, seperatorInverter_STS;
    public SeperatorView seperatorIsolation_Bypass;
    public SmallVerticalSeperatorView seperatorSwitchBattery;
    public ImageView imgMCBGrid, imgMCBByPass, batterySwitch, imgTransformer;  // imgGridClose, imgBypassClose,
    public ArrayList<ModelData> listData = new ArrayList<>();
    public TView tView;
    public Config config;
    public String[] data;
    public LView lView;
    public View homeLayout;

    public ReportsCallback callback = new ReportsCallback() {
        @Override
        public void getResult(final String data) {
            homePresenter.updateCallbackData(data);
        }
    };

    public String[] listAlarms = alarmsData.split(",");
    public TextView txtPowerMode, txtSyncStatus, txtTemperature, timerLogs;
    public ImageView imgSwitch2input1output, imgswitch1input2output;
    private View btnEraseLogs, btnSettingLog, btnLoadSettingLog, btnConfigureLog, btnLoadConfigureLog, btnSendLogToUsb, btnEventLog;
    public Dialog d;

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            homePresenter.noBluetoothConnection();
        }
    };

    Runnable runnableSettings = new Runnable() {
        @Override
        public void run() {
            mBooleanIsPressed = false;
            homePresenter.showSettingDialog();
        }
    };

    Runnable runnableEvents = new Runnable() {
        @Override
        public void run() {
            homePresenter.saveEventsIntoFile(powerMode);
            if (getSharedPreferences(Config.Prefernces, 0).getBoolean(Config.ISLOGENABLED, true)) {
                if (eventsHandler != null && runnableEvents != null) {
                    eventsHandler.removeCallbacks(runnableEvents);
                }
                eventsHandler.postDelayed(this, getSharedPreferences(Config.Prefernces, 0).getInt(Config.LOG_INTERVAL, 50) * 60000);
            }
        }
    };

    Runnable runnableDateEvent = new Runnable() {
        @Override
        public void run() {
            if (dateEventsHandler != null && runnableDateEvent != null) {
                eventsHandler.removeCallbacks(runnableDateEvent);
            }

            cal = Calendar.getInstance();
            dateEventsHandler.postDelayed(this, 2000);
            Config.testCommandWithoutError(MainActivity.this, " D," + sdf.format(cal.getTime()) + ",,,,,END");
        }
    };

    public View settingLayout;
    public View scrollLayout;
    public boolean receiving = false;
    public ImageView imgInverterOn, imgInverterOff;
    public Handler handler = new Handler();
    public boolean mBooleanIsPressed;
    public ArrayList<Model_Step_Checkbox> listFactorySettings = new ArrayList<>();
    public boolean passwordAlreadyAsked;
    private View.OnClickListener AlarmClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (selectedFragment_left == 2) {
                selectedFragment_left = 1;
                layout_alarms.setVisibility(View.VISIBLE);
                layout_logs.setVisibility(View.GONE);
                imgAlarm.setBackgroundResource(R.drawable.left_pressed);
                imgCalendar.setBackgroundResource(R.drawable.left);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        decorView = getWindow().getDecorView();
        decorView.setVisibility(View.GONE);
        setContentView(R.layout.activity_main);
        homePresenter = new HomePresenter(this, this);

        initialiseViews();
        initializeHomeData();

        imgInverterOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(MainActivity.this, "INVERTER ON");
            }
        });

        imgPacketStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.showDeviceDialog();
            }
        });

        btnResetAlarms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Config.testCommand(MainActivity.this, "RESETALLALARM");
            }
        });
        timerLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLogIntervalDialog();
            }
        });
        homePresenter.checkDataStatus();

        checkEnableLogs.setChecked(getSharedPreferences(Config.Prefernces, 0).getBoolean(Config.ISLOGENABLED, true));

        if (eventsHandler != null && runnableEvents != null)
            eventsHandler.postDelayed(runnableEvents, getSharedPreferences(Config.Prefernces, 0).getInt(Config.LOG_INTERVAL, 50) * 60000);

        if (dateEventsHandler != null && runnableDateEvent != null)
            dateEventsHandler.postDelayed(runnableDateEvent, 2000);

        checkEnableLogs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences(Config.Prefernces, 0).edit().putBoolean(Config.ISLOGENABLED, isChecked).commit();
                if (isChecked) {
                    if (eventsHandler != null && runnableEvents != null)
                        eventsHandler.postDelayed(runnableEvents, getSharedPreferences(Config.Prefernces, 0).getInt(Config.LOG_INTERVAL, 50) * 60000);
                }
            }
        });

        homePresenter.createRootDirectoryIfNotExist();
        homePresenter.updateAlarmData(alarmsData);
        homePresenter.enableBluetooth();
        imgAboutCompany.setOnTouchListener(this);
        imgAlarm.setOnClickListener(AlarmClickListener);

        imgCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment_left == 1) {
                    layout_alarms.setVisibility(View.GONE);
                    layout_logs.setVisibility(View.VISIBLE);
                    imgCalendar.setBackgroundResource(R.drawable.left_pressed);
                    imgAlarm.setBackgroundResource(R.drawable.left);
                    selectedFragment_left = 2;
                }
            }
        });

        btnEraseLogs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.clearLogsDialog();
            }
        });

        btnSendLogToUsb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.sendLogsToUsbDialog();
            }
        });

        btnConfigureLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelData> listData = Config.readConfigFile(MainActivity.this, "");
                if (listData != null && listData.size() > 0) {
                    settingLayout.setVisibility(View.VISIBLE);
                    scrollLayout.setVisibility(View.GONE);
                    findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingLayout.setVisibility(View.GONE);
                        }
                    });
                    TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    txtHeader.setText("CONFIGURE LOGS");
                    listView.setAdapter(new DisplayAdapter_Settings(MainActivity.this, listData));
                } else {
                    Toast.makeText(MainActivity.this, "No Logs found", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLoadConfigureLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.configureLogDialog();
            }
        });

        btnEventLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelEvents> listData = Config.readEventFile(MainActivity.this, "");

                if (listData != null && listData.size() > 0) {
                    settingLayout.setVisibility(View.GONE);
                    scrollLayout.setVisibility(View.VISIBLE);
                    RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                    LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
                    recyclerView.setLayoutManager(manager);
                    recyclerView.setAdapter(new EventAdapter(MainActivity.this, listData));
                } else {
                    Toast.makeText(MainActivity.this, "No Settings found", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnLoadSettingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                homePresenter.settingsLogDialog();
            }
        });

        btnSettingLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<ModelData> listData = Config.readSettingFile(MainActivity.this, "");
                if (listData != null && listData.size() > 0) {
                    settingLayout.setVisibility(View.VISIBLE);
                    scrollLayout.setVisibility(View.GONE);
                    findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            settingLayout.setVisibility(View.GONE);
                        }
                    });
                    TextView txtHeader = (TextView) findViewById(R.id.txtHeader);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    txtHeader.setText("SETTINGS");
                    listView.setAdapter(new DisplayAdapter_Settings(MainActivity.this, listData));
                } else {
                    Toast.makeText(MainActivity.this, "No Settings found", Toast.LENGTH_LONG).show();
                }
            }
        });

        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment > 1) {
                    displayCircuitFlow();
                }
            }
        });
        layoutMonitoring.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment != 2) {
                    displayMonitoringWindow();
                }
            }
        });
        layoutConfiguration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment != 3) {
                    displayConfigurationWindow();
                }
            }
        });
        layoutTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment != 4) {
                    displayTestWindow();
                }
            }
        });

        layoutSupport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedFragment != 5) {
                    displaySupportWindow();
                }
            }
        });

        decorView.setOnSystemUiVisibilityChangeListener(new View.OnSystemUiVisibilityChangeListener() {
            @Override
            public void onSystemUiVisibilityChange(int visibility) {
                Log.v("Visibility Changed", "aa" + visibility);
            }
        });
    }


    private void initializeHomeData() {
        config = new Config();
        imgPowerByPass.setOnClickListener(this);
        imgPowerGrid.setOnClickListener(this);
        output.setOnClickListener(this);
        imgInverter.setOnClickListener(this);
        imgRectifier.setOnClickListener(this);
        imgTransformer.setOnClickListener(this);
        imgBattery.setOnClickListener(this);
        //updatedHomeData();
        homePresenter.updateAlarmData(alarmsData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgPower:
                homePresenter.prepareData("input");
                //config.showReadingDialog(MainActivity.this, "POWER", listData);
                break;
            case R.id.imgTransformer:
                homePresenter.prepareData("bypass");
                config.showReadingDialog(MainActivity.this, "BYPASS", listData,false);
                break;
            case R.id.imgRectifier:
                homePresenter.prepareData("rectifier");
                config.showReadingDialog(MainActivity.this, "RECTIFIER", listData,rectifierFault);
                break;
            case R.id.output:
                homePresenter.prepareData("output");
                config.showReadingDialog(MainActivity.this, "OUTPUT", listData,false);
                break;
            case R.id.imgInverter:
                homePresenter.prepareData("inverter");
                config.showReadingDialog(MainActivity.this, "INVERTER", listData,false);
                break;
            case R.id.imgBattery:
                homePresenter.prepareData("battery");
                config.showReadingDialog(MainActivity.this, "BATTERY", listData,false);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mBluetoothAdapter!=null) {
            pairedDevices = mBluetoothAdapter.getBondedDevices();
            list.clear();
            connectDevice();
        }
    }

    public void connectDevice() {
        pairedDevices = mBluetoothAdapter.getBondedDevices();
        list.clear();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                System.out.println("parmit" + device.getName() + "\n" + device.getAddress());
                helperClass = new HelperClass();
                helperClass.setMac(device.getAddress());
                helperClass.setName(device.getName());
                list.add(helperClass);
            }

            if (list.size() > 0) {
                String info = list.get(0).getMac();
                String address = info.substring(info.length() - 17);
                Log.v("Address= ", address);
                //BluetoothDevice device = null;
                try {
                    final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (BluetoothService.getInstance() != null) {
                                BluetoothService.getInstance().connectBluetooth(device);
                                BluetoothService.getInstance().addReportListener(callback);
                            }
                        }
                    }, 2000);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Toast.makeText(MainActivity.this, "No Bluetooth device found", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getApplicationContext(), requestCode, Toast.LENGTH_SHORT).show();
        switch (requestCode) {
            case REQUEST_CONNECT_DEVICE_SECURE:

                break;
            case REQUEST_CONNECT_DEVICE_INSECURE:

                break;
            case REQUEST_ENABLE_BT:
                System.out.println("requestCode = " + requestCode);
                break;
        }
    }

    public void showLogIntervalDialog() {
        d = new Dialog(MainActivity.this);
        View v = getLayoutInflater().inflate(R.layout.layout_duration, null);
        d.setContentView(v);
        d.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d.dismiss();
            }
        });
        d.getWindow().hasFeature(Window.FEATURE_NO_TITLE);
        d.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        final NumberPicker numberPicker = (NumberPicker) v.findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(60);
        numberPicker.setValue(30);
        d.show();
        d.findViewById(R.id.btnDone).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timerLogs.setText(numberPicker.getValue() + " Min");
                getSharedPreferences(Config.Prefernces, 0).edit().putInt(Config.LOG_INTERVAL, numberPicker.getValue()).commit();
                if (getSharedPreferences(Config.Prefernces, 0).getBoolean(Config.ISLOGENABLED, true)) {
                    if (eventsHandler != null && runnableEvents != null) {
                        eventsHandler.removeCallbacks(runnableEvents);
                    }
                    eventsHandler.postDelayed(runnableEvents, getSharedPreferences(Config.Prefernces, 0).getInt(Config.LOG_INTERVAL, 50) * 60000);
                }
                d.dismiss();
            }
        });
    }

    public String dialog = "";


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Execute your Runnable after 5000 milliseconds = 5 seconds.
            handler.postDelayed(runnableSettings, 2000);
            mBooleanIsPressed = true;
            return true;
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (mBooleanIsPressed) {
                mBooleanIsPressed = false;
                handler.removeCallbacks(runnableSettings);
            }
        }
        return false;
    }


    @Override
    public void displayCircuitFlow() {
        homeLayout.setVisibility(View.VISIBLE);
        scrollLayout.setVisibility(View.GONE);
        settingLayout.setVisibility(View.GONE);
        layoutHome.setBackgroundResource(R.drawable.left_pressed);
        layoutConfiguration.setBackgroundResource(R.drawable.left);
        layoutMonitoring.setBackgroundResource(R.drawable.left);
        layoutTest.setBackgroundResource(R.drawable.left);
        selectedFragment = 1;
        layoutSupport.setBackgroundResource(R.drawable.left);
        homePresenter.updateAlarmFaultData();
    }

    @Override
    public void displayMonitoringWindow() {
        homeLayout.setVisibility(View.GONE);
        scrollLayout.setVisibility(View.GONE);
        settingLayout.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, monitoringFragment).commit();
        layoutHome.setBackgroundResource(R.drawable.left);
        layoutConfiguration.setBackgroundResource(R.drawable.left);
        layoutMonitoring.setBackgroundResource(R.drawable.left_pressed);
        layoutTest.setBackgroundResource(R.drawable.left);
        selectedFragment = 2;
        layoutSupport.setBackgroundResource(R.drawable.left);
        if (monitoringFragment != null) {
            monitoringFragment.updatedInitialData(getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, previousMonitoringData));
        }
    }

    @Override
    public void displayConfigurationWindow() {
        homeLayout.setVisibility(View.GONE);
        settingLayout.setVisibility(View.GONE);
        scrollLayout.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, configurationFragment).commit();
        layoutHome.setBackgroundResource(R.drawable.left);
        layoutConfiguration.setBackgroundResource(R.drawable.left_pressed);
        layoutMonitoring.setBackgroundResource(R.drawable.left);
        layoutSupport.setBackgroundResource(R.drawable.left);
        layoutTest.setBackgroundResource(R.drawable.left);
        selectedFragment = 3;
        if (configurationFragment != null) {
            configurationFragment.updatedData(getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.configurationData_Format));
        }
    }

    @Override
    public void displayTestWindow() {
        homeLayout.setVisibility(View.GONE);
        scrollLayout.setVisibility(View.GONE);
        settingLayout.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, testFragment).commit();
        layoutHome.setBackgroundResource(R.drawable.left);
        layoutConfiguration.setBackgroundResource(R.drawable.left);
        layoutMonitoring.setBackgroundResource(R.drawable.left);
        layoutTest.setBackgroundResource(R.drawable.left_pressed);
        layoutSupport.setBackgroundResource(R.drawable.left);
        selectedFragment = 4;
    }

    @Override
    public void displaySupportWindow() {
        homeLayout.setVisibility(View.GONE);
        scrollLayout.setVisibility(View.GONE);
        settingLayout.setVisibility(View.GONE);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, supportFragment).commit();
        layoutHome.setBackgroundResource(R.drawable.left);
        layoutConfiguration.setBackgroundResource(R.drawable.left);
        layoutMonitoring.setBackgroundResource(R.drawable.left);
        layoutTest.setBackgroundResource(R.drawable.left);
        layoutSupport.setBackgroundResource(R.drawable.left_pressed);
        selectedFragment = 5;
    }

    @Override
    public void initialiseViews() {
        listViewAlarms = (ListView) findViewById(R.id.listViewA);
        timerLogs = (TextView) findViewById(R.id.timerLogs);
        txtPowerMode = (TextView) findViewById(R.id.txtPowerMode);
        imgSwitch2input1output = (ImageView) findViewById(R.id.imgSwitch2input1output);
        imgswitch1input2output = (ImageView) findViewById(R.id.imgswitch1input2output);
        btnEraseLogs = findViewById(R.id.btnEraseLog);
        btnSettingLog = findViewById(R.id.btnSettingLog);
        settingLayout = findViewById(R.id.settingsLayout);
        btnResetAlarms = findViewById(R.id.btnResetAlarms);
        checkEnableLogs = (CheckBox) findViewById(R.id.checkEnableLogs);
        scrollLayout = findViewById(R.id.scrollLayout);
        btnLoadSettingLog = findViewById(R.id.btnLoadSettingLog);
        btnConfigureLog = findViewById(R.id.btnConfigureLog);
        btnEventLog = findViewById(R.id.btnEventLog);
        btnLoadConfigureLog = findViewById(R.id.btnLoadConfigureLog);
        btnSendLogToUsb = findViewById(R.id.btnSendLogToUsb);
        Intent bluettothService = new Intent(MainActivity.this, BluetoothService.class);
        bluettothService.addCategory("bluettothService");
        startService(bluettothService);
        txtSyncStatus = (TextView) findViewById(R.id.txtSyncStatus);
        txtTemperature = (TextView) findViewById(R.id.txtTemperature);
        homeLayout = findViewById(R.id.homeLayout);
        layout_alarms = findViewById(R.id.layout_alarms);
        layout_logs = findViewById(R.id.layout_logs);
        imgAboutCompany = (ImageView) findViewById(R.id.imgAboutCompany);
        imgPacketStatus = (ImageView) findViewById(R.id.imgPacketStatus);
        layoutHome = findViewById(R.id.layoutHome);
        layoutConfiguration = findViewById(R.id.layoutConfigurations);
        layoutMonitoring = findViewById(R.id.layoutMonitoring);
        layoutTest = findViewById(R.id.layoutTest);
        layoutSupport = findViewById(R.id.layoutSupport);
        imgAlarm = findViewById(R.id.imgAlarm);
        imgInverterOn = (ImageView) findViewById(R.id.imgInverterOn);
        imgCalendar = findViewById(R.id.imgCalendar);
        monitoringFragment = new MonitoringFragment();
        configurationFragment = new ConfigurationFragment();
        imgPowerByPass = (ImageView) findViewById(R.id.imgPowerByPass);
        imgPowerGrid = (ImageView) findViewById(R.id.imgPower);
        imgRectifier = (ImageView) findViewById(R.id.imgRectifier);
        imgTransformer = (ImageView) findViewById(R.id.imgTransformer);
        imgInverter = (ImageView) findViewById(R.id.imgInverter);
        imgBattery = (ImageView) findViewById(R.id.imgBattery);
        output = findViewById(R.id.output);
        tView = (TView) findViewById(R.id.tView);
        lView = (LView) findViewById(R.id.lView);
        seperatorOutput = (SmallSeperatorView) findViewById(R.id.seperatorOutput);
        seperatorMCBByPassToSwitch = (SmallSeperatorView) findViewById(R.id.seperatorMCBByPassToSwitch);
        seperatorInverter_STS = (SmallSeperatorView) findViewById(R.id.seperatorInverter_STS);
        seperatorMCBByPassToIsolation = (SmallSeperatorView) findViewById(R.id.seperatorMCBByPassToIsolation);
        seperatorMCBGrid = (SmallSeperatorView) findViewById(R.id.seperatorMCBGrid);
        seperatorGridPower_Switch = (SmallSeperatorView) findViewById(R.id.seperatorGridPower_Switch);
        seperatorSwitchBattery = (SmallVerticalSeperatorView) findViewById(R.id.seperatorSwitchBattery);
        seperatorIsolation_Bypass = (SeperatorView) findViewById(R.id.seperatorIsolation_Bypass);
        imgMCBGrid = (ImageView) findViewById(R.id.imgMCBGrid);
        imgMCBByPass = (ImageView) findViewById(R.id.imgMCBByPass);
        batterySwitch = (ImageView) findViewById(R.id.batterySwitch);
        testFragment = new TestFragment();
        supportFragment = new SupportFragment();
    }
}
