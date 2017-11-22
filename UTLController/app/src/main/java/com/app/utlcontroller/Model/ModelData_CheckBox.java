package com.app.utlcontroller.Model;

/**
 * Created by Mints on 5/10/2017.
 */

public class ModelData_CheckBox {
    public String name;
    public boolean status;
    public String firstOption,secondOption;

    public ModelData_CheckBox(String name,String firstOption,String secondOption,boolean initialStatus){
        this.name=name;
        this.firstOption=firstOption;
        this.secondOption=secondOption;
        this.status=initialStatus;
    }
}
