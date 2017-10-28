package com.aj.onlinebloodbank;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    Button button1, button2;
    EditText searchView;


    private GoogleMap mMap;
    private static final String REGISTER_URL = "http://192.168.225.148/insert.php";

    String lat;
    String lng;
    double lat1, lng1;
    private long UPDATE_INTERVAL = 60000;  /* 60 secs */
    private long FASTEST_INTERVAL = 5000; /* 5 secs */

    LocationRequest mLocationRequest;
    GoogleApiClient mGoogleApiClient;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    Location mLastLocation;

    String url = "http://192.168.225.148/insert.php";

    List<Address> addresses;


    public static final String KEY_LATITUDE = "Latitude";
    public static final String KEY_LONGITUDE = "Longitude";

//    SharedPreferences sp1;
//    SharedPreferences sp2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        button1 = (Button) findViewById(R.id.button1);
        searchView = (EditText) findViewById(R.id.searchView1);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        mGoogleApiClient.connect();
    }


    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Needed")
                        .setMessage("This app needs the Location permission, please accept to use location functionality")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();


            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        }
    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;


        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                //Location Permission already granted
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            } else {
                //Request Location Permission
                checkLocationPermission();
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }


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

        mMap.setOnMyLocationButtonClickListener(new GoogleMap.OnMyLocationButtonClickListener() {
            @Override
            public boolean onMyLocationButtonClick() {


                if (ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
//                    return TODO;
                }
                Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
                if (location != null) {
                    //  Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
                    LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


                    MarkerOptions markerOptions = new MarkerOptions();
                    markerOptions.position(latLng);
                    markerOptions.title("Current Position");
                    markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    mMap.addMarker(markerOptions);


                    CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
                    mMap.animateCamera(cameraUpdate);
                } else {
                    //Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
                }
                return false;
            }
        });
//        sp2 = this.getSharedPreferences("private1", MODE_PRIVATE);
//        sp1 = this.getSharedPreferences("private", MODE_PRIVATE);
//        lat = sp1.getString("lat", " ");
//        lng = sp1.getString("lng", " ");
//        lat1 = DonorReg.latitude;
//        lng1 = DonorReg.longitude;
        // Add a marker in Sydney and move the camera
        final LatLng loc = new LatLng(lat1, lng1);
        mMap.addMarker(new MarkerOptions().position(loc).title("I'm Here"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(loc));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
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
                double latitude = latLng.latitude;
                double longitude = latLng.longitude;
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

                //  Toast.makeText(MapsActivity.this, "Selected Location is - \nLat: "
                //          + latitude + "\nLong: " + longitude, Toast.LENGTH_SHORT).show();
                String lati = String.valueOf(latitude);
                String longi = String.valueOf(longitude);
//                SharedPreferences.Editor editor=sp2.edit();
//                editor.putString("lat", lati);
//                editor.putString("lng", longi);
//                editor.commit();

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
                    if (addresses != null && !addresses.equals("")) {
                        search(addresses);


                    }

                } catch (Exception e) {

                }

            }
        });
        Button set = (Button) findViewById(R.id.set);
//        set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                GPSTracker gps = null;
//                lat1 = DonorReg.latitude;
//                lng1 = DonorReg.longitude;
//
//
//                final String Latitude = String.valueOf(lat1);
//                final String Longitude = String.valueOf(lng1);
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(MapsActivity.this, response, Toast.LENGTH_LONG).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MapsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//
//                        params.put(KEY_LATITUDE, Latitude);
//                        params.put(KEY_LONGITUDE, Longitude);
//                        return params;
//
//                    }
//
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
//                requestQueue.add(stringRequest);
//                Intent intent = new Intent(MapsActivity.this, DonorReg.class);
//                startActivity(intent);
//
//            }
//        });

        Button cancel = (Button) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MapsActivity.this, "User press Cancel Button", Toast.LENGTH_SHORT).show();
//                sp2.edit().putString("lat","").commit();
//                sp2.edit().putString("lng","").commit();
                Intent intent = new Intent(MapsActivity.this, DonorReg.class);
                startActivity(intent);
            }
        });
    }


    protected void search(List<Address> addresses) {

        Log.e("Hello", "Bingo");

        mMap.clear();
        Address address = (Address) addresses.get(0);
        final double home_long = address.getLongitude();
        final double home_lat = address.getLatitude();
        LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());

        String addressText = String.format("%s, %s", address.getMaxAddressLineIndex() > 0 ? address.getAddressLine(0) : "", address.getCountryName());
        mMap.clear();
        MarkerOptions markerOptions = new MarkerOptions();
        Log.e("Address", "" + addressText);
        markerOptions.position(latLng);
        markerOptions.title(addressText);
        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        Button set = (Button) findViewById(R.id.set);
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String g1 = searchView.getText().toString();
                // Toast.makeText(MapsActivity.this, "Selected Location is - \nLat: "
                //       + home_lat + "\nLong: " + home_long, Toast.LENGTH_LONG).show();
                Geocoder geocoder=new Geocoder(MapsActivity.this);
                try{
                    ArrayList<Address> addresses1=(ArrayList<Address>) geocoder.getFromLocationName(g1,50);

                    for(Address add:addresses1){
                        if(g1!=null){
                            double longitude=add.getLongitude();
                            double latitude=add.getLatitude();
                            Toast.makeText(MapsActivity.this, "Latitude= "+latitude+"Longitude="+longitude, Toast.LENGTH_SHORT).show();


                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

//                Intent int1=new Intent(MainActivity.this,NextActivity.class);
//                int1.putExtra("data",latit);
//                startActivity(int1);



//                StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                Toast.makeText(MapsActivity.this, response, Toast.LENGTH_LONG).show();
//                            }
//                        }, new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MapsActivity.this, error.toString(), Toast.LENGTH_LONG).show();
//                    }
//                }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//
//                          params.put(KEY_LATITUDE, latitude);
//                          params.put(KEY_LONGITUDE, longitude);
//                        return params;
//
//                    }
//
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(MapsActivity.this);
//                requestQueue.add(stringRequest);
//            }

            Intent intent = new Intent(MapsActivity.this, DonorReg.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if (location != null) {
            Toast.makeText(this, "GPS location was found!", Toast.LENGTH_SHORT).show();
            LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());


            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(latLng);
            markerOptions.title("Current Position");
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
            mMap.addMarker(markerOptions);


            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 18);
            mMap.animateCamera(cameraUpdate);
        } else {
            Toast.makeText(this, "Current location was null, enable GPS on emulator!", Toast.LENGTH_SHORT).show();
        }
        startLocationUpdates();
    }

    protected void startLocationUpdates() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Position");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
        mMap.addMarker(markerOptions);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

