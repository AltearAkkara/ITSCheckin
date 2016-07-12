package com.akkaratanapat.altear.itscheckin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.akkaratanapat.altear.itscheckin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInHistoryFragment extends Fragment {


    public CheckInHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_check_in_history, container, false);
    }

}
