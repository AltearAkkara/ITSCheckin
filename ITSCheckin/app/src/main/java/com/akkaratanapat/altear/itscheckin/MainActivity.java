package com.akkaratanapat.altear.itscheckin;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.akkaratanapat.altear.itscheckin.Fragment.CheckInFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.CheckOutFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.MainFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.OutsideFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.OutsideHistoryFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.WithdrawFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.WithdrawHistoryFragment;

public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    AlertDialog.Builder aBuilder;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);

        MainFragment myFragment = new MainFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container01, myFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void changeFragmentByFragment(String fragmentName){
        if(fragmentName.equals("checkin")){
            CheckInFragment myFragment = new CheckInFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(fragmentName.equals("outside")){
            OutsideFragment myFragment = new OutsideFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(fragmentName.equals("withdraw")){
            WithdrawFragment myFragment = new WithdrawFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(fragmentName.equals("logout")){
            //Dialog
            aBuilder = new AlertDialog.Builder(MainActivity.this);
            aBuilder.setTitle("Log out").setMessage("Do you want to log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            });
            alertDialog = aBuilder.create();
            alertDialog.show();
        }
        else if(fragmentName.equals("checkout")){
            CheckOutFragment myFragment = new CheckOutFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(fragmentName.equals("history_outside")){
            OutsideHistoryFragment myFragment = new OutsideHistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
        else if(fragmentName.equals("history_withdraw")){
            WithdrawHistoryFragment myFragment = new WithdrawHistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageview.setImageURI(selectedImage);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    //imageview.setImageURI(selectedImage);
                }
                break;
        }
    }
}
