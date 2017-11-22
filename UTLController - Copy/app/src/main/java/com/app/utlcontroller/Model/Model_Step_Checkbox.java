package com.app.utlcontroller.Model;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;

/**
 * Created by Mints on 7/22/2017.
 */

public class Model_Step_Checkbox {
    public String name;
    public boolean status;
    public String firstOption,secondOption;
    public int viewType;
    public String[] values;

    public float maxValue,minValue,stepSize;
    public float value;
    public String unit;
    public int decimalPlaces;
    public boolean isEditable;
    public String type1Value;

    public String keyBoardType= "ANY";

    public Model_Step_Checkbox(String name,String firstOption,String secondOption,boolean initialStatus){
        this.name=name;
        this.firstOption=firstOption;
        this.secondOption=secondOption;
        this.status=initialStatus;
        this.viewType=3;
        isEditable=true;
    }

    public Model_Step_Checkbox(String name,String firstOption,String secondOption,boolean initialStatus,boolean isEditable){
        this.name=name;
        this.firstOption=firstOption;
        this.secondOption=secondOption;
        this.status=initialStatus;
        this.viewType=3;
        this.isEditable=isEditable;
    }

    public Model_Step_Checkbox(String name,float value,String unit,float minValue,float maxValue,float stepSize,int decimalPlaces){
        this.name=name;
        this.value=value;
        this.unit=unit;
        this.maxValue=maxValue;
        this.minValue=minValue;
        this.stepSize=stepSize;
        this.decimalPlaces=decimalPlaces;
        this.viewType=2;
    }

    public Model_Step_Checkbox(String name,float value,String unit,String[] values){
        this.name=name;
        this.value=value;
        this.unit=unit;
        this.values=values;
        this.viewType=4;
    }

    public Model_Step_Checkbox(String title,String value,boolean isEditable){
        this.viewType=1;
        this.name=title;
        this.unit="";
        this.type1Value=value;
        this.isEditable=isEditable;
    }

    public Model_Step_Checkbox(String title,String value,boolean isEditable,String unit){
        this.viewType=1;
        this.name=title;
        this.type1Value=value;
        this.isEditable=isEditable;
        this.unit=unit;
    }

    public Model_Step_Checkbox(String title,String value,boolean isEditable,String keyboardType,String unit){
        this.viewType=1;
        this.name=title;
        this.unit=unit;
        this.type1Value=value;
        this.isEditable=isEditable;
        this.keyBoardType=keyboardType;
    }

    public Model_Step_Checkbox(String s, String button) {
        viewType=5;
        this.name=s;
    }
}
