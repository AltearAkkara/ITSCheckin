package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WithdrawAdapter extends ArrayAdapter<String> {

    String[] str,str2,str3,str4,str5;
    int status;
    Context mContext;
    LayoutInflater INFLATER;

    public WithdrawAdapter(Context context, int viewResourceId
            , String[] objects1,String[] obStrings2,String[] obStrings3,String[] obStrings4,String[] obStrings5,int status) {
        super(context, viewResourceId, objects1);
        str = objects1;
        str2 = obStrings2;
        str3 = obStrings3;
        str4 = obStrings4;
        str5 = obStrings5;
        this.status = status;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_withdraw_adapter, parent, false);

        TextView textViewName = (TextView)view.findViewById(R.id.textView14);
        textViewName.setText(str[position]);
        TextView textViewTitle = (TextView)view.findViewById(R.id.textView15);
        textViewTitle.setText(str2[position]);
        TextView textViewDetail = (TextView)view.findViewById(R.id.textView16);
        textViewDetail.setText(str3[position]);
        TextView textViewCost = (TextView)view.findViewById(R.id.textView17);
        textViewCost.setText(str4[position]);
        TextView textViewDate = (TextView)view.findViewById(R.id.textView18);
        textViewDate.setText(str5[position]);

        return view;
    }
}
