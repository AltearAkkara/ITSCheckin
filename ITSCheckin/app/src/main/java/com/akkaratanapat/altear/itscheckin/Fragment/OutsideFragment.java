package com.akkaratanapat.altear.itscheckin.Fragment;


import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.akkaratanapat.altear.itscheckin.Constants;
import com.akkaratanapat.altear.itscheckin.CustomRequest;
import com.akkaratanapat.altear.itscheckin.MainActivity;
import com.akkaratanapat.altear.itscheckin.MultipartRequest;
import com.akkaratanapat.altear.itscheckin.R;
import com.akkaratanapat.altear.itscheckin.VolleySingleton;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class OutsideFragment extends Fragment {

    MainActivity activity;
    Button takePhotoButton;
    ImageView imageView;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> id  = new ArrayList<String>(),code  = new ArrayList<String>(),name  = new ArrayList<String>();
    ListView listView;
    int position = 0;
    Uri uri;
    EditText locationEdit,reasonEdit;
    ArrayList<String> location = new ArrayList<String>(), idLocation = new ArrayList<String>();
    String mode = "location";
    SharedPreferences sp;
    SharedPreferences.Editor editor;
    ProgressDialog pd;


    public OutsideFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_outside, container, false);
        sp = this.getActivity().getSharedPreferences("Temp", Context.MODE_PRIVATE);
        editor = sp.edit();
        ((AppCompatActivity)getActivity()).setTitle("Outside Working");
        takePhotoButton = (Button) rootView.findViewById(R.id.button17);
        imageView = (ImageView) rootView.findViewById(R.id.imageView3);
        listView = (ListView) rootView.findViewById(R.id.listView3);
        locationEdit = (EditText)rootView.findViewById(R.id.editText3);
        reasonEdit = (EditText)rootView.findViewById(R.id.editText4);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle("Choose option");

                alertDialog.setMessage("Take photo using  gallery or camera");

                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Camera", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        int REQUEST_CAMERA = 0;
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        String timeStamp =
                                new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                        String imageFileName = "IMG_" + timeStamp + ".jpg";
                        File f = new File(Environment.getExternalStorageDirectory()
                                , "DCIM/Camera/" + imageFileName);
                        uri = Uri.fromFile(f);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(Intent.createChooser(intent
                                , "Take a picture with"), REQUEST_CAMERA);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Gallery", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(pickPhoto, 1);
                    }
                });

                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
                alertDialog.show();
            }
        });
        Button checkIn = (Button) rootView.findViewById(R.id.button10);
        checkIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder aBuilder = new AlertDialog.Builder(getActivity());
                aBuilder.setTitle("Confirmation.").setMessage("Do you want to check in?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mode = "checkin";
                        new LoadCheckin().execute();
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
        Button history = (Button) rootView.findViewById(R.id.button11);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.changeFragmentByFragment("history_outside");
            }
        });
        new LoadCheckin().execute();
        arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_expandable_list_item_1, name);
        listView.setAdapter(arrayAdapter);
        listView.setSelector(R.color.dark_gray);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
                Toast.makeText(getContext(), "Select : " + name.get(position).toString(), Toast.LENGTH_SHORT);
                Log.i("kkkk", position + "");
            }
        });
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0) {//camera
            if (resultCode == -1) {
                getActivity().getContentResolver().notifyChange(uri, null);
                ContentResolver cr = getActivity().getContentResolver();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(cr, uri);
                    imageView.setImageBitmap(bitmap);
                    Toast.makeText(getActivity()
                            , uri.getPath(), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else if (requestCode == 1) {//gallery
            if (resultCode == -1) {
                Uri uri = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);
                    imageView.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainActivity) getActivity();
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
                    pd.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError response) {
                Log.d("Response: ", response.toString());
                //ok200 = false;
                pd.dismiss();
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsObjRequest);
    }

    public void requestCheckin() {
        String url = "http://itsserver.itsconsultancy.co.th:8080/Itsconsultancy/itscheckin/service/outside/";
        Log.d("Outside: ", "imcoming");
        MultipartRequest multipartRequest = new MultipartRequest(Request.Method.POST, url, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse networkResponse) {
                String resReturn, resCode, resType, resRespone;
                String resultResponse = new String(networkResponse.data);
                try {
                    JSONObject result = new JSONObject(resultResponse);
                    resReturn = result.getString("return");
                    resCode = result.getString("code");
                    resType = result.getString("type");
                    resRespone = result.getString("response");
                    Log.d("Outside2: ", "imcoming");
                    Toast.makeText(getContext(), resRespone, Toast.LENGTH_SHORT).show();
                    pd.dismiss();

                    getFragmentManager().popBackStack();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Response: ", error.toString());
                pd.dismiss();
                Toast.makeText(getContext(), "Network Error", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("apikey", Constants.API_KEY);
                params.put("u", sp.getString("Username", "testuser"));
                params.put("location",locationEdit.getText().toString() );
                params.put("detail", reasonEdit.getText().toString());
                params.put("lat", sp.getString("lat","13.766"));
                params.put("lng", sp.getString("lat","100.605"));
                params.put("pid", code.get(position));
                return params;
            }

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                // file name could found file base or direct access from real path
                // for now just get bitmap data from ImageView
                params.put("image", new DataPart("file_avatar.jpg", getFileDataFromDrawable(), "image/jpeg"));

                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(multipartRequest);

    }

    private byte[] getFileDataFromDrawable() {
        //Drawable drawable = ContextCompat.getDrawable(context, id);
        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        if(bitmap.getWidth()>bitmap.getHeight()){
            if(bitmap.getWidth()>1024){
                bitmap = Bitmap.createScaledBitmap(bitmap,1024 * bitmap.getHeight() / bitmap.getWidth(), 1024, false);
            }
        }
        else if(bitmap.getWidth()<bitmap.getHeight()){
            if(bitmap.getHeight()>1024){
                bitmap = Bitmap.createScaledBitmap(bitmap,1024, 1024 * bitmap.getWidth() / bitmap.getHeight(), false);
            }
        }
        else{
            if(bitmap.getHeight()>1024){
                bitmap = Bitmap.createScaledBitmap(bitmap,1024, 1024, false);
            }
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private class LoadCheckin extends AsyncTask<Void, Integer, Void> {

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
            if (mode.equals("location")) {
                requestProject();
            } else {
                requestCheckin();
            }
            try {
                synchronized (this) {
                    for (int i = 0; i < 10000; i++) {

                        int c = (int) ((i / 100) * (i + 1));
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
//            pd.dismiss();
            //setContentView(activity);

        }
    }
}
