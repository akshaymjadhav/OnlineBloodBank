package com.aj.onlinebloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

/**
 * Created by jadhave on 08-Jun-17.
 */

public class helping extends AppCompatActivity {
    TextView textView1, textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helping);

        textView1 = (TextView) findViewById(R.id.t1);
        textView2 = (TextView) findViewById(R.id.t2);

        Intent intent1 = getIntent();
        textView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2=new Intent(helping.this,Use.class);
                startActivity(intent2);

            }
        });

        textView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3=new Intent(helping.this,Us.class);
                startActivity(intent3);
            }
        });



    }

}
