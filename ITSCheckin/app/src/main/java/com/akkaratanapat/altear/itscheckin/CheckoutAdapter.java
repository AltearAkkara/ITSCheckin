package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckoutAdapter extends ArrayAdapter<String> {

    ArrayList<String> str = new ArrayList<String>(),str2 = new ArrayList<String>(),str3 = new ArrayList<String>();
    Context mContext;
    LayoutInflater INFLATER;

    public CheckoutAdapter(Context context, int viewResourceId
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
            view = mInflater.inflate(R.layout.activity_checkout_adapter, parent, false);

        TextView textViewName = (TextView)view.findViewById(R.id.textView6);
        textViewName.setText(str.get(position));
        TextView textViewDesc = (TextView)view.findViewById(R.id.textView7);
        textViewDesc.setText(str2.get(position));
        TextView textViewTime = (TextView)view.findViewById(R.id.textView8);
        textViewTime.setText(str3.get(position));
        return view;
    }
}
