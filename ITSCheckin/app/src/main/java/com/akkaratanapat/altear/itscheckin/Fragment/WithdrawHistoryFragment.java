package com.akkaratanapat.altear.itscheckin.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.R;
import com.akkaratanapat.altear.itscheckin.WithdrawAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class WithdrawHistoryFragment extends Fragment {

    String[] str1 = {"aaa"},str2 ={"ssss"},str3 = {"8"},str4 ={"aaa"},str5 ={"999"};

    public WithdrawHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_withdraw_history, container, false);

        WithdrawAdapter myAdapter = new WithdrawAdapter(getActivity(),android.R.layout.simple_list_item_1,str1,str2,str3,str4,str5,4);
        ListView myListView = (ListView)rootView.findViewById(R.id.listView7);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), str1[position], Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }

}
