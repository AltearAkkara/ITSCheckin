package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
        TextView textViewTime = (TextView)view.findViewById(R.id.textView21);
        textViewTime.setText(str3.get(position));
        return view;
    }
}
