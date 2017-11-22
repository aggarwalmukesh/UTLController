package com.app.utlcontroller.Controller;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.utlcontroller.Adapter.DisplayAdapter;
import com.app.utlcontroller.Adapter.DisplayAdapter_BigFont;
import com.app.utlcontroller.Adapter.DisplayAdapter_Settings;
import com.app.utlcontroller.Adapter.GridAdapter;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.R;
import com.app.utlcontroller.interfaces.SuccessInterface;
import com.app.utlcontroller.services.BluetoothService;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.formula.functions.Mode;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import static com.app.utlcontroller.R.id.listView;

/**
 * Created by Mints on 5/5/2017.
 */

public class Config {

    public static final String LOG_INTERVAL = "pref_log_interval";
    public static final String ISLOGENABLED = "pref_log_enable";
    public static final String RECEIVING_DATA = "receivingData";
    public static final long COMM_UPDATE_TIME = 10000;
    public static final CharSequence FACTORY_SETTINGS_SDATA = "FSD";
    public static final CharSequence FACTORY_SETTINGS_EDATA = "ESD";
    public static final String FACTORY_READING = "factoryReading";

    public static String SETTING_NAME = "SHUNT SIZE," +
            "DC AMP RATING," +
            "AC VOLTAGE RATING," +
            "FREQUENCY," +
            "CHARGER PHASE," +
            " ," +
            "ALARM THRESHOLD SETTINGS," +
            "SETTING NAME," +
            "OVER TEMPERATURE," +
            "OVER TEMPERATURE RESET," +
            "OVER TEMPERATURE DERATE," +
            "OVER TEMPERATURE DERATE RESET," +
            "OVER TEMPERATURE SHUTDOWN," +
            "OVER TEMPERATURE SHUTDOWN RESET," +
            "NICAD BATT TEST VOLTAGE," +
            "NICAD BATT TEST FAIL VOLTAGE," +
            "NICAD BATT TEST PASS VOLTAGE," +
            "LEAD ACID BATT TEST VOLTAGE," +
            "LEAD ACID BATT TEST FAIL VOLTAGE," +
            "LEAD ACID BATT TEST PASS VOLTAGE," +
            " ," +
            "SOFTWARE POT CALIBRATION," +
            "SETTING NAME," +
            "DCV," +
            "LS DCV," +
            "DCA," +
            "ACV," +
            "OT PROBE," +
            "TC PROBE," +
            " ," +
            "FAN SETTINGS," +
            "SETTING NAME," +
            "BOTTOM FAN ON C," +
            "BOTTOM FAN OFF C," +
            "TOP FAN ON C," +
            "TOP FAN OFF C," +
            "FANS ALWAYS ON," +
            " ," +
            "EXT. COMMUNICATION SETTING," +
            "PROTOCOLS";


    public static String SETTING_UNITS = "AMPS , " +
            "AMPS , " +
            "VOLTS , " +
            "HZ , " +
            "NONE , " +
            " ," +
            " ," +
            "UNITS , " +
            "C , " +
            "C , " +
            "C , " +
            "C , " +
            "C , " +
            "C , " +
            "Volt/Cell , " +
            "Volt/Cell , " +
            "Volt/Cell , " +
            "Volt/Cell , " +
            "Volt/Cell , " +
            "Volt/Cell , " +
            " , " +
            " , " +
            "UNITS , " +
            "NONE , " +
            "NONE , " +
            "NONE , " +
            "NONE , " +
            "NONE , " +
            "NONE , " +
            " , " +
            " , " +
            "UNITS , " +
            "C , " +
            "C , " +
            "C , " +
            "C , " +
            "NONE , " +
            " , " +
            " , " +
            "NONE ";
    public static String SETTING_DEFAULT_VALUES = "10 , " +
            "6 , " +
            "480 , " +
            "60 , " +
            "3 , " +
            " , " +
            " , " +
            "DEFAULT VALUE , " +
            "100 , " +
            "90 , " +
            "110 , " +
            "85 , " +
            "115 , " +
            "85 , " +
            "1.2 , " +
            "1.25 , " +
            "1.3 , " +
            "1.85 , " +
            "1.9 , " +
            "2 , " +
            " , " +
            " , " +
            "DEFAULT VALUE , " +
            "VARIES , " +
            "VARIES , " +
            "VARIES , " +
            "VARIES , " +
            "VARIES , " +
            "VARIES , " +
            " , " +
            " , " +
            "DEFAULT VALUE , " +
            "50 , " +
            "40 , " +
            "85 , " +
            "55 , " +
            "NO , " +
            " , " +
            " , " +
            "MODBUS-SERIAL ";
    public static String SETTING_VALID_SETTINGS = "10 15 20 25 30 50 70 75 100 150 200 250 300 400 500 600 800 900 1000 , " +
            "6 12 16 20 25 30 35 40 50 60 75 100 125 150 200 250 300 400 500 , " +
            "110 120 208 220 230 240 277 380 400 415 440 450 480 500 575 600 , " +
            "50 60 , " +
            "1 3 , " +
            " , " +
            " , " +
            "VALID SETTINGS , " +
            "90 TO 120 , " +
            "60 TO 100 , " +
            "85 TO 120 , " +
            "60 TO 110 , " +
            "85 TO 120 , " +
            "60 TO 115 , " +
            "0.55 TO 1.80 , " +
            "0.55 TO 1.80 , " +
            "0.55 TO 1.80 , " +
            "1.20 TO 2.50 , " +
            "1.2 TO 2.50 , " +
            "1.20 TO 2.50 , " +
            " , " +
            " , " +
            "VALID SETTINGS , " +
            "0 TO 5.00000 , " +
            "0 TO 5.00000 , " +
            "0 TO 5.0000 , " +
            "0 TO 9.9999 , " +
            "0 TO 5.0000 , " +
            "0 TO 5.0000 , " +
            " ," +
            " ," +
            "VALID SETTINGS , " +
            "30 TO 70 ," +
            "10 TO 40 ," +
            "65 TO 105 ," +
            "35 TO 75 ," +
            "YES NO ," +
            " ," +
            " ," +
            "MODBUS-SERIAL- MODBUS-TCP- I2C- ETHERNET- GSM- BT- CAN  ";
    public static String SETTING_VALUES = "10 , " +
            "6 , " +
            "480 , " +
            "60 , " +
            "3 , " +
            " , " +
            " , " +
            "VALUE , " +
            "100 , " +
            "90 , " +
            "110 , " +
            "85 , " +
            "115 , " +
            "85 , " +
            "1.2 , " +
            "1.25 , " +
            "1.3 , " +
            "1.85 , " +
            "1.9 , " +
            "2 , " +
            " , " +
            " , " +
            "VALUE , " +
            "0.0315 , " +
            "0.0315 , " +
            "0.0032 , " +
            "0.1928 , " +
            "1 , " +
            "1 , " +
            " , " +
            " , " +
            "VALUE , " +
            "50 , " +
            "40 , " +
            "85 , " +
            "55 , " +
            "NO , " +
            " , " +
            " , " +
            "MODBUS-SERIAL ";


