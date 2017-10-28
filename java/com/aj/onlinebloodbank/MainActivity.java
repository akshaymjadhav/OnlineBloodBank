package com.aj.onlinebloodbank;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static com.aj.onlinebloodbank.R.id.nav_feedback;
import static com.aj.onlinebloodbank.R.id.nav_help;
import static com.aj.onlinebloodbank.R.id.nav_share;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Button b1,b2;
    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        t1=(TextView) findViewById(R.id.txtTicker);

        b1 = (Button) findViewById(R.id.b1);
        b2 = (Button) findViewById(R.id.b2);

        t1.setSelected(true);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DonorReg.class);
                startActivity(intent);
            }
        });
       b2.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(MainActivity.this,ReceiverReg.class);
               startActivity(intent);
           }
       });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //displaselectedscreen(R.id.nav_help);
        //displaselectedscreen(R.id.nav_feedback);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        return super.onOptionsItemSelected(item);
    }

    //  private void displaselectedscreen(int id) {

//        Fragment fragment = null;
    //      switch (id) {


    //        case R.id.nav_help:
    //          fragment = new Help();
    //        break;
    //  case R.id.nav_feedback:
    //    fragment = new Feedback();
    //  break;
    // case R.id.nav_share:
    //   fragment = new Share();
    // break;


    // }

    //if (fragment != null) {
    //  FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
    // ft.replace(R.id.content_main,fragment);
    //ft.commit();
    //}
    //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    //drawer.closeDrawer(GravityCompat.START);


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (item.getItemId() == nav_help) {


            Intent intent1 = new Intent(MainActivity.this, helping.class);
            startActivity(intent1);


        }
        if (item.getItemId() == nav_feedback) {


            Intent intent1 = new Intent(MainActivity.this, Feed.class);
            startActivity(intent1);


        }
        if (item.getItemId() == nav_share) {

            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            //sendIntent.putExtra(Intent.EXTRA_TEXT,
            // "Hey check out my app at: https://play.google.com/store/apps/details?id=com.google.android.apps.plus");
            sendIntent.setType("text/plain");
            startActivity(sendIntent);





        }


        // displaselectedscreen(id);
        return true;
    }


}