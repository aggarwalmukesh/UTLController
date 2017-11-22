package com.app.utlcontroller.Controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.app.utlcontroller.R;

import java.util.ArrayList;

/**
 * Created by Mints on 5/12/2017.
 */

public class AlarmFragment extends Fragment {

    String[] list={};
    ListView listView;
    ArrayAdapter<String> adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_alarms,null);
        listView= (ListView) v.findViewById(R.id.listView);
        adapter=new ArrayAdapter<String>(getActivity(),R.layout.row_alarms,list);
        listView.setAdapter(adapter);
        return v;
    }


    public void updateData(String data){
        list=data.split(",");
        adapter.notifyDataSetChanged();
    }

}