    public static String configNames = "Name," +
            "Location," +
            "Date and Time," +
            "Display Time Out," +
            "Password," +
            "INVERTER SETTINGS," +
            "Output Voltage," +
            "output Frequency," +
            "Output Phase Shift," +
            "BATTERY SETTINGS," +
            "Type," +
            "Battery Cell/String," +
            "Battery Capacity," +
            "No. of String," +
            "Float Voltage," +
            "Equalize voltage," +
            "Equalize Timer," +
            "Equalize Mode," +
            "Remote Equalize," +
            "Temperature Comp. Rate," +
            "Low Voltage Sutdown," +
            "STATIC SWITCH SETTINGS," +
            "Load Voltage High Limit," +
            "Load voltage Low limit," +
            "Inveter Voltage range," +
            "Bypass Voltage Range," +
            "Inverter Frequency Range," +
            "Bypass Frequency range," +
            "Out of sync Check," +
            "Transfer Delay," +
            "COMMUNICATION SETTINGS (MODBUS )," +
            "Address," +
            "Baudrate (DROPDOWN LIST)," +
            "Parity  (DROPDOWN LIST)," +
            "Stopbits  (DROPDOWN LIST)," +
            "BATTERY ALARM SETTING," +
            "High Voltage," +
            "Low Voltage," +
            "Battery Over temperature," +
            "End Of discharge," +
            "RECTIFIER ALARM SETTINGS," +
            "AC Fail Upper," +
            "AC Fail Lower," +
            "High DC Shutdown," +
            "Low DC Current," +
            "Rectifier Over Temperature," +
            "INVERTER ALARM SETTINGS," +
            "Low AC Output," +
            "High AC Output," +
            "Inverter Over Temperature," +
            "Current Limit," +
            "UPS ALARM SETTINGS," +
            "AC Input Breaker trip," +
            "Chg. Output Breaker Trip," +
            "Battery Breaker Trip," +
            "AC output Breaket trip," +
            "Bypass Breaker Trip," +
            "LCD Fail," +
            "INC. IN SUMMARY SETTINGS," +
            "AC Fail," +
            "Low DC Current," +
            "OverLoad," +
            "Ground Detection," +
            "Battery Over temperature";


    public static String configUnits = "Char," +
            "char," +
            "DD/MM/YYYY-HH:MM:AM/PM," +
            "Minutes," +
            "char," +
            "," +
            "Volts ac," +
            "Hz," +
            "Degree(+)," +
            "," +
            "," +
            "," +
            "AH," +
            "," +
            "Volt/Cell," +
            "Volt/Cell," +
            "Hour," +
            "," +
            "," +
            "mV/*C/Cell," +
            "Volt/Cell," +
            "," +
            "% (  +  )," +
            "% (   -  )," +
            "% (  +  )," +
            "% (  +  )," +
            "% (  +  )," +
            "% (  +  )," +
            "Degree (  +  )," +
            "Seconds ," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "," +
            "V/Cell," +
            "V/Cell," +
            "Degree centigrade," +
            "Volt/Cell," +
            "," +
            "%," +
            "%," +
            "V/Cell," +
            "Amps," +
            "Degree centigrade," +
            "," +
            "%," +
            "%," +
            "Degree centigrade," +
            "%," +
            " ," +
            " ," +
            " ," +
            " ," +
            " , " +
            " ," +
            " ," +
            " ," +
            " ," +
            " ," +
            " ," +
            " ," +
            " ";

    public static String configDefaultValues = "LA-MARCHE UPS," +
            "106 Bradrock Dr- Des Plaines- IL 60018- USA," +
            "--," +
            "5," +
            "1188," +
            "," +
            "120," +
            "60," +
            "2," +
            "," +
            "LA," +
            "121L / 183N," +
            "110," +
            "1," +
            "2.25L / 1.40N," +
            "2.33L / 1.55N," +
            "8," +
            "P1," +
            "ENABLE," +
            "3," +
            "1.75L / 1.00N," +
            "," +
            "10," +
            "-10," +
            "5," +
            "5," +
            "5," +
            "5," +
            "5," +
            "20," +
            "," +
            "1," +
            "#9600," +
            "#NONE," +
            "#1," +
            "," +
            "2.45L / 1.60N," +
            "1.98L-1.20N," +
            "60," +
            "1.75L / 1.00N," +
            "," +
            "5," +
            "5," +
            "2.50L / 1.65N," +
            "1," +
            "60," +
            "," +
            "-10," +
            "10," +
            "60," +
            "110," +
            "," +
            "Disable," +
            "Disable," +
            "Disable," +
            "Disable," +
            "Disable," +
            "Disable," +
            "," +
            "Yes," +
            "Yes," +
            "Yes," +
            "Yes," +
            "Yes";


    public static String configValidValues = "up to 100 charecter string," +
            "up to 100 charecter string," +
            "--," +
            "1-60," +
            "Four Digit Numeric String," +
            "," +
            "110-130," +
            "50-60," +
            "0 to +5," +
            "," +
            "NICAD-LEAD ACID," +
            "120-122L /180-186N," +
            "10-250," +
            "1-10," +
            "2.12-2.30L/ 1.39-1.45N," +
            "2.25-2.40L/ 1.5-1.60N," +
            "1-24," +
            "P1/ P2/ P3/ P4," +
            "ENABLE/ DISABLE," +
            "1-5," +
            "1.60-1.85L / 0.95-1.10N," +
            "," +
            "5-20," +
            "-20-(-5)," +
            "0- 5," +
            "1-5," +
            "1-5," +
            "1-5," +
            "0-10," +
            "0-60," +
            "," +
            "1-255," +
            "#9600/ #14400/ #115200," +
            "#EVEN/ #ODD/ #NONE," +
            "#1/ #1.5/ #2," +
            "," +
            "2.20-2.70L  1.44-1.76N," +
            "1.78-2.18L/ 1.08-1.78N," +
            "50-90," +
            "1.60-1.85L / 0.95-1.10N," +
            "," +
            "5-10," +
            "5-15," +
            "2.40-2.75L  1.45-1.80N," +
            "0.1-2.0," +
            "50-90," +
            "," +
            "-5 to -15," +
            "5-10," +
            "50-90," +
            "70-115," +
            "," +
            "Enable / Disable," +
            "Enable / Disable," +
            "Enable / Disable," +
            "Enable / Disable," +
            "Enable / Disable," +
            "Enable / Disable," +
            "," +
            "YES/NO," +
            "YES/NO," +
            "YES/NO," +
            "YES/NO," +
            "YES/NO";

    private static String EventLogFileName = "Event_Log_File.xls";
    private static String ConfigLogFileName = "CONFIG.xls";
    private static String SettingLogFileName = "SETTINGS.xls";
    private static Cell c;
    public static String dialogDisplayed = "";
    private static Row row;


