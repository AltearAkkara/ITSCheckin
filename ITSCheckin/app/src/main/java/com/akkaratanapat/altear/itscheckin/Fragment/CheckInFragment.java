package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.Constants;
import com.akkaratanapat.altear.itscheckin.CustomRequest;
import com.akkaratanapat.altear.itscheckin.MainActivity;
import com.akkaratanapat.altear.itscheckin.R;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInFragment extends Fragment {

    MainActivity activity;
    Button checkInButton, checkInHistoryButton;
    TextView userText, dateText, checkinText, dateCheckinText;
    ListView listView;
    Button checkinTYPE;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    int position = 0;
    ArrayList<String> location = new ArrayList<String>(), idLocation = new ArrayList<String>();

    int ok2001 = 2,ok2002 = 2;
    ArrayAdapter<String> arrayAdapter;
    String mode = "location";

    public CheckInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_check_in, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        checkInButton = (Button) rootView.findViewById(R.id.button5);
        checkInHistoryButton = (Button) rootView.findViewById(R.id.button6);
        userText = (TextView) rootView.findViewById(R.id.textView4);
        dateText = (TextView) rootView.findViewById(R.id.textView5);
        checkinText = (TextView) rootView.findViewById(R.id.textView2);
        dateCheckinText = (TextView) rootView.findViewById(R.id.textView3);
        listView = (ListView) rootView.findViewById(R.id.listView);
        checkinTYPE = (Button)rootView.findViewById(R.id.button7);
        userText.setText(sp.getString("Username", "testuser"));
        checkinText.setText(sp.getString("checkInLocation", "---"));
        dateCheckinText.setText(sp.getString("dateCheckIn", "--/--/--"));
        checkInButton.setText(sp.getString("isCheckIn", "CHECK IN"));
        if(sp.getString("checkinType","-").equals("0")){
            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_0));
        }
        else if(sp.getString("checkinType","-").equals("1")){
            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_1));
        }
        else if(sp.getString("checkinType","-").equals("2")){
            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_2));
        }
        else if(sp.getString("checkinType","-").equals("3")){
            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_3));
        }

        if (location.isEmpty()) {
            mode = "location";
            new LoadCheckin().execute();
        }

        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, location);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Toast.makeText(getContext(), "Select : " + location.get(position).toString(), Toast.LENGTH_SHORT);
                Log.i("kkkk",position+"");
            }
        });

        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (checkInButton.getText().toString().equals("CHECK OUT")) {
                    activity.changeFragmentByFragment("checkout");
                } else {
                    LayoutInflater li = LayoutInflater.from(getContext());
                    View dialogView = li.inflate(R.layout.checkin_dialog, null);
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            getContext());
                    alertDialogBuilder.setTitle("Confirmation");
                    alertDialogBuilder.setMessage("Do you want to check in?");
                    alertDialogBuilder.setView(dialogView);
                    final EditText userInput = (EditText) dialogView.findViewById(R.id.editText7);
                    if (!idLocation.isEmpty()) {
                        if (idLocation.get(position).equals("999"))
                            userInput.setHint("Check In Detail(Require)");
                        else userInput.setHint("Check In Detail(Optional)");
                        alertDialogBuilder
                                .setCancelable(true)
                                .setPositiveButton("OK",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                //must be define in preference
                                                if (idLocation.get(position).equals("999") && userInput.getText().length() == 0) {
                                                    Toast.makeText(getContext(), "Require detail.", Toast.LENGTH_LONG).show();
                                                } else {
                                                    mode = "checkin";
                                                    new LoadCheckin().execute();
                                                }
                                            }
                                        })
                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog,
                                                                int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                    }
                }
            }
        });

        checkInHistoryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("checkin_history");
            }
        });
        //interval date 1sec
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            private long time = 0;

            @Override
            public void run() {
                // do stuff then
                // can call h again after work!
                try {
                    dateText.setText(new Date().toString());
                } finally {

                }
                h.postDelayed(this, 500);
            }
        }, 1000);

        return rootView;
    }

    public void requestLcation() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/location/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Constants.API_KEY);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resReturn, resCode, resType, resRespone;

                try {
                    resReturn = response.getString("return");
                    resCode = response.getString("code");
                    resType = response.getString("type");
                    resRespone = response.getString("response");
                    Log.i("location", "ok1");
                    if (resReturn.equals("true")) {
                        JSONArray resData = response.getJSONArray("data");
                        for (int i = 0; i < resData.length(); i++) {
                            location.add(resData.getJSONObject(i).getString("name"));
                            idLocation.add(resData.getJSONObject(i).getString("id"));
                            Log.i("location", "ok" + i);
                        }
                        ok2001 = 1;
                        arrayAdapter.notifyDataSetChanged();
                        Log.i("location", "ok");
                    } else {
                        Toast.makeText(getContext(), resRespone, Toast.LENGTH_LONG).show();
                        Log.i("location", " no ok");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                ok2001 = 0;
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    public void requestCheckin() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/Checkin/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Constants.API_KEY);
        params.put("u", sp.getString("Username", "testuser"));
        params.put("locationid", idLocation.get(position));
        params.put("lat", sp.getString("lat","13.766"));
        params.put("lng", sp.getString("lat","100.605"));
        params.put("uniqueid", sp.getString("IMEI", "123456789012345"));
        params.put("mode", "dev");

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resReturn, resCode, resType, resRespone;

                try {
                    Log.i("location", "ok1");
                    resReturn = response.getString("return");
                    resCode = response.getString("code");
                    resType = response.getString("type");
                    resRespone = response.getString("response");
                    if (resReturn.equals("true")) {
                        String checkin_time = response.getString("checkin_time");
                        String checkin_type = response.getString("checkin_type");
                        ok2002 = 1;
                        editor.putString("checkInLocation", location.get(position));
                        editor.putString("checkInLocationID", idLocation.get(position));
                        editor.putString("dateCheckIn",checkin_time);
                        editor.putString("checkinType",checkin_type);
                        editor.putString("isCheckIn", "CHECK OUT");
                        editor.commit();
                        checkinText.setText(sp.getString("checkInLocation", "---"));
                        dateCheckinText.setText(sp.getString("dateCheckIn", "--/--/--"));
                        checkInButton.setText(sp.getString("isCheckIn", "CHECK OUT"));

                        if(sp.getString("checkinType","-").equals("0")){
                            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_0));
                        }
                        else if(sp.getString("checkinType","-").equals("1")){
                            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_1));
                        }
                        else if(sp.getString("checkinType","-").equals("2")){
                            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_2));
                        }
                        else if(sp.getString("checkinType","-").equals("3")){
                            checkinTYPE.setBackground(ContextCompat.getDrawable(getActivity(), R.drawable.checkin_type_3));
                        }

                        Log.i("location", "ok2");
                    } else {
                        Toast.makeText(getContext(), resRespone , Toast.LENGTH_LONG).show();
                        ok2002 = 0;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                ok2002 = 0;
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }


    private class LoadCheckin extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pd;

        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
