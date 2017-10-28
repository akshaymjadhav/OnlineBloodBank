package com.aj.onlinebloodbank;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.aj.onlinebloodbank.Adapter.CustomAdapter2;
import com.aj.onlinebloodbank.Pojo.Receiver;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class List2 extends AppCompatActivity {

    ListView l;
    ArrayList<Receiver> arrayList = new ArrayList<>();
    CustomAdapter2 c1;

    String url1 = "http://192.168.225.148/waiting.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list2);

        l = (ListView) findViewById(R.id.list2);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Receiver receiver;
                        try {
                            JSONArray jsonArray = response.getJSONArray("data");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String name = jsonObject.getString("Name");
                                String phone = jsonObject.getString("Phone");
                                String blood = jsonObject.getString("Blood");

                                receiver = new Receiver(name, phone, blood);
                                arrayList.add(receiver);
                            }
                            regresh();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }


    void regresh() {
        c1 = new CustomAdapter2(this, arrayList);
        l.setAdapter(c1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

