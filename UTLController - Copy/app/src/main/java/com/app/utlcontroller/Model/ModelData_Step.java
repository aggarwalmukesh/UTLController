package com.app.utlcontroller.Model;

/**
 * Created by Mints on 5/10/2017.
 */

public class ModelData_Step {
    public float maxValue,minValue,stepSize;
    public String name;
    public float value;
    public String unit;
    public int decimalPlaces;


    public ModelData_Step(String name,float value,String unit,float minValue,float maxValue,float stepSize,int decimalPlaces){
        this.name=name;
        this.value=value;
        this.unit=unit;
        this.maxValue=maxValue;
        this.minValue=minValue;
        this.stepSize=stepSize;
        this.decimalPlaces=decimalPlaces;
    }
}
