package com.example.raul_will.travellivesupport;

import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {


        googleMap.moveCamera(CameraUpdateFactory.newLatLng(ParserTask.allpoints.get(0)));

        PolylineOptions options= new PolylineOptions();
        options.addAll(ParserTask.allpoints)
                .width(5)
                .color(Color.RED);

        googleMap.addPolyline(options);
        googleMap.setMinZoomPreference(6.0f);
        googleMap.setMaxZoomPreference(14.0f);


    }


}
