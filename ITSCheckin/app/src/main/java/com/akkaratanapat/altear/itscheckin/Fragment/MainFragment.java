package com.akkaratanapat.altear.itscheckin.Fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akkaratanapat.altear.itscheckin.MainActivity;
import com.akkaratanapat.altear.itscheckin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment {

    MainActivity activity;
    Button checkinBtn,outsideBtn,withdrawBtn,logoutBtn;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_main, container, false);
        ((AppCompatActivity)getActivity()).setTitle("ITS Check In");
        checkinBtn = (Button)rootView.findViewById(R.id.button);
        checkinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("checkin");
            }
        });
        outsideBtn = (Button)rootView.findViewById(R.id.button2);
        outsideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("outside");
            }
        });
        withdrawBtn = (Button)rootView.findViewById(R.id.button3);
        withdrawBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("withdraw");
            }
        });
        logoutBtn = (Button)rootView.findViewById(R.id.button4);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("logout");
            }
        });
        return rootView;
    }

}
