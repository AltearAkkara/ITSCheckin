package com.akkaratanapat.altear.itscheckin;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    Toolbar toolbar;
    EditText userText,passwordText;
    Button loginButton;
    SharedPreferences sp;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        setTitle("Login");
        sp = getPreferences(Context.MODE_PRIVATE);
        editor = sp.edit();
        userText = (EditText)findViewById(R.id.editText);
        passwordText = (EditText)findViewById(R.id.editText2);
        loginButton = (Button)findViewById(R.id.buttonLogin);
        Log.i("Login","create");
        String user = sp.getString("Username","testuser");
        String password = sp.getString("Password","1234");
        userText.setText(user);
        passwordText.setText(password);
        setCustomComponent();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i("Login","start");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Login","resume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("Login","pause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("Login","stop");
    }

    public void setCustomComponent(){
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new LoadLogin().execute();
            }
        });
    }

    private class LoadLogin extends AsyncTask<Void, Integer, Void>
    {
        ProgressDialog pd;

        protected void onPreExecute()
        {
            pd = new ProgressDialog(LoginActivity.this);
            pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pd.setTitle("Loging in...");
            pd.setMessage("Loading ...");
            pd.setCancelable(false);
            pd.setIndeterminate(false);
            pd.setMax(100);
            pd.setProgress(0);
            pd.show();
        }

        protected Void doInBackground(Void... params)
        {
            for(int count =0;count<10000;count++){
                publishProgress(count/100);
            }
            return null;
        }

        protected void onProgressUpdate(Integer... values)
        {
            pd.setProgress(values[0]);
        }

        protected void onPostExecute(Void result)
        {
            pd.dismiss();
            setContentView(R.layout.activity_login);
            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            editor.putString("Username", userText.getText().toString());
            editor.putString("Password", passwordText.getText().toString());
            editor.commit();
        }
    }
}
