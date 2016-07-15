package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class WithdrawAdapter extends ArrayAdapter<String> {

    ArrayList<String> titleList = new ArrayList<String>(), dateList = new ArrayList<String>(),codeList = new ArrayList<String>(),proList = new ArrayList<String>()
            ,detailList = new ArrayList<String>(),statusList = new ArrayList<String>(),costList = new ArrayList<String>();
    int status;
    Context mContext;
    LayoutInflater INFLATER;

    public WithdrawAdapter(Context context, int viewResourceId
            , ArrayList<String> objects1, ArrayList<String> obStrings2, ArrayList<String> obStrings3, ArrayList<String> obStrings4
            , ArrayList<String> obStrings5, ArrayList<String> obStrings6, ArrayList<String> obStrings7) {
        super(context, viewResourceId, objects1);
        proList = objects1;
        codeList = obStrings2;
        titleList = obStrings3;
        detailList = obStrings4;
        statusList = obStrings5;
        costList = obStrings6;
        dateList = obStrings7;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_withdraw_adapter, parent, false);

        TextView textViewName = (TextView)view.findViewById(R.id.textView14);
        textViewName.setText(proList.get(position)+"(" + codeList.get(position) +")");
        TextView textViewTitle = (TextView)view.findViewById(R.id.textView15);
        textViewTitle.setText(titleList.get(position));
        TextView textViewDetail = (TextView)view.findViewById(R.id.textView16);
        textViewDetail.setText(detailList.get(position));
        TextView textViewCost = (TextView)view.findViewById(R.id.textView17);
        textViewCost.setText(dateList.get(position));
        TextView textViewDate = (TextView)view.findViewById(R.id.textView18);
        textViewDate.setText(costList.get(position));
        Button buttonstatus = (Button)view.findViewById(R.id.button16);
        buttonstatus.setText(statusList.get(position));

        return view;
    }
}
