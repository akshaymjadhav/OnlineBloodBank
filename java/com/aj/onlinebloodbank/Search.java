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

public class Search extends AppCompatActivity implements View.OnClickListener {

    TextView t1;
    EditText ed1;
    Button b1;
    private static final String URL = "http://192.168.225.148/TEST.php";
    public static final String KEY_BLOOD = "Blood";
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        t1 = (TextView) findViewById(R.id.t1);
        ed1 = (EditText) findViewById(R.id.ed1);
        b1 = (Button) findViewById(R.id.b1);
        sp = this.getSharedPreferences("data2", MODE_PRIVATE);
        b1.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        enterintodb();
        final String Blood = ed1.getText().toString();
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("blood", Blood);
        editor.commit();
    }

    public void enterintodb() {


        final String Blood = ed1.getText().toString().trim();
//
        if (!TextUtils.equals("", Blood) && (TextUtils.equals("A+", Blood) || TextUtils.equals("A-", Blood) || TextUtils.equals("B+", Blood) || TextUtils.equals("B-", Blood) || TextUtils.equals("AB+", Blood) || TextUtils.equals("AB-", Blood) || TextUtils.equals("O+", Blood) || TextUtils.equals("0-", Blood))) {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //Toast.makeText(Search.this, response, Toast.LENGTH_LONG).show();

                            if (response.equals("{\"data\":[]}")) {
                                Toast.makeText(Search.this, "No Donor Found.....", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Search.this, Waiting.class);
                                startActivity(intent);
                            } else {
                                Intent int1 = new Intent(Search.this, List.class);
                                int1.putExtra("Res", response);
                                startActivity(int1);

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(Search.this, error.toString(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(KEY_BLOOD, Blood);
                    return params;

                }

            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {

            Toast.makeText(Search.this, "Enter Correct Blood Group", Toast.LENGTH_SHORT).show();
        }
    }
}
