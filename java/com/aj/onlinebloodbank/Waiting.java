package com.aj.onlinebloodbank;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

import static com.aj.onlinebloodbank.DonorReg.Blood;
import static com.aj.onlinebloodbank.DonorReg.Name;
import static com.aj.onlinebloodbank.DonorReg.Phone;

public class Waiting extends AppCompatActivity {
    TextView t1;
    Button b1, b2;
    private static final String url = "http://192.168.225.148/insertwait.php";

//    public static final String name = "Name";
//    public static final String phone = "Phone";
//    public static final String blood = "Blood";
    SharedPreferences sp,sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waiting);

        t1 = (TextView) findViewById(R.id.t1);
        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);
        sp = this.getSharedPreferences("data", MODE_PRIVATE);
        sp2=this.getSharedPreferences("data2",MODE_PRIVATE);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



               final String n = sp.getString("name", Name);
                final String p = sp.getString("phone", Phone);
               final String  b = sp2.getString("blood", Blood);

                Toast.makeText(Waiting.this, "Name=" + n + "Phone=" + p+"Blood=" + b, Toast.LENGTH_SHORT).show();


                StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Waiting.this, response, Toast.LENGTH_LONG).show();

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Waiting.this, error.toString(), Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("n", n);
                        params.put("p", p);
                        params.put("b", b);
                        return params;

                    }

                };
                RequestQueue requestQueue = Volley.newRequestQueue(Waiting.this);
                requestQueue.add(stringRequest);

            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Waiting.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
