package com.lab09.question01.mapaslocation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

public class MapsActivity01 extends FragmentActivity implements OnMapReadyCallback {


    //private final Handler handler = new Handler();
    //private boolean isNetworkLocation, isGPSLocation;
    /**
     * FusedLocationProviderApi Save request parameters
     */
    //private LocationRequest mLocationRequest;


    /**
     * Provide callbacks for location events.
     */
    //private LocationCallback mLocationCallback;

    /**
     * An object representing the current location
     */
    //private Location mCurrentLocation;

    //A client that handles connection / connection failures for Google locations
    // (changed from play-services 11.0.0)
    //private FusedLocationProviderClient mFusedLocationClient;

    //private String provider;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps01);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
        findViewById(R.id.startLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkMyPermissionLocation();
            }
        });
        findViewById(R.id.stopLocation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopLocationSerivce();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setZoomControlsEnabled(true);
        LocationServices.mMap = mMap;
    }


    private void checkMyPermissionLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //Permission Check
            PermissionUtils.requestPermission(this);
        } else {
            //If you're authorized, start setting your location
            //initGoogleMapLocation();
            startLocationService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        //If the request code does not match
        if (requestCode != PermissionUtils.REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION}, grantResults)) {
            //If you have permission, go to the code to get the location value
            //initGoogleMapLocation();
            startLocationService();
        } else {
            Toast.makeText(this, "Stop apps without permission to use location information", Toast.LENGTH_SHORT).show();
            //finish();
        }
    }

    /**
     * Remove location information
     */
    @Override
    public void onStop() {
        super.onStop();
        /*if (mFusedLocationClient != null) {
            mFusedLocationClient.removeLocationUpdates(mLocationCallback);
        }*/
    }

    private void startLocationService(){
        if(!LocationServices.isMyServiceRunning){
            Intent intent = new Intent(getApplicationContext(),LocationServices.class);
            intent.setAction(Constants.ACTION_START_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"Location service started",Toast.LENGTH_SHORT).show();
        }
    }
    private void stopLocationSerivce(){
        if(LocationServices.isMyServiceRunning){
            Intent intent = new Intent(getApplicationContext(),LocationServices.class);
            intent.setAction(Constants.ACTION_STOP_LOCATION_SERVICE);
            startService(intent);
            Toast.makeText(this,"Location service stopped",Toast.LENGTH_SHORT).show();
        }
    }

}