    public static SimpleDateFormat createdAtFormat = new SimpleDateFormat("dd/MM/yyyy 'at' hh:mm:ss");
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm:ss");
    public static String defaultMontoringData = "##,12345,LM-UPS-02.01,Kirti nagar,310,1,1,1,127,12,56.8,125,124,56.7,120,112,56.3,NA,1523,1,1,1,1,125.3,112,253,15,56,110,58,12/12/2017 12:20 AM,1,1,1,1,1,1,320,112,32.5,1522,2553,0.95,1,1,1,1,321,59.9,0,2,0,1,1,1,210,123,235,3214,1324,0.99,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,@@";
    public static String configurationData_Format = "%,LA-MARCHE UPS,106 Bradrock Dr - Des Plaines- IL 60018- USA,12/11/2017 12:23 am,modbus,1,1,1,LA,121,110,1,2.25,2.33,8,P1,1,3,1.75,1,1,1,1,120,2,10,-10,5,5,5,5,1,1,1,1,5,20,2.45,60,1,1,1.98,1.75,1,2.5,1,60,-10,10,60,110,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,*";
    public static String configurationData_NI = "%,LA-MARCHE UPS,106 Bradrock Dr - Des Plaines- IL 60018- USA,12/11/2017 0:23,modbus,1,1,1,NICD,183,110,1,1.4,1.55,8,P1,1,3,1,1,1,1,1,120,2,10,-10,5,5,5,5,1,1,1,1,5,20,1.6,60,1,90,1.2,1,145,1.65,1,60,-10,10,60,110,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,*";
    public static String configurationData_LEAD = "%,LA-MARCHE UPS,106 Bradrock Dr - Des Plaines- IL 60018- USA,12/11/2017 0:23,modbus,1,1,1,LA,121,110,1,2.25,2.33,8,P1,1,3,1.75,1,1,1,1,120,2,10,-10,5,5,5,5,1,1,1,1,5,20,2.45,60,1,90,1.98,1.75,145,2.5,1,60,-10,10,60,110,0,0,0,0,0,1,1,1,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,*";
    public static int Conf_SystemNameIndex = 1;
    public static int Conf_Location_Index = 2, Conf_Time_Date_Index = 3, Conf_Communications_Index = 4;
    public static int Conf_Battery_Type_Index = 8, Conf_Battery_Cells_Index = 9, Conf_Battery_capacity_Index = 10;
    public static int Conf_NumStrings_Index = 11, Conf_FloatVoltage_Index = 12, Conf_Equalize_Voltage_Index = 13;
    public static int Conf_Equalize_Timer_Hours_Index = 14, Conf_Equalize_Timer_Mode_Index = 15, Conf_Enable_Remote_Eq_Index = 16;
    public static int Conf_Temperature_Compensation_Index = 17, Conf_Low_Voltage_Shut_Index = 18;
    public static int Conf_intOutput_Voltage_Index = 23, Conf_Output_Phase_Shift = 24, Conf_Load_Voltage_High_Limit_Index = 25;
    public static int Conf_Load_Voltage_Low_LIMIT_Index = 26, Conf_Inverter_Voltage_Range_Index = 27, Conf_Bypass_Voltage_RangeIndex = 28;
    public static int Conf_Inverter_Frequency_Range_Index = 29, Conf_Bypass_Frequency_Range_Index = 30, Conf_Out_of_Sync_Check_Index = 35;
    public static int Conf_Retransfer_Delay_Index = 36, Conf_High_Voltage_Index = 37, Conf_Battery_over_temp_Index = 38;
    public static int Conf_Low_Voltage_Index = 41, Conf_End_Discharge_Index = 42, Conf_AC_Fail_UPPER_Index = 43, Conf_High_DC_Volt_Shutdown = 44;
    public static int Conf_Low_DC_Current_Index = 45, Conf_Rectifier_over_temp_Index = 46, Conf_Low_AC_Output_Index = 47;
    public static int Conf_High_AC_Output_Index = 48, Conf_Inverter_Overtemp_Index = 49, Conf_Current_Limit_Index = 50;
    public static int Conf_AC_Input_Breaker_Trip_Index = 51, Conf_Charger_Output_Breaker_Trip_Index = 52, Conf_Battery_breaker_trip_Index = 53;
    public static int Conf_AC_Output_Breaker_Trip_Index = 54, Conf_Bypass_Breaker_Trip = 55;
    public static int Conf_LCD_Fail_Index = 59, Conf_AC_Fail_2_Index = 60, Conf_Low_DC_2_Current_Index = 61, Conf_Overload_Index = 62;
    public static int Conf_Ground_Detection_Index = 63, Conf_Battery_Over_Temperature_Index = 64;


    public static int Mon_ModelNumber = 1, Mon_System_Name = 2, Mon_SystemLocation = 3, Mon_DateTime = 4, Mon_PowerMode = 5, Mon_SYS_TEMP = 6;
    public static int Mon_InputVPhase1 = 8, Mon_InputCPhase1 = 9, Mon_InputFPhase1 = 10;
    public static int Mon_InputVPhase2 = 11, Mon_InputCPhase2 = 12, Mon_InputFPhase2 = 13;
    public static int Mon_InputVPhase3 = 14, Mon_InputCPhase3 = 15, Mon_InputFPhase3 = 16;
    public static int Mon_InputVA = 17, Mon_InputPower = 18, Mon_InputContactor = 19;
    public static int Mon_BatterVol = 23, Mon_BatChrgDiscCurrent = 24, Mon_BatteryDisElapsedTime = 25, Mon_RemovedAH = 26, Mon_BatterySOC = 27;
    public static int Mon_BatterCellsInfo = 28, Mon_BatTemp = 29, Mon_BatteryResultDate = 30, Mon_BatteryResult = 31, Mon_BatteryContactor = 32;
    public static int Mon_InvVoltage = 37, Mon_InvCurrent = 38, Mon_InvFrequency = 39, Mon_InvVA = 40, Mon_InvPower = 41, Mon_InvPF = 42, Mon_InvSYNCSTATUS = 43;
    public static int Mon_ByPassVoltage = 47, Mon_ByPassFrequency = 48, Mon_ByPassContactor = 49, Mon_ByPassSource = 50, Mon_ByPassMBBS1 = 51, Mon_ByPassMBBS2 = 52;
    public static int Mon_OutputVoltage = 55, Mon_OutputCurrent = 56, Mon_OutputFrequency = 57, Mon_OutputVA = 58, Mon_OutputPower = 59, Mon_OutputPF = 60;

    public static final int Mon_RectifierVoltage = 64, Mon_RectifierCurrent = 65;


    public static final String STATUS_SDATA = "$";
    public static final String STATUS_EDATA = "&";

    /*  Settings Data
    *
    * This is the same string as tab transmits the setting from Configuration window, This  is the just copy of data
    * saved in to UPS Board, This is required in case when tab get defected now we have to replace the tab with new tab
    * then the tab will restore the previous setting from UPS Board. */

    public static final String SETTINGS_SDATA = "%";
    public static final String SETTINGS_EDATA = "*";

