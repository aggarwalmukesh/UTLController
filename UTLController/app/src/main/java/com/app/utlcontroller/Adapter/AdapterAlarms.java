package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.app.utlcontroller.Model.ModelAlarm;
import com.app.utlcontroller.R;

import java.util.ArrayList;

/**
 * Created by Mints on 6/8/2017.
 */

public class AdapterAlarms extends BaseAdapter {
    Context context;
    ArrayList<ModelAlarm> data;
    public AdapterAlarms(Context context,ArrayList<ModelAlarm> data){
        this.data=data;
        this.context=context;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView= LayoutInflater.from(context).inflate(R.layout.row_alarms,null);
        TextView txtAlarmStatus= (TextView) convertView.findViewById(R.id.txtAlarmStatus);
        ImageView imgIndicator= (ImageView) convertView.findViewById(R.id.imgIndicator);
        if(data.get(position).isWarningAlarm){
            imgIndicator.setImageResource(R.drawable.drawable_circle_red);
        }else{
            imgIndicator.setImageResource(R.drawable.drawable_circle_green);
        }
        txtAlarmStatus.setText(data.get(position).title);
        return convertView;
    }
}
