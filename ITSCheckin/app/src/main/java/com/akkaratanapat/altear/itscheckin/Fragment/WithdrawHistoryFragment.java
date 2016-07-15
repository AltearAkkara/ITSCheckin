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

import com.akkaratanapat.altear.itscheckin.Constants;
import com.akkaratanapat.altear.itscheckin.CustomRequest;
import com.akkaratanapat.altear.itscheckin.R;
import com.akkaratanapat.altear.itscheckin.WithdrawAdapter;
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
public class WithdrawHistoryFragment extends Fragment {

    String[] str1 = {"aaa"},str2 ={"ssss"},str3 = {"8"},str4 ={"aaa"},str5 ={"999"};
    ArrayList<String> titleList = new ArrayList<String>(), dateList = new ArrayList<String>(),codeList = new ArrayList<String>(),proList = new ArrayList<String>(),detailList = new ArrayList<String>(),statusList = new ArrayList<String>(),costList = new ArrayList<String>();
    WithdrawAdapter myAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public WithdrawHistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_withdraw_history, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        new LoadWithdrawHistory().execute();
        myAdapter = new WithdrawAdapter(getActivity(),android.R.layout.simple_list_item_1,proList,codeList,titleList,detailList,statusList,costList,dateList);
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

    public void requestWithdrawHistory() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/hwithdraw/";
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
                        Log.i("outside","I'm coming");
                        JSONArray checkin_time = response.getJSONArray("data");
                        for(int count =0;count<checkin_time.length();count++){
                            proList.add(checkin_time.getJSONObject(count).getString("pname"));
                            codeList.add(checkin_time.getJSONObject(count).getString("pcode"));
                            titleList.add(checkin_time.getJSONObject(count).getString("title"));
                            detailList.add(checkin_time.getJSONObject(count).getString("detail"));
                            statusList.add(checkin_time.getJSONObject(count).getString("status"));
                            dateList.add(checkin_time.getJSONObject(count).getString("withdraw_date"));
                            costList.add(checkin_time.getJSONObject(count).getString("cost"));
                            Log.i("outside",count+"");
                        }
                        myAdapter.notifyDataSetChanged();
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

    private class LoadWithdrawHistory extends AsyncTask<Void, Integer, Void> {
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
            requestWithdrawHistory();
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
