package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelEvents;
import com.app.utlcontroller.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mints on 8/9/2017.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.MyHolder> {
    Context context;
    ArrayList<ModelEvents> listData;

    public EventAdapter(Context context, ArrayList<ModelEvents> listData) {
        this.context = context;
        this.listData = listData;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.row_scrollview, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        List<String> values = listData.get(position).values;
        holder.txtTitle.setText(values.get(0));

        if (values.size() <=1) {
            holder.dataLayout.setVisibility(View.GONE);
        } else {
            holder.dataLayout.setVisibility(View.VISIBLE);
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
                    case 5:
                        holder.txt5.setText(values.get(i));
                        break;
                    case 6:
                        holder.txt6.setText(values.get(i));
                        break;
                    case 7:
                        holder.txt7.setText(values.get(i));
                        break;
                    case 8:
                        holder.txt8.setText(values.get(i));
                        break;
                    case 9:
                        holder.txt9.setText(values.get(i));
                        break;
                    case 10:
                        holder.txt10.setText(values.get(i));
                        break;
                    case 11:
                        holder.txt11.setText(values.get(i));
                        break;
                    case 12:
                        holder.txt12.setText(values.get(i));
                        break;
                    case 13:
                        holder.txt13.setText(values.get(i));
                        break;
                    case 14:
                        holder.txt14.setText(values.get(i));
                        break;
                    case 15:
                        holder.txt15.setText(values.get(i));
                        break;
                    case 16:
                        holder.txt10.setText(values.get(i));
                        break;
                    case 17:
                        holder.txt17.setText(values.get(i));
                        break;
                    case 18:
                        holder.txt18.setText(values.get(i));
                        break;
                    case 19:
                        holder.txt19.setText(values.get(i));
                        break;
                    case 20:
                        holder.txt20.setText(values.get(i));
                        break;
                    case 21:
                        holder.txt21.setText(values.get(i));
                        break;
                    case 22:
                        holder.txt22.setText(values.get(i));
                        break;
                    case 23:
                        holder.txt23.setText(values.get(i));
                        break;
                    case 24:
                        holder.txt24.setText(values.get(i));
                        break;
                    case 25:
                        holder.txt25.setText(values.get(i));
                        break;
                    case 26:
                        holder.txt26.setText(values.get(i));
                        break;
                    case 27:
                        holder.txt27.setText(values.get(i));
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

        TextView txt1, txt2, txt3, txt4, txt5, txt6, txt7, txt8, txt9, txt10;
        TextView txt11, txt12, txt13, txt14, txt15, txt16, txt17, txt18, txt19, txt20;
        TextView txt21, txt22, txt23, txt24, txt25, txt26, txt27,txtTitle;
        View dataLayout;

        public MyHolder(View itemView) {
            super(itemView);
            txtTitle = (TextView) itemView.findViewById(R.id.txtTitle);
            txt1 = (TextView) itemView.findViewById(R.id.txt1);
            txt2 = (TextView) itemView.findViewById(R.id.txt2);
            txt3 = (TextView) itemView.findViewById(R.id.txt3);
            txt4 = (TextView) itemView.findViewById(R.id.txt4);
            txt5 = (TextView) itemView.findViewById(R.id.txt5);
            txt6 = (TextView) itemView.findViewById(R.id.txt6);
            txt7 = (TextView) itemView.findViewById(R.id.txt7);
            txt8 = (TextView) itemView.findViewById(R.id.txt8);
            txt9 = (TextView) itemView.findViewById(R.id.txt9);
            txt10 = (TextView) itemView.findViewById(R.id.txt10);
            txt11 = (TextView) itemView.findViewById(R.id.txt11);
            txt12 = (TextView) itemView.findViewById(R.id.txt12);
            txt13 = (TextView) itemView.findViewById(R.id.txt13);
            txt14 = (TextView) itemView.findViewById(R.id.txt14);
            txt15 = (TextView) itemView.findViewById(R.id.txt15);
            txt16 = (TextView) itemView.findViewById(R.id.txt16);
            txt17 = (TextView) itemView.findViewById(R.id.txt17);
            txt18 = (TextView) itemView.findViewById(R.id.txt18);
            txt19 = (TextView) itemView.findViewById(R.id.txt19);
            txt20 = (TextView) itemView.findViewById(R.id.txt20);
            txt21 = (TextView) itemView.findViewById(R.id.txt21);
            txt22 = (TextView) itemView.findViewById(R.id.txt22);
            txt23 = (TextView) itemView.findViewById(R.id.txt23);
            txt24 = (TextView) itemView.findViewById(R.id.txt24);
            txt25 = (TextView) itemView.findViewById(R.id.txt25);
            txt26 = (TextView) itemView.findViewById(R.id.txt26);
            txt27 = (TextView) itemView.findViewById(R.id.txt27);
            dataLayout = itemView.findViewById(R.id.dataLayout);
        }
    }
}


