package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckInHistoryAdapter extends ArrayAdapter<String> {

    ArrayList<String> str,str2,str3;
    Context mContext;
    LayoutInflater INFLATER;

    public CheckInHistoryAdapter(Context context, int viewResourceId
            , ArrayList<String> objects1,  ArrayList<String> obStrings2,  ArrayList<String> obStrings3) {
        super(context, viewResourceId, objects1);
        str = objects1;
        str2 = obStrings2;
        str3 = obStrings3;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_check_in_history_adapter, parent, false);

        TextView textViewName = (TextView)view.findViewById(R.id.textView19);
        textViewName.setText(str.get(position));
        TextView textViewDesc = (TextView)view.findViewById(R.id.textView20);
        textViewDesc.setText(str2.get(position));
        Button btn = (Button)view.findViewById(R.id.button19);

        if(str3.get(position).equals("0")){
            btn.setVisibility(View.VISIBLE);
            btn.setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.checkin_type_0));
        }
        else if(str3.get(position).equals("1")){
            btn.setVisibility(View.VISIBLE);
            btn.setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.checkin_type_1));
        }
        else if(str3.get(position).equals("2")){
            btn.setVisibility(View.VISIBLE);
            btn.setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.checkin_type_2));
        }
        else if(str3.get(position).equals("3")){
            btn.setVisibility(View.VISIBLE);
            btn.setBackground(ContextCompat.getDrawable(mInflater.getContext(), R.drawable.checkin_type_3));
        }
        return view;
    }
}
