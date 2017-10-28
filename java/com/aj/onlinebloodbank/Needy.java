package com.aj.onlinebloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Needy extends AppCompatActivity {

    Button b1,b2;
    TextView t1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_needy);

        b1=(Button) findViewById(R.id.b1);
        b2=(Button) findViewById(R.id.b2);
        t1=(TextView) findViewById(R.id.txtTicker);

        t1.setSelected(true);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Needy.this,List2.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Needy.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
