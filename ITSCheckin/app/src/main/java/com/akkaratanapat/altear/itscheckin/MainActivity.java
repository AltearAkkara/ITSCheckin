package com.akkaratanapat.altear.itscheckin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.akkaratanapat.altear.itscheckin.Fragment.CheckInFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.CheckInHistoryFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.CheckOutFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.LoginFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.MainFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.OutsideFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.OutsideHistoryFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.WithdrawFragment;
import com.akkaratanapat.altear.itscheckin.Fragment.WithdrawHistoryFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    Toolbar toolbar;
    AlertDialog.Builder aBuilder;
    AlertDialog alertDialog;
    private GoogleApiClient googleApiClient;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        sp = getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .build();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.container01);
        LoginFragment myFragment = (LoginFragment) fragment;


//        MainFragment myFragment = new MainFragment();
//        FragmentManager manager = getSupportFragmentManager();
//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.container01, myFragment);
//        transaction.addToBackStack(null);
//        transaction.commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Toast.makeText(getBaseContext(),"main",Toast.LENGTH_SHORT).show();
    }

    public void changeFragmentByFragment(String fragmentName) {
        if (fragmentName.equals("checkin")) {
            CheckInFragment myFragment = new CheckInFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("checkin_history")) {
            CheckInHistoryFragment myFragment = new CheckInHistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("outside")) {
            OutsideFragment myFragment = new OutsideFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("withdraw")) {
            WithdrawFragment myFragment = new WithdrawFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("logout")) {
            //Dialog
            aBuilder = new AlertDialog.Builder(MainActivity.this);
            aBuilder.setTitle("Log out").setMessage("Do you want to log out?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    LoginFragment myFragment = new LoginFragment();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
                    transaction.replace(R.id.container01, myFragment);
                    transaction.addToBackStack(null);
                    transaction.commit();
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            });
            alertDialog = aBuilder.create();
            alertDialog.show();
        } else if (fragmentName.equals("checkout")) {
            CheckOutFragment myFragment = new CheckOutFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("history_outside")) {
            OutsideHistoryFragment myFragment = new OutsideHistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("history_withdraw")) {
            WithdrawHistoryFragment myFragment = new WithdrawHistoryFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        } else if (fragmentName.equals("main")) {
            MainFragment myFragment = new MainFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.setCustomAnimations(R.anim.push_right_in, R.anim.push_left_out,R.anim.push_left_in, R.anim.push_right_out);
            transaction.replace(R.id.container01, myFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

//    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
//        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
//        switch (requestCode) {
//            case 0:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    //imageview.setImageURI(selectedImage);
//                }
//
//                break;
//            case 1:
//                if (resultCode == RESULT_OK) {
//                    Uri selectedImage = imageReturnedIntent.getData();
//                    //imageview.setImageURI(selectedImage);
//                }
//                break;
//        }
//    }

    @Override
    public void onStart() {
        super.onStart();
        googleApiClient.connect();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected()) {
            // Disconnect Google API Client if available and connected
            googleApiClient.disconnect();
        }
    }

    @Override
    public void onConnected(Bundle bundle) {
        LocationAvailability locationAvailability = LocationServices.FusedLocationApi.getLocationAvailability(googleApiClient);
        if (locationAvailability.isLocationAvailable()) {
            // Call Location Services
            LocationRequest locationRequest = new LocationRequest()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setInterval(500);
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        } else {
            // Do something when Location Provider not available
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            editor.putString("lat", "" + location.getLatitude());
            editor.putString("lng", "" + location.getLongitude());
            editor.commit();
            Log.i("location", location.getLatitude() + " : " + location.getLongitude());
        }

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
