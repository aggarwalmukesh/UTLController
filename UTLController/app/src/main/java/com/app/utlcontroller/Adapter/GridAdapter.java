package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mints on 8/9/2017.
 */

public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyHolder> {
    Context context;
    ArrayList<ModelEvents> listData;

    public GridAdapter(Context context, ArrayList<ModelEvents> listData) {
        this.context = context;
        this.listData = listData;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_grid, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        List<String> values = listData.get(position).values;
        holder.txtTitle.setText(values.get(0));

        if (values.size() <=2) {
            holder.dataLayout.setVisibility(View.GONE);
            holder.txtValue.setText(values.get(1));
            holder.txtValue.setVisibility(View.VISIBLE);
        } else {
            holder.dataLayout.setVisibility(View.VISIBLE);
            holder.txtValue.setVisibility(View.GONE);
            for (int i = 0; i < values.size(); i++) {
                switch (i) {
                    case 1:
                        holder.txt1.setText(values.get(i));
                        break;
                    case 2:
                        holder.txt2.setText(values.get(i));
                        break;
                    case 3:
                        holder.txt3.setText(values.get(i));
                        break;
                    case 4:
                        holder.txt4.setText(values.get(i));
                        break;
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        TextView txt1, txt2, txt3, txt4;
        TextView txtTitle,txtValue;
        View dataLayout;

        public MyHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            txtValue = (TextView) itemView.findViewById(R.id.txtValue);
            dataLayout =  itemView.findViewById(R.id.dataLayout);
        }
    }
}


