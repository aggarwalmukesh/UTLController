package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.app.utlcontroller.Controller.PositionCallback;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.R;

import java.util.ArrayList;

/**
 * Created by Mints on 5/1/2017.
 */

public class DisplayAdapter_Settings extends BaseAdapter {
    Context context;
    ArrayList<ModelData> listData;
    PositionCallback callback;

    public DisplayAdapter_Settings(Context context, ArrayList<ModelData> listData) {
        this.context = context;
        this.listData = listData;
    }
    public DisplayAdapter_Settings(Context context, ArrayList<ModelData> listData, PositionCallback callback) {
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
        MyViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_settings, null);
            holder = new MyViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        ModelData data = listData.get(position);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.txt1 = (TextView) convertView.findViewById(R.id.txt1);
        holder.txt2 = (TextView) convertView.findViewById(R.id.txt2);
        holder.txt3 = (TextView) convertView.findViewById(R.id.txt3);
        holder.txt4 = (TextView) convertView.findViewById(R.id.txt4);
        holder.dataLayout = convertView.findViewById(R.id.dataLayout);
        holder.txtTitle.setText(data.name);

        if(data.value.length()==0 && data.validSettings.length()==0 && data.defaultValues.length()==0){
            holder.dataLayout.setVisibility(View.GONE);
        }else{
            holder.dataLayout.setVisibility(View.VISIBLE);
            holder.txt1.setText(data.value);
            holder.txt2.setText(data.unit);
            holder.txt3.setText(data.defaultValues);
            holder.txt4.setText(data.validSettings);
        }

        if(callback!=null) {
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clickPosition(position);
                }
            });
        }

        return convertView;
    }

    class MyViewHolder {
        TextView txtTitle, txt1,txt2,txt3,txt4;
        View dataLayout;
    }
}
