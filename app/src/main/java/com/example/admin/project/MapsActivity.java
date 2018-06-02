package com.example.admin.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> arrayPoints = new ArrayList<LatLng>();
    private ArrayList<String> arrayNames = new ArrayList<>();
    private ArrayList<String> arrayDates = new ArrayList<>();
    PolylineOptions polylineOptions = new PolylineOptions();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        polylineOptions.color(Color.RED);
        polylineOptions.width(5);

        Intent intent = getIntent();
        arrayPoints = (ArrayList<LatLng>)intent.getSerializableExtra("point");
        arrayNames = (ArrayList<String>)intent.getSerializableExtra("name");
        arrayDates = (ArrayList<String>)intent.getSerializableExtra("date");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MarkerOptions markerOptions = new MarkerOptions();
        if(arrayPoints.size() != 0) {
            for (int i = 0; i < arrayPoints.size(); ++i) {
                markerOptions.position(arrayPoints.get(i)).title("(" + arrayDates.get(i) +")" + "(" + arrayNames.get(i) + ")");
                mMap.addMarker(markerOptions);


            }
            polylineOptions.addAll(arrayPoints);
            mMap.addPolyline(polylineOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(arrayPoints.get(0),16));
        }
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }


}
