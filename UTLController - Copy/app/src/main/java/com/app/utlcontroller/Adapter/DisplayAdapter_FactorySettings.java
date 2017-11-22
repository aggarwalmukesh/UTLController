package com.app.utlcontroller.Adapter;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.app.utlcontroller.Controller.PositionCallback;
import com.app.utlcontroller.Model.Model_Step_Checkbox;
import com.app.utlcontroller.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Mints on 5/1/2017.
 */

public class DisplayAdapter_FactorySettings extends BaseAdapter {
    Context context;
    ArrayList<Model_Step_Checkbox> listData;
    PositionCallback callback;
    int selectedIndex = -1;
    int clickedposition=-1;

    public DisplayAdapter_FactorySettings(Context context, ArrayList<Model_Step_Checkbox> listData) {
        this.context = context;
        this.listData = listData;
    }

    public DisplayAdapter_FactorySettings(Context context, ArrayList<Model_Step_Checkbox> listData, PositionCallback callback) {
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
        //if (convertView == null) {
        convertView = LayoutInflater.from(context).inflate(R.layout.row_factory_settings, null);
        holder = new MyViewHolder();
        convertView.setTag(holder);
        //} /*else {
        //  holder = (MyViewHolder) convertView.getTag();
        //}*/
        final Model_Step_Checkbox data = listData.get(position);
        holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
        holder.txtUnits = (TextView) convertView.findViewById(R.id.txtUnits);
        holder.txtReadings = (TextView) convertView.findViewById(R.id.txtReading);
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
        holder.editValue.setId(position);
        holder.txtTitle.setText(data.name);
        if (position == selectedIndex) {
            convertView.setBackgroundResource(R.drawable.drawable_row_selected_gradient);
            holder.increamentLayout.setVisibility(View.VISIBLE);
        } else {
            convertView.setBackgroundResource(0);
            holder.increamentLayout.setVisibility(View.GONE);
        }

        if(data.viewType==4){
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.GONE);
            holder.txtReadings.setVisibility(View.GONE);
            holder.txtTitle.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.VISIBLE);
            holder.txtTitle.setText(data.name);
            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int indexposition, long id) {
                    callback.clickPosition(position,indexposition);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(context,R.layout.layout_dropdown,data.values);
            holder.spinner.setAdapter(adapter);

            Handler handler=new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    holder.spinner.setSelection((int)listData.get(position).value);
                }
            },200);
        }

        else if (data.viewType == 1) {
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.GONE);
            holder.txtUnits.setText(data.unit);
            holder.editValue.setText(data.type1Value);

            if(data.isEditable){
                holder.txtReadings.setVisibility(View.GONE);
                if(data.keyBoardType.contains("DECIMAL")){
                    holder.editValue.setInputType(InputType.TYPE_CLASS_NUMBER|InputType.TYPE_NUMBER_FLAG_DECIMAL);
                }
            }else{
                holder.txtReadings.setVisibility(View.VISIBLE);
                holder.txtReadings.setText(data.type1Value);
                holder.editValue.setVisibility(View.GONE);
            }

            // holder.editValue.setId(position);

            //we need to update adapter once we finish with editing

            holder.editValue.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    Log.v("ClickedPosition="+position," Value= "+s.toString());
                    listData.get(position).type1Value = holder.editValue.getText().toString();
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            holder.editValue.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                public void onFocusChange(View v, boolean hasFocus) {
                    Log.v("Has focus",position+"  a");
                    if (!hasFocus){
                        final int position = v.getId();
                        final EditText Caption = (EditText) v;
                        listData.get(position).type1Value = Caption.getText().toString();
                    }
                }
            });

            holder.editValue.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    clickedposition=position;
                    return false;
                }
            });
            holder.txtReadings.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    callback.clickPosition(position);
                }
            });
        } else if (data.viewType == 2) {
            holder.txtReadings.setVisibility(View.VISIBLE);
            holder.spinner.setVisibility(View.GONE);
            holder.checkBoxLayout.setVisibility(View.GONE);
            holder.editValue.setVisibility(View.GONE);

            if(data.stepSize>=1) {
                holder.txtReadings.setText((int)data.value + data.unit);
            }else{
                holder.txtReadings.setText(data.value + data.unit);
            }
            holder.incValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Increment=", "STep=" + data.stepSize + "   Value= " + data.value + "   Max=  " + data.maxValue);
                    if ((data.stepSize + data.value) <= data.maxValue) {
                        BigDecimal roundfinalPrice = new BigDecimal(data.value + data.stepSize).setScale(data.decimalPlaces, BigDecimal.ROUND_UP);
                        data.value = roundfinalPrice.floatValue();
                        Log.v("roundfinalPrice Up= ", roundfinalPrice + "   value  " + data.value);
                        if(data.stepSize>=1) {
                            holder.txtReadings.setText((int)data.value + data.unit);
                        }else{
                            holder.txtReadings.setText(data.value + data.unit);
                        }
                    }
                }
            });

            holder.decValue.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.v("Decrement=", "STep=" + data.stepSize + "   Value= " + data.value + "   Min=  " + data.minValue);

                    if ((data.value - data.stepSize) >= data.minValue) {
                        BigDecimal roundfinalPrice = new BigDecimal(data.value - data.stepSize).setScale(data.decimalPlaces, BigDecimal.ROUND_DOWN);
                        data.value = roundfinalPrice.floatValue();
                        Log.v("roundfinalPrice Down = ", roundfinalPrice + "   value  " + data.value);
                        if(data.stepSize>=1) {
                            holder.txtReadings.setText((int)data.value + data.unit);
                        }else{
                            holder.txtReadings.setText(data.value + data.unit);
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

            if(data.isEditable){
                holder.radioFirst.setEnabled(true);
                holder.radioSecond.setEnabled(true);
            }else{
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
        TextView txtTitle, txtReadings,txtUnits;
        ImageView incValue, decValue;
        View mainLayout, checkBoxLayout,increamentLayout;
        Spinner spinner;
        RadioButton radioFirst, radioSecond;
        RadioGroup radioGroup;
        EditText editValue;

    }

    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Log.v("ClickedPosition="+clickedposition," Value= "+s.toString());
            if(clickedposition>-1)
                listData.get(clickedposition).type1Value=s.toString();
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
}
