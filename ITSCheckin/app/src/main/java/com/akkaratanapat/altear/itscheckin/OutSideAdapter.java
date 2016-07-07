package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class OutSideAdapter extends ArrayAdapter<String> {

    String[] str,str2,str3,str4;
    int[] image;
    Context mContext;
    LayoutInflater INFLATER;

    public OutSideAdapter(Context context, int viewResourceId
            , String[] objects, String[] objects2, String[] objects3, String[] objects4) {
        super(context, viewResourceId, objects);
        str = objects;
        str2 = objects2;
        str3 = objects3;
        str4 = objects4;
        mContext = context;
        INFLATER = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.activity_out_side_adapter, parent, false);

        TextView textView = (TextView)view.findViewById(R.id.textView9);
        textView.setText(str[position]);

        TextView textView2 = (TextView)view.findViewById(R.id.textView10);
        textView2.setText(str2[position]);

        TextView textView3 = (TextView)view.findViewById(R.id.textView11);
        textView3.setText(str3[position]);

        TextView textView4 = (TextView)view.findViewById(R.id.textView12);
        textView4.setText(str4[position]);
        //ImageView imageView = (ImageView)view.findViewById(R.id.imageView1);
        //imageView.setBackgroundResource(resId[position]);

        return view;
    }
}