    /*

    Please save this  String into a ​.xls ​file in new line, every time when new string received.
    And The file should contain these information in the header-

     */
    public static final String EVENT_LOG_SDATA = "EL";
    public static final String EVENT_LOG_EDATA = "EndEL";

    /*

     UTL Peripheral Board to Tab Data String. a. Current/Live Data Strings (Monitoring Window data)-

     */
    public static final String MONITORING_SDATA = "#";
    public static final String MONITORING_EDATA = "@";

    /*

    1. Tab to UTL Peripheral Board Data String. a. Configuration Window / User Settings data string-
     */
    public static final String CONFIGURATION_SETTINGS_SDATA = "%";
    public static final String CONFIGURATION_SETTINGS_EDATA = "*";
    public static String Prefernces = "pref";
    public static String MonitoringReading = "monitor";
    public static String MyFilter = "logsFilter";
    public static float CircleRadius = 7;
    public static String Configuration_Reading = "configuration";
    public static String Configuration_Reading_NI = "configurationNI";
    public static String Configuration_Reading_LEAD = "configurationLEAD";
    public static final int SYSTEM_SETTING = 1;
    public static final int INVERTER_SETTING = 2;
    public static final int BATTERY_SETTING = 3;
    public static final int STATIC_SWITCH = 4;
    public static final int COMMUNICATION_SETTING = 5;
    public static final int BATTERY_ALARM = 6;
    public static final int RECTIFIER_ALARM = 7;
    public static final int INVERTER_ALARM = 8;
    public static final int UPS_ALARM = 9;
    public static final int SUMMARY_ALARM = 10;
    //public static String factoryData_Format="FSD,1,120,3,3,0.025,0.015,0.012,1,2";
    public static String factoryData_Format="FSD,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1";


    String[] eventHeaderNames = {"Date", "Time", "Input Voltage PHASE 1", "Input Current PHASE 1",
            "Input Frequency PHASE 1", "Input Voltage PHASE 2", "Input Current PHASE 2", "Input Frequency PHASE 2",
            "Input Voltage PHASE 3", "Input Current PHASE 3", "Input Frequency PHASE 3", "Rectifier Temperature",
            "Battery Voltage", "Battery Charging", "Discharging Current", "Battery SOC", "Battery Temperature", "Inverter Voltage",
            "Inverter Current", "Inverter Frequency", "Inverter temperature", "SYNC Status", "Bypass Voltage", "Bypass Frequency",
            "Bypass Source", "Output Voltage", "Output Current", "Output Frequency", "Event Name"};
    Dialog d;
    String message = "Since 1945, La Marche has been providing reliable power conversion products. La Marche products include industrial battery chargers, rectifiers, power supplies, inverters and many more.  We have built our reputation by controlling all aspects of the development process including design, in-house manufacturing and testing.  La Marche is an ISO 9001:2008-certified manufacturer which includes 100% functional testing for every La Marche product.  The Company is headquartered in Des Plaines, IL.";
    String contactUs = "La Marche MFG  ,106 Bradrock Dr  \nDes Plaines, IL 60018  \nPhone: 847-299-1188  \nFax: 847-299-3061  \nEmail: sales@lamarchemfg.com\n© Copyright 1945 - 2017 La Marche Manufacturing Company.";

