package com.huyphan.SmartHome;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends AppCompatActivity implements OnMapReadyCallback {
    String title, address;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        Intent intent = getIntent();
        latitude = intent.getDoubleExtra("latitude", 0d);
        longitude = intent.getDoubleExtra("longitute",0d);
        title = intent.getStringExtra("name");
        address = intent.getStringExtra("address");


        //Log.e("place",latlngss);

        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    public void onMapReady(GoogleMap map){
        LatLng sydney = new LatLng(latitude,longitude);


        map.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 13));
        map.addMarker( new MarkerOptions()
                .title(title)
                .snippet(address)
                .position(sydney));
    }

    public void moveBackToSettings(View view){
        Intent moveBackToSettings =  new Intent(this, SettingsActivity.class);
        startActivity(moveBackToSettings);
    }
}