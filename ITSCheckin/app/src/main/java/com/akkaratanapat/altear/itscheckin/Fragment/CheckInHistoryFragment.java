package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.CheckInHistoryAdapter;
import com.akkaratanapat.altear.itscheckin.Constants;
import com.akkaratanapat.altear.itscheckin.CustomRequest;
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
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckInHistoryFragment extends Fragment {

    ArrayList<String> locationList = new ArrayList<String>(), timeList = new ArrayList<String>(),typeList = new ArrayList<String>();
    ListView listView;
    CheckInHistoryAdapter arrayAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public CheckInHistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView =  inflater.inflate(R.layout.fragment_check_in_history, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        listView = (ListView) rootView.findViewById(R.id.listView8);
        new LoadCheckin().execute();
        arrayAdapter = new CheckInHistoryAdapter(getContext(), android.R.layout.simple_expandable_list_item_1, locationList,timeList,typeList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Toast.makeText(getContext(), "Select : " + locationList.get(i).toString(), Toast.LENGTH_SHORT);
            }
        });
        return  rootView;
    }

    public void requestCheckinHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/pcheckin/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Constants.API_KEY);
        params.put("u", sp.getString("Username", "testuser"));

        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resReturn, resCode, resType, resRespone;

                try {
                    resReturn = response.getString("return");
                    resCode = response.getString("code");
                    resType = response.getString("type");
                    resRespone = response.getString("response");
                    if (resReturn.equals("true")) {
                        JSONArray checkin_time = response.getJSONArray("data");
                        for(int count =0;count<checkin_time.length();count++){
                            locationList.add(checkin_time.getJSONObject(count).getString("location_name"));
                            typeList.add(checkin_time.getJSONObject(count).getString("checkin_type"));
                            timeList.add(checkin_time.getJSONObject(count).getString("check_time"));
                        }
                        arrayAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), resRespone , Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
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
            requestCheckinHistory();
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

        }
    }
}
