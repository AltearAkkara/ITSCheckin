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
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    EditText userText, passwordText;
    Button loginButton;
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    MainActivity activity;
    Boolean ok200 = true;
    //ProgressDialog pd;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_login, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        userText = (EditText) rootView.findViewById(R.id.editText);
        passwordText = (EditText) rootView.findViewById(R.id.editText2);
        loginButton = (Button) rootView.findViewById(R.id.buttonLogin);
        String user = sp.getString("Username", "testuser");
        String password = sp.getString("Password", "1234");
        userText.setText(user);
        passwordText.setText(password);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadLogin().execute();
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
    }


    public void requestLogin() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        final String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/loGIN/";
        Map<String, String> params = new HashMap<String, String>();
        params.put("apikey", Constants.API_KEY);
        params.put("u", userText.getText().toString());
        params.put("p", passwordText.getText().toString());
        JSONObject jsonObj = new JSONObject(params);
        CustomRequest jsObjRequest = new CustomRequest(Request.Method.POST, url, params, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String resReturn, resCode, resType, resRespone, resCheckin;
                try {
                    resReturn = response.getString("return");
                    resCode = response.getString("code");
                    resType = response.getString("type");
                    resRespone = response.getString("response");
                    if (resReturn.equals("true")) {
                        resCheckin = response.getString("checkin");
                        editor.putString("Username", userText.getText().toString());
                        editor.putString("Password", passwordText.getText().toString());
                        editor.commit();
                        //pd.dismiss();
                        activity.changeFragmentByFragment("main");
                        ok200 = true;
                    } else {
                        Toast.makeText(getContext(), resRespone, Toast.LENGTH_LONG).show();
                        //pd.dismiss();
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

    private class LoadLogin extends AsyncTask<Void, Integer, Void> {
        ProgressDialog pd;

        protected void onPreExecute() {
            pd = new ProgressDialog(getActivity());
            //pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Loging in...");
            pd.setMessage("Loading ...");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            //pd.setMax(100);
            //pd.setProgress(0);
            pd.show();

        }

        protected Void doInBackground(Void... params) {

            requestLogin();
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
            // pd.setProgress(values[0]);
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pd.dismiss();
//            if (ok200) {
//                activity.changeFragmentByFragment("main");
//                editor.putString("Username", userText.getText().toString());
//                editor.putString("Password", passwordText.getText().toString());
//                editor.commit();
//            } else {
//                Toast.makeText(getContext(), "Unsuccessfully", Toast.LENGTH_SHORT).show();
//            }
        }
    }


}
