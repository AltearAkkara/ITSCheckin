package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.CheckoutAdapter;
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
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckOutFragment extends Fragment {

    String[] str1 = {"aaa"},str2 ={"ssss"},str3 = {"8"};
    ArrayList<String> id  = new ArrayList<String>(),code  = new ArrayList<String>(),name  = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    Boolean ok200 = true;
    MainActivity activity;
    int position= 99;
    public CheckOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_check_out, container, false);

        new LoadCheckOut().execute();
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, name);
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
                ListView listView = (ListView)dialog.findViewById(R.id.listView4);
                listView.setAdapter(arrayAdapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        position = i;
                    }
                });
                dialog.show();
            }
        });
        return rootView;
    }

    public void requestProject() {
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
                    if (resReturn.equals("true")) {
                        JSONArray resData = response.getJSONArray("data");
                        for (int i = 0; i < resData.length(); i++) {
                            id.add(resData.getJSONObject(i).getString("id"));
                            code.add(resData.getJSONObject(i).getString("code"));
                            name.add(resData.getJSONObject(i).getString("name"));
                        }
                        ok200 = true;
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getContext(),resRespone,Toast.LENGTH_LONG).show();
                        ok200 = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                ok200 = false;
                //Toast.makeText(getApplicationContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private class LoadCheckOut extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pd;

        protected void onPreExecute() {
//            pd = new ProgressDialog(activity);
////            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
//            pd.setTitle("Loging in...");
//            pd.setMessage("Loading ...");
//            pd.setCancelable(false);
//            pd.setIndeterminate(false);
////            pd.setMax(100);
////            pd.setProgress(0);
//            pd.show();

        }

        protected Void doInBackground(Void... params) {
            requestProject();
            return null;
        }

        protected void onProgressUpdate(Integer... values) {
            pd.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            //pd.dismiss();
            //setContentView(activity);

                if (ok200) {
                    arrayAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
                }
            }
    }
}
