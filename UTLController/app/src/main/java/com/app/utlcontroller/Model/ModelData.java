package com.app.utlcontroller.Model;

/**
 * Created by Mints on 5/1/2017.
 */

public class ModelData {
    public String name="";
    public String value="";
    public String unit="";
    public String defaultValues="";
    public String validSettings="";


    public ModelData(String name, String value, String unit) {
        this.name = name;
        this.value = value;
        this.unit = unit;
    }

    public ModelData(String name, String value, String unit, String defaultValues, String validSettings) {
        this.name = name;
        this.value = value;
        this.unit = unit;
        this.defaultValues = defaultValues;
        this.validSettings = validSettings;
    }


    public ModelData(String name, String value) {
        this.name = name;
        this.value = value;
    }



    public ModelData() {
    }

}
