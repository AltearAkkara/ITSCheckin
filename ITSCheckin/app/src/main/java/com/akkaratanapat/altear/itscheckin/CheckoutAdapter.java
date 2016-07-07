package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CheckoutAdapter extends ArrayAdapter<String> {

    String[] str,str2,str3;
    Context mContext;
    LayoutInflater INFLATER;

    public CheckoutAdapter(Context context, int viewResourceId
            , String[] objects1,String[] obStrings2,String[] obStrings3) {
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
        textViewName.setText(str[position]);
        TextView textViewDesc = (TextView)view.findViewById(R.id.textView7);
        textViewDesc.setText(str2[position]);
        TextView textViewTime = (TextView)view.findViewById(R.id.textView8);
        textViewTime.setText(str3[position]);
        return view;
    }
}
