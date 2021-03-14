package com.mean.punchbuddy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class StrikeListAdapter extends BaseAdapter {

    Activity myActivity;
    ArrayList<String> list;
    ViewHolder holder;
    int tempPosition;

    public StrikeListAdapter(Activity myActivity, ArrayList<String> list) {
        this.myActivity = myActivity;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        tempPosition = position;

        if (convertView == null) {

            holder = new ViewHolder();

            LayoutInflater vi = (LayoutInflater)
                    myActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.activity_strike_list_line, null);

            holder.strikeName = (EditText) convertView.findViewById(R.id.entry_name);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }
        //binding data from array list
        holder.strikeName.setText(list.get(position));
        holder.strikeName.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //setting data to array, when changed
                //list.get(tempPosition) = s.toString();

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return convertView;
    }


        /*View thisRow;
        LayoutInflater inflater =
                (LayoutInflater) myActivity.getSystemService((Context.LAYOUT_INFLATER_SERVICE));
        thisRow = inflater.inflate(R.layout.activity_strike_list_line, parent, false);

        TextView tv = (TextView) thisRow.findViewById(R.id.delete_line);
        tv.setTag(position);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = (int) v.getTag();
                list.remove(pos);
                StrikeListAdapter.this.notifyDataSetChanged();
            }
        });*/
        //return thisRow;
    }

    class ViewHolder {
        EditText strikeName;
        TextView deleteButton;
        int ref;
    }


