package com.myappdeport.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myappdeport.R;
import com.myappdeport.service.usecase.ChronometerUseCase;
import com.myappdeport.service.usecase.interfaces.TimerInterface;
import com.myappdeport.utils.ParseMetrics;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapsFragment extends Fragment implements TimerInterface.TimerInterfaceView,OnMapReadyCallback {

    GoogleMap map;
    private Chronometer chronometer;
    private TextView textView;
    private TimerInterface.TimerInterfaceUseCase mUCTimer;
    private FloatingActionButton btnStart;
    private LocationManager locationManager;
    private Location location;
    private LatLng previoL;
    private List<LatLng> latLngList = new ArrayList<>();
    private boolean flag = false;
        @Override
        public void onMapReady(GoogleMap googleMap) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            map = googleMap;
            map.setMyLocationEnabled(true);
            map.getUiSettings().setMyLocationButtonEnabled(false);
            getContext();
            LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            LocationListener locationListener = new LocationListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    //map.addMarker(new MarkerOptions().position(latLng).title("Mi posicion"));
                    if(flag) {
                        latLngList.add(latLng);
                        map.addPolyline((new PolylineOptions()).clickable(true).color(Color.LTGRAY).addAll(latLngList));
                        DecimalFormat df = new DecimalFormat("0.00");
                        textView.setText(""+ df.format(ParseMetrics.mtoKm(distaceAll(latLngList))));
                    }
                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                    CameraPosition cameraPosition =  new CameraPosition.Builder().target(latLng).zoom(16)/*.bearing(90).tilt(45)*/.build();
                    map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
                }
                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {
                }
                @Override
                public void onProviderEnabled(String provider) {
                }
                @Override
                public void onProviderDisabled(String provider) {
                }
            };
            int permiso = ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,0,0,locationListener);
        }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup  = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);
        btnStart = viewGroup.findViewById(R.id.floatingActionButtonStartActivity);
        chronometer = viewGroup.findViewById(R.id.timeChr);
        textView = viewGroup.findViewById(R.id.textView20);
        initView();
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void initView() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.setFormat("00:%s");
        //Listener en escuchador de reloj , cambia el formato a hh:mm:ss
        chronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            public void onChronometerTick(Chronometer c) {
                //CODIGO CANDIDATO A IR EN UTILS
                long elapsedMillis = SystemClock.elapsedRealtime() -c.getBase();
                if(elapsedMillis > 3600000L){
                    c.setFormat("0%s");
                }else{
                    c.setFormat("00:%s");
                }
            }
        });
        mUCTimer = new ChronometerUseCase(this,chronometer);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCTimer.startChronometer();
                btnStart.setVisibility(View.GONE);
                flag = true;
            }
        });
    }

    @Override
    public void setViewData(boolean flag) {

    }
    private double distaceAll(List<LatLng> latLngList){
        double sum = 0;
        if (latLngList.size() == 0 || latLngList.size() == 1){
            return sum;
        }
        for (int i = 0; i<latLngList.size() -1; i++){
            Location locationA = new Location("A");
            locationA.setLatitude(latLngList.get(i).latitude);
            locationA.setLongitude(latLngList.get(i).longitude);
            Location locationB = new Location("B");
            locationB.setLatitude(latLngList.get(i+1).latitude);
            locationB.setLongitude(latLngList.get(i+1).longitude);
            sum += locationA.distanceTo(locationB);
        }
        return sum;
    }
}