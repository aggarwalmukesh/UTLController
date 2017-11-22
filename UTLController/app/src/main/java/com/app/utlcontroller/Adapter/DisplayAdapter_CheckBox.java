package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.utlcontroller.Controller.PositionCallback;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelData_CheckBox;
import com.app.utlcontroller.Model.ModelData_Step;
import com.app.utlcontroller.R;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Created by Mints on 5/1/2017.
 */

public class DisplayAdapter_CheckBox extends BaseAdapter {
    Context context;
    ArrayList<ModelData_CheckBox> listData;
    PositionCallback callback;

    public DisplayAdapter_CheckBox(Context context, ArrayList<ModelData_CheckBox> listData) {
        this.context = context;
        this.listData = listData;
    }
    public DisplayAdapter_CheckBox(Context context, ArrayList<ModelData_CheckBox> listData, PositionCallback callback) {
        this.context = context;
        this.listData = listData;
        this.callback=callback;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.row_checkbox, null);
            holder = new MyViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        final ModelData_CheckBox data = listData.get(position);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.radioFirst = (RadioButton) convertView.findViewById(R.id.radioFirst);
        holder.radioSecond = (RadioButton) convertView.findViewById(R.id.radioSecond);
        holder.radioFirst.setText(data.firstOption);
        holder.radioSecond.setText(data.secondOption);

        holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
        holder.txtTitle.setText(data.name);

        Log.v("OnScroll ", data.name+"      "+ data.status);
        if(data.status){
            ((RadioButton)holder.radioGroup.getChildAt(0)).setChecked(true);
            ((RadioButton)holder.radioGroup.getChildAt(1)).setChecked(false);
        }else{
            ((RadioButton)holder.radioGroup.getChildAt(1)).setChecked(true);
            ((RadioButton)holder.radioGroup.getChildAt(0)).setChecked(false);
        }

        holder.radioFirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("check change ", data.name+"      "+ data.status+ " isFirstChecked  "+holder.radioFirst.isChecked());
                 data.status=true;
            }
        });

        holder.radioSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("check change ", data.name+"      "+ data.status+ " isSecondChecked  "+holder.radioSecond.isChecked());
                data.status=false;
            }
        });

        /*holder.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if(checkedId==R.id.radioFirst){
                    data.status=true;
                }else{
                    data.status=false;
                }
                Log.v("check change ", data.name+"      "+ data.status+ checkedId);

            }
        });*/

       /* holder.radioFirst.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    data.status=true;

                Log.v("RadioFirst= ",isChecked+"  "+data.name+"    "+ data.status);

            }
        });

        holder.radioSecond.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    data.status=false;

                Log.v("RadioSecond= ",isChecked+"  "+data.name);

            }
        });*/





        return convertView;
    }

    class MyViewHolder {
        TextView txtTitle;
        RadioButton radioFirst,radioSecond;
        RadioGroup radioGroup;
    }
}
