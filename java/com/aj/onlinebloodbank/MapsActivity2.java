package com.aj.onlinebloodbank;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
import java.util.Locale;

public class MapsActivity2 extends FragmentActivity implements OnMapReadyCallback{
    Button button1, button2;
    EditText searchView;
    private GoogleMap mMap;
    String lat;
    String lng;
    double lat1, lng1;
    SharedPreferences sp;
    SharedPreferences sp2;
    Button btn_start;
    private static final int REQUEST_PERMISSIONS = 100;
    boolean boolean_permission;
    SharedPreferences mPref;
    SharedPreferences.Editor medit;
    Double latitude,longitude;
    Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        //Intent intent1=new Intent(MapsActivity.this,GPSTracker.class);
        //startActivity(intent1);
        sp=this.getSharedPreferences("private1",MODE_PRIVATE);
        geocoder = new Geocoder(this, Locale.getDefault());
        mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        medit = mPref.edit();
//        Intent intent = new Intent(this, GPSTracker.class);
//        startService(intent);

        button1 = (Button) findViewById(R.id.button1);
        searchView = (EditText) findViewById(R.id.searchView1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }




    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;
//        LocationManager service=(LocationManager)getSystemService(LOCATION_SERVICE);
//        boolean enabled=service.isProviderEnabled(LocationManager.GPS_PROVIDER);
//        if(!enabled)
//        {
//            Intent intent=new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//            startActivity(intent);
//        }
//        Location location=LocationManager
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                latitude = Double.valueOf(intent.getStringExtra("latutide"));
                longitude = Double.valueOf(intent.getStringExtra("longitude"));
                final LatLng loc = new LatLng(latitude,longitude);
                mMap.addMarker(new MarkerOptions().position(loc).title("I'm Here"));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
                mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // mMap.setTrafficEnabled(true);

        mMap.getUiSettings().setAllGesturesEnabled(true);

      //  sp2 = this.getSharedPreferences("private1", MODE_PRIVATE);
//        sp = this.getSharedPreferences("private", MODE_PRIVATE);
//        lat = sp.getString("lat", " ");
//        lng = sp.getString("lng", " ");
//        lat1 = Double.parseDouble(lat);
//        lng1 = Double.parseDouble(lng);
        // Add a marker in Sydney and move the camera
        // final LatLng loc = new LatLng(lat1, lng1);
        //mMap.addMarker(new MarkerOptions().position(loc).title("I'm Here"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        // Setting a click event handler for the map
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {

            @Override
            public void onMapClick(LatLng latLng) {

                // Creating a marker
                mMap.clear();
                //      mMap.addMarker(new MarkerOptions().position(loc).title("I'm Here"));
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);
                double latitude=latLng.latitude;
                double longitude=latLng.longitude;
                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                // googleMap.clear();

                // Animating to the touched position
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
                Circle circle=mMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeColor(Color.RED));

                //  Toast.makeText(MapsActivity.this, "Selected Location is - \nLat: "
                //          + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
                String lati= String.valueOf(latitude);
                String longi=String.valueOf(longitude);
                SharedPreferences.Editor editor=sp.edit();
                editor.putString("lat", lati);
                editor.putString("lng", longi);
                editor.commit();

            }


        });

        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String g = searchView.getText().toString();

                Geocoder geocoder = new Geocoder(getBaseContext());
                List<Address> addresses = null;

                try {
                    // Getting a maximum of 3 Address that matches the input
                    // text
                    addresses = geocoder.getFromLocationName(g, 3);
                    if (addresses != null && !addresses.equals(""))
                        search(addresses);

                } catch (Exception e) {

                }

            }
        });
        Button set=(Button)findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lt=sp.getString("lat","");
                String ln=sp.getString("lng","");
                if(!TextUtils.equals("",lt) && !TextUtils.equals("",ln)) {
                    Toast.makeText(MapsActivity2.this, "Location Added Successfully", Toast.LENGTH_SHORT).show();
                finish();
                }
                else
                {
                    Toast.makeText(MapsActivity2.this, "Set the location first", Toast.LENGTH_SHORT).show();
                }

            }
        });

        Button cancel=(Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity2.this,"User press Cancel Button", Toast.LENGTH_SHORT).show();
                sp2.edit().putString("lat","").commit();
                sp2.edit().putString("lng","").commit();
                Intent intent=new Intent(MapsActivity2.this,DonorReg.class);
                startActivity(intent);
                finish();
            }
        });


    }
    protected void search(List<Address> addresses) {


        mMap.clear();
//        sp2=this.getSharedPreferences("private1",MODE_PRIVATE);
//        sp1 = this.getSharedPreferences("private", MODE_PRIVATE);
//        lat = sp1.getString("lat", " ");
//        lng = sp1.getString("lng", " ");
//        lat1 = Double.parseDouble(lat);
//        lng1 = Double.parseDouble(lng);
        // Add a marker in Sydney and move the camera
        //  final LatLng loc = new LatLng(lat1, lng1);
        //    mMap.addMarker(new MarkerOptions().position(loc).title("I'm Here") .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
        Address address = (Address) addresses.get(0);
        final double   home_long = address.getLongitude();
        final double   home_lat = address.getLatitude();
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String    addressText = String.format("%s, %s",address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getCountryName());
        mMap.clear();
//        Circle circle=mMap.addCircle(new CircleOptions().center(latLng).radius(100).strokeColor(Color.RED));
        MarkerOptions markerOptions = new MarkerOptions();

        markerOptions.position(latLng);
        markerOptions.title(addressText);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        String lati= String.valueOf(home_lat);
        String longi=String.valueOf(home_long);
        SharedPreferences.Editor editor=sp.edit();
        editor.putString("lat", lati);
        editor.putString("lng", longi);
        editor.commit();

    }
}

