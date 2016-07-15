package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class OutSideAdapter extends ArrayAdapter<String> {

    ArrayList<String> locationList, timeList,codeList,proList,desList;

    Context mContext;
    LayoutInflater INFLATER;

    public OutSideAdapter(Context context, int viewResourceId
            , ArrayList<String> objects, ArrayList<String> objects2, ArrayList<String> objects3, ArrayList<String> objects4,ArrayList<String> objects5) {
        super(context, viewResourceId, objects);
        proList = objects;
        codeList = objects2;
        locationList = objects3;
        desList = objects4;
        timeList = objects5;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_out_side_adapter, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView9);
        textView.setText(proList.get(position).toString()+"("+codeList.get(position).toString()+")");

        TextView textView2 = (TextView)view.findViewById(R.id.textView10);
        textView2.setText(locationList.get(position));

        TextView textView3 = (TextView)view.findViewById(R.id.textView11);
        textView3.setText(desList.get(position));

        TextView textView4 = (TextView)view.findViewById(R.id.textView12);
        textView4.setText(timeList.get(position));
        //ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        //imageView.setBackgroundResource(resId[position]);

        return view;
    }
}