//            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Loading ...");
            pd.setMessage("just a second ...");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
//            pd.setMax(100);
//            pd.setProgress(0);
            pd.show();

        }

        protected Void doInBackground(Void... params) {
            //publishProgress(20);
            if (mode.equals("checkin")) {
                //publishProgress(60);
                requestCheckin();
                //publishProgress(100);

            } else {
                //publishProgress(80);
                requestLcation();
                //publishProgress(100);
            }
            try
            {
                synchronized (this)
                {
                    for(int i = 0 ; i < 10000 ; i++) {

                        int c = (int)((i/ 100) * (i + 1));
                        publishProgress(c);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
            //setContentView(activity);
            if (mode.equals("location")) {
                if (ok2001 == 1) {
//                    arrayAdapter.notifyDataSetChanged();
                } else if (ok2001 == 0){
                    Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (ok2002 == 1) {
//                    editor.putString("checkInLocation", location.get(position));
//                    editor.putString("checkInLocationID", idLocation.get(position));
//                    editor.putString("dateCheckIn", new Date().toString());
//                    editor.putString("isCheckIn", "CHECK OUT");
//                    editor.commit();
//                    checkinText.setText(sp.getString("checkInLocation", "---"));
//                    dateCheckinText.setText(sp.getString("dateCheckIn", "--/--/--"));
//                    checkInButton.setText(sp.getString("isCheckIn", "CHECK OUT"));
                } else if (ok2002 == 0){
                    Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    //Toast.makeText(getContext(), "Try again", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