    public void showReadingDialog(final Context context, String header, ArrayList<ModelData> data, boolean rectifierFault) {
        dialogDisplayed = header;
        if (d == null || !d.isShowing()) {
            d = new Dialog(context);
            d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(context).inflate(R.layout.dialog_readings, null);
            d.setContentView(v);
            v.findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            if(header.contains("RECTIFIER") && rectifierFault){
                v.findViewById(R.id.btnResetRectifier).setVisibility(View.VISIBLE);
            }else{
                v.findViewById(R.id.btnResetRectifier).setVisibility(View.GONE);
            }

            v.findViewById(R.id.btnResetRectifier).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    testCommand(context,"RESET RECTIFIER");
                }
            });

            TextView txtHeader = (TextView) v.findViewById(R.id.txtHeader);
            ListView listView = (ListView) v.findViewById(R.id.listView);
            txtHeader.setText(header);
            listView.setAdapter(new DisplayAdapter_BigFont(context, data));
            d.show();
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogDisplayed = "";
                }
            });
        }
    }

    public void showReadingDialog_RecyclerView(Context context, String header, ArrayList<ModelEvents> data) {
        dialogDisplayed = header;
        if (d == null || !d.isShowing()) {
            d = new Dialog(context);
            d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(context).inflate(R.layout.dialog_readings, null);
            d.setContentView(v);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = 800;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;

            d.getWindow().setAttributes(lp);
            v.findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            TextView txtHeader = (TextView) v.findViewById(R.id.txtHeader);
            v.findViewById(R.id.listView).setVisibility(View.GONE);
            RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
            recyclerView.setVisibility(View.VISIBLE);
            txtHeader.setText(header);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(manager);
            recyclerView.setAdapter(new GridAdapter(context, data));
            d.show();
            d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialog) {
                    dialogDisplayed = "";
                }
            });
        }
    }

    public void updateReadingDialog_RecyclerView(Context context, String header, ArrayList<ModelEvents> data) {
        Log.v("Updating Recycler", "Dialog");
        dialogDisplayed = header;
        if (d == null || !d.isShowing()) {
            d = new Dialog(context);
            d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(context).inflate(R.layout.dialog_readings, null);
            d.setContentView(v);
            v.findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(d.getWindow().getAttributes());
            lp.width = 800;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.gravity = Gravity.CENTER;
            d.getWindow().setAttributes(lp);
        }

        Log.v("Updating Recycler", " trying update Dialog");
        TextView txtHeader = (TextView) d.findViewById(R.id.txtHeader);
        d.findViewById(R.id.listView).setVisibility(View.GONE);
        RecyclerView recyclerView = (RecyclerView) d.findViewById(R.id.recyclerView);
        recyclerView.setVisibility(View.VISIBLE);
        txtHeader.setText(header);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(new GridAdapter(context, data));
        if (!d.isShowing()) {
            d.show();
        }

        d.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dialogDisplayed = "";
            }
        });
    }

    public void updateReadingDialog(Context context, String header, ArrayList<ModelData> data,boolean rectifierFault) {
        dialogDisplayed = header;
        try {
            if (d != null && d.isShowing()) {
                Log.v("UpdateDialog", " " + header);
                //View v = LayoutInflater.from(context).inflate(R.layout.dialog_readings, null);
                d.findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        d.dismiss();
                    }
                });
                ListView listView = (ListView) d.findViewById(R.id.listView);
                listView.setAdapter(new DisplayAdapter_BigFont(context, data));
                if(header.contains("RECTIFIER") && rectifierFault){
                    d.findViewById(R.id.btnResetRectifier).setVisibility(View.VISIBLE);
                }else{
                    d.findViewById(R.id.btnResetRectifier).setVisibility(View.GONE);
                }
                d.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        dialogDisplayed = "";
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSettingDataDialog(Context context, String header, ArrayList<ModelData> data) {
        if (d == null || !d.isShowing()) {
            d = new Dialog(context);
            d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            View v = LayoutInflater.from(context).inflate(R.layout.dialog_readings, null);
            d.setContentView(v);
            v.findViewById(R.id.imgCross).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    d.dismiss();
                }
            });
            TextView txtHeader = (TextView) v.findViewById(R.id.txtHeader);
            ListView listView = (ListView) v.findViewById(R.id.listView);
            txtHeader.setText(header);
            listView.setAdapter(new DisplayAdapter_Settings(context, data));
            d.show();
        }
    }

    public void showCompanyDialog(Context context) {
        d = new Dialog(context);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(context).inflate(R.layout.aboutcompany, null);
        TextView txtMessage = (TextView) v.findViewById(R.id.txtMessage);
        TextView txtContactUs = (TextView) v.findViewById(R.id.txtContactUs);
        txtMessage.setText(message);
        txtContactUs.setText(contactUs);
        d.setContentView(v);
        d.show();
    }

    public void showPasswordDialog(final Context context, final SuccessInterface successInterface) {
        d = new Dialog(context);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View v = LayoutInflater.from(context).inflate(R.layout.password_layout, null);
        final EditText editPassword = (EditText) v.findViewById(R.id.editpassword);

        v.findViewById(R.id.btnSubmit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editPassword.getText().toString().equalsIgnoreCase("1188")) {
                    d.dismiss();
                    successInterface.onSuccess();
                } else {
                    editPassword.setError("Invalid Password");
                }
            }
        });
        d.setContentView(v);
        d.show();
    }


    public static File getEventLogsFile() {
        //String path = Environment.getExternalStorageDirectory().toString() + "/UTL";
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MPPT/" + EventLogFileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static File getConfigLogsFile() {
        //String path = Environment.getExternalStorageDirectory().toString() + "/UTL";
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MPPT/" + ConfigLogFileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static File getSettingLogsFile() {
        //String path = Environment.getExternalStorageDirectory().toString() + "/UTL";
        File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/MPPT/" + SettingLogFileName);
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return f;
    }

    public static String getEventLogFileName() {
        return EventLogFileName;
    }

    public static String getConfigLogFileName() {
        return ConfigLogFileName;
    }

    public static String getSettingsLogFileName() {
        return SettingLogFileName;
    }

    public static void createColumn(Row row, Object title, CellStyle style, int columnIndex) {
        c = row.createCell(columnIndex);
        c.setCellValue(title + "");
        c.setCellStyle(style);
    }

    static Sheet sheet1 = null;

    public static void generateEventLogFile(Context context, String eventLogData, String sysMode) {

        String[] monitoringData = context.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");
        boolean success = false;
        Log.v("Saving File", "Faulty Report");
        Workbook wb = new HSSFWorkbook();

        //Cell style for header row

        //New Sheet

        int rowAvailable = 0;
        InputStream ExcelFileToRead = null;
        try {
            ExcelFileToRead = new FileInputStream(getFilePath() + getEventLogFileName());
            wb = new HSSFWorkbook(ExcelFileToRead);
            //Iterator<Sheet> iter=hsWb.iterator();

            Iterator<Row> rows = wb.getSheetAt(0).iterator();
            while (rows.hasNext()) {
                sheet1 = wb.getSheetAt(0);
                rowAvailable++;
                Log.v("Num rows=", rows.next().toString() + "  ");
            }
        } catch (FileNotFoundException e) {

            e.printStackTrace();
        } catch (Exception e) {

        }
        CellStyle csHeading = wb.createCellStyle();
        csHeading.setFillForegroundColor(HSSFColor.LIME.index);
        csHeading.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csHeading.setWrapText(true);

        CellStyle csData = wb.createCellStyle();
        csData.setFillForegroundColor(HSSFColor.WHITE.index);
        csData.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csData.setAlignment(HorizontalAlignment.LEFT);

        if (rowAvailable == 0) {


            sheet1 = wb.createSheet(EventLogFileName);
            // Generate column headings
            row = sheet1.createRow(0);
            rowAvailable++;
            createColumn(row, "LaMarche Mfg. Company A77DE Event Log File", csHeading, 3);
            row = sheet1.createRow(1);
            rowAvailable++;
            createColumn(row, "Created on " + createdAtFormat.format(Calendar.getInstance().getTime()), csHeading, 3);
            row = sheet1.createRow(2);
            rowAvailable++;
            createColumn(row, "Model Number " + monitoringData[Config.Mon_ModelNumber], csHeading, 3);
            row = sheet1.createRow(3);
            rowAvailable++;
            createColumn(row, "System Name/ID " + monitoringData[Config.Mon_System_Name], csHeading, 3);
            row = sheet1.createRow(4);
            rowAvailable++;
            createColumn(row, "System Location  " + monitoringData[Config.Mon_SystemLocation], csHeading, 3);
            row = sheet1.createRow(5);
            rowAvailable++;
            createColumn(row, "Firmware  xxxxxxxxxxxxxxxxxx ", csHeading, 3);

            c = row.createCell(7);
            c.setCellValue("DATE");
            c.setCellStyle(csHeading);

            createColumn(row, "DATE", csHeading, 0);
            createColumn(row, "Time", csHeading, 1);
            createColumn(row, "Batt. V (V)", csHeading, 2);
            createColumn(row, "Batt. Amps(A)", csHeading, 3);
            createColumn(row, "Battery SOC", csHeading, 4);
            createColumn(row, "Battery Temperature", csHeading, 5);
            createColumn(row, "SYS. Temperature", csHeading, 6);
            createColumn(row, "SYS. MODE", csHeading, 7);

            createColumn(row, "PHASE 1 (ACV)", csHeading, 8);
            createColumn(row, "PHASE 2 (ACV)", csHeading, 9);
            createColumn(row, "PHASE 3 (ACV)", csHeading, 10);
            createColumn(row, "PHASE 1 (ACA)", csHeading, 11);
            createColumn(row, "PHASE 2 (ACA)", csHeading, 12);
            createColumn(row, "PHASE 3 (ACA)", csHeading, 13);
            createColumn(row, "PHASE 1 (ACHz)", csHeading, 14);
            createColumn(row, "PHASE 2 (ACHz)", csHeading, 15);
            createColumn(row, "PHASE 3 (ACHz)", csHeading, 16);
            createColumn(row, "INPUT POWER", csHeading, 17);
            createColumn(row, "INPUT ENERGY", csHeading, 18);
            createColumn(row, "RECTIFIER VOLT.", csHeading, 19);
            createColumn(row, "RECTIFIER CURR", csHeading, 20);
            createColumn(row, "INVERTER VOLT", csHeading, 21);
            createColumn(row, "INVRTER CURR.", csHeading, 22);
            createColumn(row, "INVERTER FREQ.", csHeading, 23);
            createColumn(row, "BYPASS VOLT", csHeading, 24);
            createColumn(row, "BYPASS FREQ.", csHeading, 25);
            createColumn(row, "EVENT", csHeading, 26);

        }//else{
        row = sheet1.createRow(++rowAvailable);
        Log.v("Row= ", rowAvailable + "");
        c = row.createCell(0);
        c.setCellStyle(csHeading);
        //}
        //String[] records = eventLogData.split(",");
        String[] records = new String[27];
        try {
            records[0] = dateFormat.format(Calendar.getInstance().getTime());
            records[1] = timeFormat.format(Calendar.getInstance().getTime());
            records[2] = monitoringData[Config.Mon_BatterVol];
            records[3] = monitoringData[Config.Mon_BatChrgDiscCurrent];   // need to verify
            records[4] = monitoringData[Config.Mon_BatterySOC];
            records[5] = monitoringData[Config.Mon_BatTemp];
            records[6] = monitoringData[Config.Mon_BatTemp];   //Sys Temp
            records[7] = sysMode;
            records[8] = monitoringData[Config.Mon_InputVPhase1];
            records[9] = monitoringData[Config.Mon_InputVPhase2];
            records[10] = monitoringData[Config.Mon_InputVPhase3];
            records[11] = monitoringData[Config.Mon_InputCPhase1];
            records[12] = monitoringData[Config.Mon_InputCPhase2];
            records[13] = monitoringData[Config.Mon_InputCPhase3];
            records[14] = monitoringData[Config.Mon_InputFPhase1];
            records[15] = monitoringData[Config.Mon_InputFPhase2];
            records[16] = monitoringData[Config.Mon_InputFPhase3];
            records[17] = monitoringData[Config.Mon_InputPower];
            records[18] = "Input Energy";
            records[19] = monitoringData[Config.Mon_RectifierVoltage];
            records[20] = monitoringData[Config.Mon_RectifierCurrent];
            records[21] = monitoringData[Config.Mon_InvVoltage];
            records[22] = monitoringData[Config.Mon_InvCurrent];
            records[23] = monitoringData[Config.Mon_InvFrequency];
            records[24] = monitoringData[Config.Mon_ByPassVoltage];
            records[25] = monitoringData[Config.Mon_ByPassFrequency];
            records[26] = eventLogData;

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < records.length; i++) {
            createColumn(row, records[i], csData, i);
        }

        sheet1.setColumnWidth(0, (15 * 300));
        sheet1.setColumnWidth(1, (15 * 350));
        sheet1.setColumnWidth(2, (15 * 350));
        sheet1.setColumnWidth(3, (15 * 300));

        // Create a path where we will place our List of objects on external storage
        String path = getFilePath() + getEventLogFileName();
        // Create a path where we will place our List of objects on external storage
        File file = new File(path);
        // File file = getEventLogsFileName();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
    }


    public static void generateConfigLogFile(Context context, String eventLogData, String sysMode) {

        String[] monitoringData = context.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");

        ArrayList<ModelData> listData = prepareConfigData(context);
        boolean success = false;
        Log.v("Saving File", "Config Report");
        Workbook wb = new HSSFWorkbook();

        //Cell style for header row

        //New Sheet

        int rowAvailable = 0;
        InputStream ExcelFileToRead = null;
        CellStyle csHeading = wb.createCellStyle();
        csHeading.setFillForegroundColor(HSSFColor.LIME.index);
        csHeading.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csHeading.setWrapText(true);

        CellStyle csData = wb.createCellStyle();
        csData.setFillForegroundColor(HSSFColor.WHITE.index);
        csData.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csData.setAlignment(HorizontalAlignment.LEFT);

        sheet1 = wb.createSheet(ConfigLogFileName);
        sheet1.setColumnWidth(0, (15 * 750));
        sheet1.setColumnWidth(1, (15 * 450));
        sheet1.setColumnWidth(2, (15 * 450));
        sheet1.setColumnWidth(3, (15 * 450));
        sheet1.setColumnWidth(4, (15 * 450));
        // Generate column headings
        row = sheet1.createRow(0);
        rowAvailable++;
        createColumn(row, "LaMarche Mfg. Company A77DE Event Log File", csHeading, 0);
        row = sheet1.createRow(1);
        rowAvailable++;
        createColumn(row, "Created on " + createdAtFormat.format(Calendar.getInstance().getTime()), csHeading, 0);
        row = sheet1.createRow(2);
        rowAvailable++;
        createColumn(row, "Model Number " + monitoringData[Config.Mon_ModelNumber], csHeading, 0);
        row = sheet1.createRow(3);
        rowAvailable++;
        createColumn(row, "System Name/ID " + monitoringData[Config.Mon_System_Name], csHeading, 0);
        row = sheet1.createRow(4);
        rowAvailable++;
        createColumn(row, "System Location  " + monitoringData[Config.Mon_SystemLocation], csHeading, 0);
        row = sheet1.createRow(5);
        rowAvailable++;
        createColumn(row, "Firmware  xxxxxxxxxxxxxxxxxx ", csHeading, 0);

        c = row.createCell(7);
        c.setCellValue("DATE");
        c.setCellStyle(csHeading);
        createColumn(row, "SETTING NAME", csHeading, 0);
        createColumn(row, "VALUE", csHeading, 1);
        createColumn(row, "UNITS", csHeading, 2);
        createColumn(row, "DEFAULT VALUES", csHeading, 3);
        createColumn(row, "VALID SETTINGS", csHeading, 4);

        row = sheet1.createRow(++rowAvailable);
        Log.v("Row= ", rowAvailable + "");
        c = row.createCell(0);
        c.setCellStyle(csHeading);

        for (int i = 0; i < listData.size(); i++) {
            row = sheet1.createRow(++rowAvailable);
            if (listData.get(i).value.contains("Header")) {
                createColumn(row, listData.get(i).name, csHeading, 0);
                createColumn(row, "", csHeading, 1);
                createColumn(row, "", csHeading, 2);
                createColumn(row, "", csHeading, 3);
                createColumn(row, "", csHeading, 4);

            } else {
                createColumn(row, listData.get(i).name, csData, 0);
                createColumn(row, listData.get(i).value, csData, 1);
                createColumn(row, listData.get(i).unit, csData, 2);
                createColumn(row, listData.get(i).defaultValues, csData, 3);
                createColumn(row, listData.get(i).validSettings, csData, 4);
            }
        }

        // Create a path where we will place our List of objects on external storage
        String path = getFilePath() + getConfigLogFileName();
        // Create a path where we will place our List of objects on external storage
        File file = new File(path);
        // File file = getEventLogsFileName();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
    }

    public static void generateSettingLogFile(Context context, String eventLogData, String sysMode) {

        String[] monitoringData = context.getSharedPreferences(Config.Prefernces, 0).getString(Config.MonitoringReading, Config.defaultMontoringData).split(",");

        ArrayList<ModelData> listData = prepareSettingsData(context);
        boolean success = false;
        Log.v("Saving File", "Setting Report");
        Workbook wb = new HSSFWorkbook();

        //Cell style for header row

        //New Sheet

        int rowAvailable = 0;
        InputStream ExcelFileToRead = null;
        CellStyle csHeading = wb.createCellStyle();
        csHeading.setFillForegroundColor(HSSFColor.LIME.index);
        csHeading.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csHeading.setWrapText(true);

        CellStyle csData = wb.createCellStyle();
        csData.setFillForegroundColor(HSSFColor.WHITE.index);
        csData.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        csData.setAlignment(HorizontalAlignment.LEFT);

        sheet1 = wb.createSheet(ConfigLogFileName);
        sheet1.setColumnWidth(0, (15 * 750));
        sheet1.setColumnWidth(1, (15 * 450));
        sheet1.setColumnWidth(2, (15 * 450));
        sheet1.setColumnWidth(3, (15 * 450));
        sheet1.setColumnWidth(4, (15 * 450));
        // Generate column headings
        row = sheet1.createRow(0);
        rowAvailable++;
        createColumn(row, "LaMarche Mfg. Company A77DE Event Log File", csHeading, 0);
        row = sheet1.createRow(1);
        rowAvailable++;
        createColumn(row, "Created on " + createdAtFormat.format(Calendar.getInstance().getTime()), csHeading, 0);
        row = sheet1.createRow(2);
        rowAvailable++;
        createColumn(row, "Model Number " + monitoringData[Config.Mon_ModelNumber], csHeading, 0);
        row = sheet1.createRow(3);
        rowAvailable++;
        createColumn(row, "System Name/ID " + monitoringData[Config.Mon_System_Name], csHeading, 0);
        row = sheet1.createRow(4);
        rowAvailable++;
        createColumn(row, "System Location  " + monitoringData[Config.Mon_SystemLocation], csHeading, 0);
        row = sheet1.createRow(5);
        rowAvailable++;
        createColumn(row, "Firmware  xxxxxxxxxxxxxxxxxx ", csHeading, 0);

        row = sheet1.createRow(6);
        rowAvailable++;
        createColumn(row, "INSTRUCTIONS: ONLY CHANGES TO THE VALUE FIELD ARE RECOGNIZED.\n" +
                "VALUES ENTERED OUTSIDE OF THE VALID SETTINGS SPECIFIED ARE NOT UPLOADED.\n ", csHeading, 3);

        c = row.createCell(7);
        c.setCellValue("DATE");
        c.setCellStyle(csHeading);
        createColumn(row, "SETTING NAME", csHeading, 0);
        createColumn(row, "VALUE", csHeading, 1);
        createColumn(row, "UNITS", csHeading, 2);
        createColumn(row, "DEFAULT VALUES", csHeading, 3);
        createColumn(row, "VALID SETTINGS", csHeading, 4);

        row = sheet1.createRow(++rowAvailable);
        Log.v("Row= ", rowAvailable + "");
        c = row.createCell(0);
        c.setCellStyle(csHeading);

        for (int i = 0; i < listData.size(); i++) {
            row = sheet1.createRow(++rowAvailable);
            if (listData.get(i).value.contains("Header")) {
                createColumn(row, listData.get(i).name, csHeading, 0);
                createColumn(row, "", csHeading, 1);
                createColumn(row, "", csHeading, 2);
                createColumn(row, "", csHeading, 3);
                createColumn(row, "", csHeading, 4);

            } else {
                createColumn(row, listData.get(i).name, csData, 0);
                createColumn(row, listData.get(i).value, csData, 1);
                createColumn(row, listData.get(i).unit, csData, 2);
                createColumn(row, listData.get(i).defaultValues, csData, 3);
                createColumn(row, listData.get(i).validSettings, csData, 4);
            }
        }


        // Create a path where we will place our List of objects on external storage
        String path = getFilePath() + getSettingsLogFileName();
        // Create a path where we will place our List of objects on external storage
        File file = new File(path);
        // File file = getEventLogsFileName();
        FileOutputStream os = null;
        try {
            os = new FileOutputStream(file);
            wb.write(os);
            Log.w("FileUtils", "Writing file" + file);
            success = true;
        } catch (IOException e) {
            Log.w("FileUtils", "Error writing " + file, e);
        } catch (Exception e) {
            Log.w("FileUtils", "Failed to save file", e);
        } finally {
            try {
                if (null != os)
                    os.close();
            } catch (Exception ex) {
            }
        }
    }

    private static ArrayList<ModelData> prepareConfigData(Context context) {
        String[] configurationData = context.getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.defaultMontoringData).split(",");

        String[] values = {configurationData[Conf_SystemNameIndex],
                configurationData[Conf_Location_Index],
                configurationData[Conf_Time_Date_Index],
                "DisplayTimeout",
                "Password",
                "Inverter Header",
                configurationData[Conf_intOutput_Voltage_Index],
                configurationData[Conf_Inverter_Frequency_Range_Index],
                configurationData[Conf_Output_Phase_Shift],
                "Battery Header",
                configurationData[Conf_Battery_Type_Index],
                configurationData[Conf_Battery_Cells_Index],
                configurationData[Conf_Battery_capacity_Index],
                configurationData[Conf_NumStrings_Index],
                configurationData[Conf_FloatVoltage_Index],
                configurationData[Conf_Equalize_Voltage_Index],
                configurationData[Conf_Equalize_Timer_Hours_Index]
                , configurationData[Conf_Equalize_Timer_Mode_Index],
                configurationData[Conf_Enable_Remote_Eq_Index],
                configurationData[Conf_Temperature_Compensation_Index],
                configurationData[Conf_Low_Voltage_Shut_Index],
                "Static Switch Header",
                configurationData[Conf_Load_Voltage_High_Limit_Index],
                configurationData[Conf_Load_Voltage_Low_LIMIT_Index],
                configurationData[Conf_Inverter_Voltage_Range_Index],
                configurationData[Conf_Bypass_Voltage_RangeIndex],
                configurationData[Conf_Inverter_Frequency_Range_Index],
                configurationData[Conf_Bypass_Frequency_Range_Index],
                configurationData[Conf_Out_of_Sync_Check_Index],
                configurationData[Conf_Retransfer_Delay_Index],
                "Communication Header",
                "Address",
                "Baudrate",
                "Parity",
                "Stopbits",
                "Battery Alarm Header",
                configurationData[Conf_High_Voltage_Index],
                configurationData[Conf_Low_Voltage_Index],
                configurationData[Conf_Battery_over_temp_Index],
                configurationData[Conf_End_Discharge_Index],
                "Rectifier Alarm Header",
                configurationData[Conf_AC_Fail_2_Index],
                configurationData[Conf_AC_Fail_UPPER_Index],
                configurationData[Conf_High_DC_Volt_Shutdown],
                configurationData[Conf_Low_DC_Current_Index],
                configurationData[Conf_Rectifier_over_temp_Index],
                "Inverter Alarm Header",
                configurationData[Conf_Low_AC_Output_Index],
                configurationData[Conf_High_AC_Output_Index],
                configurationData[Conf_Inverter_Overtemp_Index],
                configurationData[Conf_Current_Limit_Index],
                "Ups Alarm header",
                configurationData[Conf_AC_Input_Breaker_Trip_Index],
                configurationData[Conf_Charger_Output_Breaker_Trip_Index],
                configurationData[Conf_Battery_breaker_trip_Index],
                configurationData[Conf_AC_Output_Breaker_Trip_Index],
                configurationData[Conf_Bypass_Breaker_Trip],
                configurationData[Conf_LCD_Fail_Index],
                "Summary Header",
                configurationData[Conf_AC_Fail_UPPER_Index],
                configurationData[Conf_Low_DC_Current_Index],
                configurationData[Conf_Overload_Index],
                configurationData[Conf_Ground_Detection_Index],
                configurationData[Conf_Battery_Over_Temperature_Index]
        };
        String[] names = configNames.split(",");
        String[] defaultValues = configDefaultValues.split(",");
        String[] configValidSettings = configValidValues.split(",");
        String[] units = configUnits.split(",");

        Log.v("Names= ", names.length + "  ");
        Log.v("defaultValues= ", defaultValues.length + "  ");
        Log.v("configValidSettings= ", configValidSettings.length + "  ");
        Log.v("units= ", units.length + "  ");
        Log.v("Values= ", values.length + "  ");
        ArrayList<ModelData> listData = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            listData.add(new ModelData(names[i], values[i], units[i], defaultValues[i], configValidSettings[i]));
        }

        //listData.add(new ModelData(names[0],"",units[0],));
        return listData;

    }

    private static ArrayList<ModelData> prepareSettingsData(Context context) {
        String[] configurationData = context.getSharedPreferences(Config.Prefernces, 0).getString(Config.Configuration_Reading, Config.defaultMontoringData).split(",");
        String[] names = SETTING_NAME.split(",");
        String[] defaultValues = SETTING_DEFAULT_VALUES.split(",");
        String[] configValidSettings = SETTING_VALID_SETTINGS.split(",");
        String[] units = SETTING_UNITS.split(",");
        String[] values = SETTING_VALUES.split(",");

        Log.v("Names= ", names.length + "  ");
        Log.v("defaultValues= ", defaultValues.length + "  ");
        Log.v("configValidSettings= ", configValidSettings.length + "  ");
        Log.v("units= ", units.length + "  ");
        Log.v("Values= ", values.length + "  ");
        ArrayList<ModelData> listData = new ArrayList<>();
        for (int i = 0; i < names.length; i++) {
            listData.add(new ModelData(names[i], values[i], units[i], defaultValues[i], configValidSettings[i]));
        }

        //listData.add(new ModelData(names[0],"",units[0],));
        return listData;

    }


    public static String getFilePath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/MPPT/";
    }


    public static ArrayList<ModelData> readSettingFile(Context context, String filename) {

        if (isExternalStorageReadOnly()) {
            Log.e("TAG", "Storage not available or read only");
            return null;
        }

        ArrayList<ModelData> listData = new ArrayList<>();

        try {
// Creating Input Stream
            File file = getSettingLogsFile();
            FileInputStream myInput = new FileInputStream(file);

// Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

// Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

// Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

/** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();
            int count = 0;
            ModelData model;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                model = new ModelData();
                count = 0;
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if (count == 0)
                        model.name = myCell.toString();
                    if (count == 1)
                        model.value = myCell.toString();
                    if (count == 2)
                        model.unit = myCell.toString();
                    if (count == 3)
                        model.defaultValues = myCell.toString();
                    if (count == 4)
                        model.validSettings = myCell.toString();
                    count++;
                    Log.d("TAG", "Cell Value: " + myCell.toString() + " count= " + count);
                    //Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
                listData.add(model);
            }

            Log.v("ListData=", listData.size() + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public static ArrayList<ModelData> readConfigFile(Context context, String filename) {

        if (isExternalStorageReadOnly()) {
            Log.e("TAG", "Storage not available or read only");
            return null;
        }

        ArrayList<ModelData> listData = new ArrayList<>();

        try {
// Creating Input Stream
            File file = getConfigLogsFile();
            FileInputStream myInput = new FileInputStream(file);

// Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

// Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

// Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

/** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();
            int count = 0;
            ModelData model;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                model = new ModelData();
                count = 0;
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    if (count == 0)
                        model.name = myCell.toString();
                    if (count == 1)
                        model.value = myCell.toString();
                    if (count == 2)
                        model.unit = myCell.toString();
                    if (count == 3)
                        model.defaultValues = myCell.toString();
                    if (count == 4)
                        model.validSettings = myCell.toString();
                    count++;
                    Log.d("TAG", "Cell Value: " + myCell.toString() + " count= " + count);
                    //Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
                listData.add(model);
            }

            Log.v("ListData=", listData.size() + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public static ArrayList<ModelEvents> readEventFile(Context context, String filename) {

        if (isExternalStorageReadOnly()) {
            Log.e("TAG", "Storage not available or read only");
            return null;
        }

        ArrayList<ModelEvents> listData = new ArrayList<>();

        try {
// Creating Input Stream
            File file = getEventLogsFile();
            FileInputStream myInput = new FileInputStream(file);

// Create a POIFSFileSystem object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(myInput);

// Create a workbook using the File System
            HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);

// Get the first sheet from workbook
            HSSFSheet mySheet = myWorkBook.getSheetAt(0);

/** We now need something to iterate through the cells.**/
            Iterator rowIter = mySheet.rowIterator();
            int count = 0;
            ModelEvents model;
            while (rowIter.hasNext()) {
                HSSFRow myRow = (HSSFRow) rowIter.next();
                Iterator cellIter = myRow.cellIterator();
                model = new ModelEvents();
                count = 0;
                while (cellIter.hasNext()) {
                    HSSFCell myCell = (HSSFCell) cellIter.next();
                    model.values.add(myCell.toString());
                    //count++;
                    Log.d("TAG", "Cell Value: " + myCell.toString() + " count= " + count);
                    //Toast.makeText(context, "cell Value: " + myCell.toString(), Toast.LENGTH_SHORT).show();
                }
                listData.add(model);
            }

            Log.v("ListData=", listData.size() + " ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }

    public static boolean isExternalStorageReadOnly() {
        String extStorageState = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(extStorageState)) {
            return true;
        }
        return false;
    }

    public static void testCommand(Context context, String command) {
        if (BluetoothService.getInstance() != null) {
            Log.v("Command=  ", command + "");
            byte[] date = command.getBytes();
            if (BluetoothService.getInstance() != null && BluetoothService.getInstance().mConnectedThread != null) {
                BluetoothService.getInstance().mConnectedThread.write(date);
            } else {
                Toast.makeText(context, "Please connect with device to apply settings", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void testCommandWithoutError(Context context, String command) {
        if (BluetoothService.getInstance() != null) {
            Log.v("Command=  ", command + "");
            byte[] date = command.getBytes();
            if (BluetoothService.getInstance() != null && BluetoothService.getInstance().mConnectedThread != null) {
                BluetoothService.getInstance().mConnectedThread.write(date);
            }
        }
    }

    public static boolean isBluetoothConnected_UPS() {
        if (BluetoothService.getInstance() != null && BluetoothService.getInstance().mConnectedThread != null) {
            return true;
        }
        return false;
    }

}
