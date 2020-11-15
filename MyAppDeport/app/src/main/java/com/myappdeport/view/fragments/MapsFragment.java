package com.myappdeport.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Chronometer;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.myappdeport.R;
import com.myappdeport.service.usecase.ChronometerUseCase;
import com.myappdeport.service.usecase.interfaces.TimerInterface;

import java.util.Map;

public class MapsFragment extends Fragment implements TimerInterface.TimerInterfaceView {

    GoogleMap map;
    private Chronometer chronometer;
    private TimerInterface.TimerInterfaceUseCase mUCTimer;
    private FloatingActionButton btnStart;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {


            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            map = googleMap;
            map.setMyLocationEnabled(true);
            //map.animateCamera( CameraUpdateFactory.zoomTo( 12 ) );
            //LatLng sydney = new LatLng(-34, 151);
            //googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            //googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_maps, container, false);
        btnStart = viewGroup.findViewById(R.id.floatingActionButtonStartActivity);
        chronometer = viewGroup.findViewById(R.id.timeChr);
        initView();
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
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
            }
        });
    }

    @Override
    public void setViewData(boolean flag) {

    }
}