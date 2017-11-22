package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.utlcontroller.Controller.PositionCallback;
import com.app.utlcontroller.Model.ModelData;
import com.app.utlcontroller.Model.ModelData_Step;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.R;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 * Created by Mints on 5/1/2017.
 */

public class DisplayAdapter_Step extends BaseAdapter {
    Context context;
    ArrayList<Model_Step_Checkbox> listData;
    PositionCallback callback;
    int selectedIndex = -1;

    public DisplayAdapter_Step(Context context, ArrayList<Model_Step_Checkbox> listData) {
        this.context = context;
        this.listData = listData;
    }

    public DisplayAdapter_Step(Context context, ArrayList<Model_Step_Checkbox> listData, PositionCallback callback) {
        this.context = context;
        this.listData = listData;
        this.callback = callback;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.row_withstepsize, null);
            holder = new MyViewHolder();
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        final Model_Step_Checkbox data = listData.get(position);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.txtReadings = (TextView) convertView.findViewById(R.id.txtReading);
        holder.btnSubmit = (Button) convertView.findViewById(R.id.btnSubmit);
        holder.spinner = (Spinner) convertView.findViewById(R.id.spinner);
        holder.incValue = (ImageView) convertView.findViewById(R.id.incValue);
        holder.decValue = (ImageView) convertView.findViewById(R.id.decValue);
        holder.mainLayout = convertView.findViewById(R.id.mainLayout);
        holder.radioFirst = (RadioButton) convertView.findViewById(R.id.radioFirst);
        holder.radioSecond = (RadioButton) convertView.findViewById(R.id.radioSecond);
        holder.editValue = (EditText) convertView.findViewById(R.id.editValue);
        holder.checkBoxLayout = convertView.findViewById(R.id.checkBoxLayout);
        holder.increamentLayout = convertView.findViewById(R.id.increamentLayout);
        holder.radioFirst.setText(data.firstOption);
        holder.radioSecond.setText(data.secondOption);

        holder.txtTitle.setText(data.name);
        if (position == selectedIndex) {
            convertView.setBackgroundResource(R.drawable.drawable_row_selected_gradient);
            holder.increamentLayout.setVisibility(View.VISIBLE);
        } else {
            convertView.setBackgroundResource(0);
            holder.increamentLayout.setVisibility(View.GONE);
        }
        holder.btnSubmit.setVisibility(View.GONE);
        holder.txtTitle.setVisibility(View.VISIBLE);

        if (data.viewType == 4) {
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.GONE);
            holder.txtReadings.setVisibility(View.GONE);
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.btnSubmit.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.txtTitle.setText(data.name);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, R.layout.layout_dropdown, data.values);
            holder.spinner.setAdapter(adapter);
        }else if(data.viewType==5){
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.GONE);
            holder.txtReadings.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.txtTitle.setVisibility(View.GONE);
            holder.btnSubmit.setText(data.name);
            holder.btnSubmit.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.GONE);
            holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clickPosition(position);
                }
            });
        } else if (data.viewType == 1) {
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.GONE);
            holder.btnSubmit.setVisibility(View.GONE);

            if (data.isEditable) {
                holder.txtReadings.setVisibility(View.GONE);
                holder.editValue.setText(data.type1Value);
                if (data.keyBoardType.contains("DECIMAL")) {
                    holder.editValue.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
            } else {
                holder.txtReadings.setVisibility(View.VISIBLE);
                holder.txtReadings.setText(data.type1Value);
                holder.editValue.setVisibility(View.GONE);
                holder.txtReadings.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        callback.clickPosition(position);
                    }
                });
            }
        } else if (data.viewType == 2) {
            holder.txtReadings.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.GONE);
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.GONE);
            holder.btnSubmit.setVisibility(View.GONE);

            holder.txtReadings.setText(NumberFormat.getInstance().format(data.value) + data.unit);
            holder.incValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Increment=", "STep=" + data.stepSize + "   Value= " + data.value + "   Max=  " + data.maxValue);
                    if ((data.stepSize + data.value) <= data.maxValue) {
                        BigDecimal roundfinalPrice = new BigDecimal(data.value + data.stepSize).setScale(data.decimalPlaces, BigDecimal.ROUND_UP);
                        data.value = roundfinalPrice.floatValue();
                        Log.v("roundfinalPrice Up= ", roundfinalPrice + "   value  " + data.value);
                        holder.txtReadings.setText(NumberFormat.getInstance().format(data.value) + data.unit);
                    }
                }
            });

            holder.decValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Decrement=", "STep=" + data.stepSize + "   Value= " + data.value + "   Min=  " + data.minValue);

                    if ((data.value - data.stepSize) >= data.minValue) {
                        BigDecimal roundfinalPrice = new BigDecimal(data.value - data.stepSize).setScale(data.decimalPlaces, BigDecimal.ROUND_DOWN);
                        Log.v("roundfinalPrice Down = ", roundfinalPrice + "   value  " + data.value);
                        data.value = roundfinalPrice.floatValue();
                        if(data.name.equalsIgnoreCase("Display Timeout") && NumberFormat.getInstance().format(data.value).equalsIgnoreCase("0")){
                            holder.txtReadings.setText("OFF");
                        }else {
                            holder.txtReadings.setText(NumberFormat.getInstance().format(data.value) + data.unit);
                        }
                    }
                }
            });

            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectedIndex = position;
                    callback.clickPosition(position);
                    notifyDataSetChanged();
                }
            });
        } else if (data.viewType == 3) {
            holder.txtReadings.setVisibility(View.GONE);
            holder.spinner.setVisibility(View.GONE);
            holder.checkBoxLayout.setVisibility(View.VISIBLE);
            holder.editValue.setVisibility(View.GONE);

            holder.radioGroup = (RadioGroup) convertView.findViewById(R.id.radioGroup);
            holder.txtTitle.setText(data.name);

            Log.v("OnScroll ", data.name + "      " + data.status);
            if (data.status) {
                ((RadioButton) holder.radioGroup.getChildAt(0)).setChecked(true);
                ((RadioButton) holder.radioGroup.getChildAt(1)).setChecked(false);
            } else {
                ((RadioButton) holder.radioGroup.getChildAt(1)).setChecked(true);
                ((RadioButton) holder.radioGroup.getChildAt(0)).setChecked(false);
            }

            if (data.isEditable) {
                holder.radioFirst.setEnabled(true);
                holder.radioSecond.setEnabled(true);
            } else {
                holder.radioFirst.setEnabled(false);
                holder.radioSecond.setEnabled(false);
            }
            holder.radioFirst.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("check change ", data.name + "      " + data.status + " isFirstChecked  " + holder.radioFirst.isChecked());
                    data.status = true;
                    callback.clickPosition(position);
                }
            });

            holder.radioSecond.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("check change ", data.name + "      " + data.status + " isSecondChecked  " + holder.radioSecond.isChecked());
                    data.status = false;
                    callback.clickPosition(position);
                }
            });
        }

        return convertView;
    }

    class MyViewHolder {
        TextView txtTitle, txtReadings;
        ImageView incValue, decValue;
        View mainLayout, checkBoxLayout, increamentLayout;
        Spinner spinner;
        RadioButton radioFirst, radioSecond;
        RadioGroup radioGroup;
        EditText editValue;
        Button btnSubmit;
    }
}
