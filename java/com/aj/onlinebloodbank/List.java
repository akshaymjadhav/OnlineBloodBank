package com.aj.onlinebloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.aj.onlinebloodbank.Adapter.CustomAdapter;
import com.aj.onlinebloodbank.Pojo.Donor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class List extends AppCompatActivity {

    ListView l;
    ArrayList<Donor> arrayList = new ArrayList<>();
    CustomAdapter c1;
    Intent int2;
    Button b1;
    TextView t1;

    String response;

    String url1 = "http://192.168.225.148/TEST.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        b1=(Button) findViewById(R.id.b1);
        t1=(TextView) findViewById(R.id.t1);
        l = (ListView) findViewById(R.id.list1);
        Intent i = getIntent();
        JSONObject jsonObject1 = null;
        response = i.getStringExtra("Res");
        try {
            jsonObject1 = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Donor donor;
        try {
            JSONArray jsonArray = jsonObject1.getJSONArray("data");

            for (int j = 0; j < jsonArray.length(); j++) {
                JSONObject jsonObject = jsonArray.getJSONObject(j);
                String name = jsonObject.getString("Name");
                String address = jsonObject.getString("Address");
                String phone = jsonObject.getString("Phone");
                String blood = jsonObject.getString("Blood");
                String age = jsonObject.getString("Age");

                donor = new Donor(name, address, phone, blood, age);
                arrayList.add(donor);
            }
            regresh();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent int1=new Intent(List.this,MapsActivity2.class);
                startActivity(int1);
            }
        });


        //    c1 = new CustomAdapter(this, arrayList);

        // l.setAdapter(c1);


//
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url1, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        Donor donor;
//                        try {
//                            JSONArray jsonArray = response.getJSONArray("data");
//
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                                String name = jsonObject.getString("Name");
//                                String address = jsonObject.getString("Address");
//                                String phone = jsonObject.getString("Phone");
//                                String blood = jsonObject.getString("Blood");
//                                String age = jsonObject.getString("Age");
//
//                                   donor = new Donor(name, address, phone, blood, age);
//                                   arrayList.add(donor);
//                            }
//                            regresh();
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//        RequestQueue requestQueue = Volley.newRequestQueue(this);
//        requestQueue.add(jsonObjectRequest);
//

    }

    void regresh() {
        c1 = new CustomAdapter(this, arrayList);
        l.setAdapter(c1);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
