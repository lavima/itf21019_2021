package com.lavima.after_googlemaps_java;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

public class MapsActivity extends FragmentActivity implements AdapterView.OnItemSelectedListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener, GoogleMap.OnMyLocationClickListener {

    private GoogleMap mMap;
    private final int PERMISSION_LOCATION_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.map_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        setMapUISettings();

        mMap.moveCamera(CameraUpdateFactory.zoomTo(15.0f));

        LatLng hiof = new LatLng(59.12797849, 11.3527286);
        mMap.addMarker(new MarkerOptions().position(hiof).title("Høgskolen i Østfold"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(hiof));


        LatLng fredrikstad = new LatLng(59.21047628, 10.93994737);
        mMap.addMarker(new MarkerOptions().position(fredrikstad).title("Fredrikstad Kino"));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(fredrikstad), 2000, null);
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(PERMISSION_LOCATION_ID)
    private void setMapUISettings() {
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);
        mMap.getUiSettings().setAllGesturesEnabled(true);

        mMap.setOnMyLocationClickListener(this);
        mMap.setOnMyLocationButtonClickListener(this);

        mMap.setOnMapLongClickListener(this);

        if (EasyPermissions.hasPermissions(this, Manifest.permission.ACCESS_FINE_LOCATION))
            mMap.setMyLocationEnabled(true);
        else
            EasyPermissions.requestPermissions(this, "Need location access to show MyLocation", PERMISSION_LOCATION_ID, Manifest.permission.ACCESS_FINE_LOCATION);
    }

    @Override
    public boolean onMyLocationButtonClick() {
        return false;
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {

    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mMap.addMarker(new MarkerOptions().position(latLng).title("Ny Markør").icon(BitmapDescriptorFactory.fromResource(R.drawable.dog_icon_small)));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String typeString =  (String)parent.getItemAtPosition(position);
        Log.i("MapsActivity", typeString + " selected");
        switch (typeString) {
            case "Normal":
                Log.i("MapsActivity", "NORMAL");
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case "Hybrid":
                Log.i("MapsActivity", "HYBRID");
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case "Satellite":
                Log.i("MapsActivity", "SATELLITE");
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case "Terrain":
                Log.i("MapsActivity", "TERRAIN");
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            default:
                Log.i("MapsActivity", "NONE");
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}