package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.EditText;
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

    ArrayList<String> detailList = new ArrayList<String>(),timeList = new ArrayList<String>(),rtList = new ArrayList<String>(),otList = new ArrayList<String>(),xtList = new ArrayList<String>(),codeList = new ArrayList<String>(),projectList = new ArrayList<String>();
    ArrayList<String> id  = new ArrayList<String>(),code  = new ArrayList<String>(),name  = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    //Boolean ok200 = true;
    MainActivity activity;
    int position= 99;
    EditText detail,rt,ot,xt;
    String temp,mode="project";
    ListView myListView;
    CheckoutAdapter myAdapter;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    public CheckOutFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_check_out, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        if(name.isEmpty()){
            Log.i("name","empty");
            new LoadCheckOut().execute();
        }
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, name);
        myAdapter = new CheckoutAdapter(getActivity(),android.R.layout.simple_list_item_1,detailList,timeList,projectList);
        myListView = (ListView)rootView.findViewById(R.id.listView2);
        myListView.setAdapter(myAdapter);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), detailList.get(i), Toast.LENGTH_SHORT).show();

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
                        mode = "check out";
                        new LoadCheckOut().execute();
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
                arrayAdapter.notifyDataSetChanged();
                final Dialog dialog = new Dialog(getActivity());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.checkout_dialog);
                dialog.setCancelable(true);
                detail = (EditText)dialog.findViewById(R.id.editText5);
                rt = (EditText)dialog.findViewById(R.id.editTextRT);
                ot = (EditText)dialog.findViewById(R.id.editTextOT);
                xt = (EditText)dialog.findViewById(R.id.editTextXT);
                Button addButton = (Button)dialog.findViewById(R.id.button12);
                addButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(getActivity(),"Add",Toast.LENGTH_SHORT).show();
                        detailList.add(detail.getText().toString());
                        temp ="";
                        if(rt.length()>0){
                            temp += "RT " + rt.getText().toString();
                            rtList.add(rt.getText().toString());
                        }
                        else{
                            rtList.add("");
                        }
                        if(ot.length()>0){
                            temp += "OT " + rt.getText().toString();
                            otList.add(ot.getText().toString());
                        }
                        else{
                            otList.add("");
                        }
                        if(xt.length()>0){
                            temp += "XT " + rt.getText().toString();
                            xtList.add(xt.getText().toString());
                        }else{
                            xtList.add("");
                        }
                        timeList.add(temp);

                        projectList.add(name.get(position));
                        codeList.add(code.get(position));
                        myAdapter.notifyDataSetChanged();
                        dialog.dismiss();
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
                        Toast.makeText(getContext(),"Select " + i,Toast.LENGTH_SHORT).show();
                        position = i;
                    }
                });
                dialog.show();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity)getActivity();
    }

    public void requestProject() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/project/";
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
                        //ok200 = true;
                        arrayAdapter.notifyDataSetChanged();
                    }
                    else{
                        Toast.makeText(getContext(),resRespone,Toast.LENGTH_LONG).show();
                        //ok200 = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                //ok200 = false;
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    public String getTimeSheetString(){

        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("[");
        for(int count = 0;count<detailList.size();count++){
            strBuilder.append("{\"pcode\": +\"");
            strBuilder.append(codeList.get(count));
            strBuilder.append("\", \"detail\": ");
            strBuilder.append(detailList.get(count));
            strBuilder.append("\", \"rt\": ");
            strBuilder.append(rtList.get(count));
            strBuilder.append("\", \"ot\": ");
            strBuilder.append(otList.get(count));
            strBuilder.append("\", \"xt\": ");
            strBuilder.append(xtList.get(count));
            strBuilder.append("}");
            if(!(count == detailList.size()-1))strBuilder.append(",");
        }
        strBuilder.append("]");
        return strBuilder.toString();
    }

    public void requestCheckOut() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/checkout/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Constants.API_KEY);
        params.put("u", sp.getString("Username", "testuser"));
        params.put("data", getTimeSheetString());
        params.put("mode", "dev");
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
                        String resData = response.getString("add_timesheet");
                        //ok200 = true;
                        getFragmentManager().popBackStack();
                        editor.putString("checkInLocation","---");
                        editor.putString("checkInLocationID", "null");
                        editor.putString("dateCheckIn", "--/--/--");
                        editor.putString("isCheckIn", "CHECK IN");
                        editor.commit();
                    }
                    else{
                        Toast.makeText(getContext(),resRespone,Toast.LENGTH_LONG).show();
                        //ok200 = false;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                //ok200 = false;
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    private class LoadCheckOut extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pd;

        protected void onPreExecute() {
            pd = new ProgressDialog(activity);
//            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Loging in...");
            pd.setMessage("Loading ...");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
//            pd.setMax(100);
//            pd.setProgress(0);
            pd.show();

        }

        protected Void doInBackground(Void... params) {
            if(!mode.equals("check out")){
                requestProject();
            }
            else{
                requestCheckOut();
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
            if(mode.equals("check out")){
//                if (ok2001) {
//                    getFragmentManager().popBackStack();
//                    editor.putString("checkInLocation","---");
//                    editor.putString("checkInLocationID", "null");
//                    editor.putString("dateCheckIn", "--/--/--");
//                    editor.putString("isCheckIn", "CHECK IN");
//                    editor.commit();
//                } else {
//                    Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
//                }
            }
            else{
//                if (ok2002) {
//                    arrayAdapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
//                }
            }

            }
    }
}
