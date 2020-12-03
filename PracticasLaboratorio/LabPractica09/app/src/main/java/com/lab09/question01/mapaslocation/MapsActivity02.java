package com.lab09.question01.mapaslocation;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.lab09.question01.mapaslocation.lbs.LocationTrack;

public class MapsActivity02 extends FragmentActivity implements OnMapReadyCallback {


    private static final int REQUEST_CODE_PERMISSION = 2;
    String mPermission = Manifest.permission.ACCESS_FINE_LOCATION;
    LocationTrack gps;
    private Double latitude;
    private Double longitude;
    public static LatLng sydney;
    public static GoogleMap mMap;
    public static int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps02);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        latitude = -34d;
        longitude= 150d;
        count=0;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        try {
            if (ActivityCompat.checkSelfPermission(this, mPermission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{mPermission},
                        REQUEST_CODE_PERMISSION);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            gps = new LocationTrack(MapsActivity02.this);

            if (gps.canGetLocation()) {
                latitude = gps.getLatitude();
                longitude = gps.getLongitude();
                LatLng latLng = new LatLng(latitude, longitude);
                float zoomLevel = 16.0f; //This goes up to 21
                mMap.getMaxZoomLevel();
                //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoomLevel));
                //mMap.animateCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom - 0.5f));
                Toast.makeText(getApplicationContext(), "This is your Location : \nLatitude: " + latitude + "\nLongitude: " + longitude, Toast.LENGTH_SHORT).show();
            }
            else {
                gps.showSettings();
            }
        }catch (Exception e){

        }
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
        // Add a marker in Sydney and move the camera
        /*
        sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker lbs"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
*       */
    }
    public static void changeMarker(double latitude, double longitude){
        sydney = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title(""+count));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        float zoomLevel = 16.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
        count++;

    }
}