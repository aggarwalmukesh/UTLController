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

public class DisplayAdapter_BigFont extends BaseAdapter {
    Context context;
    ArrayList<ModelData> listData;
    PositionCallback callback;

    public DisplayAdapter_BigFont(Context context, ArrayList<ModelData> listData) {
        this.context = context;
        this.listData = listData;
    }
    public DisplayAdapter_BigFont(Context context, ArrayList<ModelData> listData, PositionCallback callback) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.layout_readingdisplay_bigfont, null);
            holder = new MyViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        ModelData data = listData.get(position);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.txtReadings = (TextView) convertView.findViewById(R.id.txtReading);
        holder.txtTitle.setText(data.name);
        holder.txtReadings.setText(data.value);

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
        TextView txtTitle, txtReadings;
    }
}
