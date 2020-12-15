package com.myappdeport.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.myappdeport.R;
import com.myappdeport.service.usecase.interfaces.TimerInterface;
import com.myappdeport.service.usecase.ChronometerUseCase;

public class MainFragmentDeport extends Fragment implements TimerInterface.TimerInterfaceView {
    private Button btnStart,btnPause,btnStop;
    private Chronometer chronometer;
    private TimerInterface.TimerInterfaceUseCase mUCTimer;


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
            LatLng sydney = new LatLng(-34, 151);
            googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_main_deport, container, false);
        btnStart = viewGroup.findViewById(R.id.btnStart);
        btnPause = viewGroup.findViewById(R.id.btnPause);
        btnStop = viewGroup.findViewById(R.id.btnStop);
        chronometer = viewGroup.findViewById(R.id.valueTimer);
        initView();
        return viewGroup;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    @Override
    public void initView() {
        //chronometer.setFormat("Time: %"); --> este es un formato alternativo
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
        /*TextView dsa
        mUCTimer = new ChronometerUseCase(this,chronometer,new TextView());
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCTimer.startChronometer();
                btnStart.setVisibility(View.GONE);
                btnPause.setVisibility(View.VISIBLE);
                btnStop.setVisibility(View.VISIBLE);
            }
        });*/
        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCTimer.pauseChronometer();
            }
        });
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUCTimer.stopChronometer();
                btnStart.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
                btnStop.setVisibility(View.GONE);
                btnPause.setText("PAUSE");
            }
        });
    }

    @Override
    public void setViewData(boolean flag) {
        if(flag){
            btnPause.setText("PAUSE");
        }else{
            btnPause.setText("CONTINUE");
        }
    }
}