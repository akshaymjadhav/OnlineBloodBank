package com.aj.onlinebloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

/**
 * Created by jadhave on 08-Jun-17.
 */

public class
Feed extends AppCompatActivity {

    ImageButton b1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);

        b1=(ImageButton)findViewById(R.id.b1);


        Intent in=getIntent();

    }
}