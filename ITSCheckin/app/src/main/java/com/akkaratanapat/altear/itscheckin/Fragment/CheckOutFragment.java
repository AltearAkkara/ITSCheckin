package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.CheckoutAdapter;
import com.akkaratanapat.altear.itscheckin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOutFragment extends Fragment {

    String[] str1 = {"aaa"},str2 ={"ssss"},str3 = {"8"};

    public CheckOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_check_out, container, false);

        CheckoutAdapter myAdapter = new CheckoutAdapter(getActivity(),android.R.layout.simple_list_item_1,str1,str2,str3);
        ListView myListView = (ListView)rootView.findViewById(R.id.listView2);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(getActivity(), str1[position], Toast.LENGTH_SHORT).show();
            }
        });

        Button checkout = (Button)rootView.findViewById(R.id.button8);

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder aBuilder = new AlertDialog.Builder(getActivity());
                aBuilder.setTitle("Confirmation.").setMessage("Do you want to check out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getFragmentManager().popBackStack();
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //do nothing

                    }
                });
                AlertDialog alertDialog = aBuilder.create();
                alertDialog.show();
            }
        });

        Button addTimeSheet = (Button)rootView.findViewById(R.id.button9);

        addTimeSheet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkout_dialog);
                dialog.setCancelable(true);
                Button addButton = (Button)dialog.findViewById(R.id.button12);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Add",Toast.LENGTH_SHORT).show();
                    }
                });
                Button cancelButton = (Button)dialog.findViewById(R.id.button13);
                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        return rootView;
    }

}
