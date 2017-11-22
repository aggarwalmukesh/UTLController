package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.app.utlcontroller.Model.HelperClass;
import com.app.utlcontroller.R;

import java.util.List;

/**
 * Created by m on 5/10/2016.
 */
public class MyAdapter extends BaseAdapter {
    Context context;
    LayoutInflater mLayoutInflater;
    List<HelperClass> list;

    public MyAdapter(Context context, List<HelperClass> list) {
        this.context = context;
        this.list = list;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.adapter_blu, null, false);
        }
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView mac = (TextView) convertView.findViewById(R.id.mac);
        name.setText(list.get(position).getName());
        mac.setText(list.get(position).getMac());


        return convertView;
    }
}
