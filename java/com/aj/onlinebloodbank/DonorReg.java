package com.aj.onlinebloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DonorReg extends AppCompatActivity {
    String s1,s2;
SharedPreferences  sp;
    boolean check=false;
    private static final String REGISTER_URL = "http://192.168.225.148/insert.php";


    public static final String KEY_NAME = "Name";
    public static final String KEY_ADDRESS = "Address";
    public static final String KEY_PHONE = "Phone";
    public static final String KEY_BLOOD = "Blood";
    public static final String KEY_AGE = "Age";
    public static final String KEY_LATITUDE = "s1";
    public static final String KEY_LONGITUDE = "s2";

    EditText ed1, ed2, ed3, ed4, ed5;
    Button b1, b2;
    GPSTracker gps;
    static String Name = "";
    static String Address = "";
    static String Phone = "";
    static String Blood = "";
    static String Age = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_reg);


        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);
        ed3 = (EditText) findViewById(R.id.ed3);
        ed4 = (EditText) findViewById(R.id.ed4);
        ed5 = (EditText) findViewById(R.id.ed5);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);

        sp=this.getSharedPreferences("private1",MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String s12 = sp.getString("lat", "a");
                final String s22 = sp.getString("lng", "b");
                Log.e("s1=", s12);
                Log.e("s2=", s22);
                Name = ed1.getText().toString();
                Address = ed2.getText().toString().trim();
                Phone = ed3.getText().toString().trim();
                Blood = ed4.getText().toString().trim();
                Age = ed5.getText().toString().trim();


                if (!TextUtils.equals("", Name) && !TextUtils.equals("", Blood) && !TextUtils.equals("", Blood) && (TextUtils.equals("A+", Blood) || TextUtils.equals("A-", Blood) || TextUtils.equals("B+", Blood) || TextUtils.equals("B-", Blood) || TextUtils.equals("AB+", Blood) || TextUtils.equals("AB-", Blood) || TextUtils.equals("O+", Blood) || TextUtils.equals("0-", Blood)) && Phone.length()==10) {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(DonorReg.this, response, Toast.LENGTH_LONG).show();
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(DonorReg.this, error.toString(), Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(KEY_NAME, Name);
                            params.put(KEY_ADDRESS, Address);
                            params.put(KEY_PHONE, Phone);
                            params.put(KEY_BLOOD, Blood);
                            params.put(KEY_AGE, Age);
                            params.put(KEY_LATITUDE, s12);
                            params.put(KEY_LONGITUDE, s22);
                            return params;

                        }

                    };
                    RequestQueue requestQueue = Volley.newRequestQueue(DonorReg.this);
                    requestQueue.add(stringRequest);
                    Intent intent = new Intent(DonorReg.this, Needy.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(DonorReg.this, "Enter Details Correctly", Toast.LENGTH_SHORT).show();
                }


            }
        });




        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
           // gps = new GPSTracker(DonorReg.this);

                if (true) {

//                    double latitude = gps.getLatitude();
//                    double longitude = gps.getLongitude();


//                    if (longitude == 0 && latitude == 0)
//                        b1.setEnabled(false);
//                    else
//                        b1.setEnabled(true);

                    // \n is for new line
                    Intent intent = new Intent(DonorReg.this, MapsActivity2.class);
                    startActivity(intent);

                } else {

//                    gps.showSettingsAlert();
                }
            }
        });
    }



}