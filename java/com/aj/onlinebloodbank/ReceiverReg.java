package com.aj.onlinebloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class ReceiverReg extends AppCompatActivity implements View.OnClickListener {

    private static final String REGISTER_URL = "http://192.168.225.148/insertreceiver.php";
    TextView t1;
    EditText ed1, ed2;
    Button b1;
    SharedPreferences sp;
    public static final String KEY_NAME = "Name";
    public static final String KEY_PHONE = "Phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiver_reg);

        t1 = (TextView) findViewById(R.id.t1);

        ed1 = (EditText) findViewById(R.id.ed1);
        ed2 = (EditText) findViewById(R.id.ed2);

        b1 = (Button) findViewById(R.id.b1);
        sp=this.getSharedPreferences("data",MODE_PRIVATE);

        b1.setOnClickListener(this);
    }

    private void registeruser() {

        final String Name = ed1.getText().toString().trim();
        final String Phone = ed2.getText().toString().trim();

        SharedPreferences.Editor editor=sp.edit();

        editor.putString("name",Name);
        editor.putString("phone",Phone);
        editor.commit();

        if (!TextUtils.equals("", Name) && !TextUtils.equals("", Phone) && Phone.length()==10) {


            StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(ReceiverReg.this, response, Toast.LENGTH_LONG).show();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ReceiverReg.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_NAME, Name);
                    params.put(KEY_PHONE, Phone);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

            Intent int1 = new Intent(ReceiverReg.this, Search.class);
            int1.putExtra("data",Name);
            int1.putExtra("data1",Phone);
            startActivity(int1);
        } else {
            Toast.makeText(ReceiverReg.this, "Enter Correct Details", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onClick(View v) {
        if (v == b1) {
            registeruser();
        }

    }
}


