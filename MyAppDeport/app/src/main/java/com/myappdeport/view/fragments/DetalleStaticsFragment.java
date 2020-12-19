package com.myappdeport.view.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.appcompat.widget.ContentFrameLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EUser;

import java.util.List;

public class DetalleStaticsFragment extends Fragment implements OnMapReadyCallback {
    //Transacciones
    private View view;
    private EActivity eActivity;
    private GoogleMap googleMap;
    private LocationListener locationListener;
    LatLng latLng;
    List<LatLng> latLngList;

    // widgets
    private TextView textViewStartTime;
    private TextView textViewEndTime;
    private TextView textViewDistncia;
    private TextView textViewK;
    private TextView textViewDate;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public DetalleStaticsFragment() {
    }

    public DetalleStaticsFragment(EActivity eActivity) {
        this.eActivity = eActivity;
        System.out.println("holaaaaaaaaaaaaaa" + eActivity.getDate());
    }

    public static DetalleStaticsFragment newInstance(String param1, String param2) {
        DetalleStaticsFragment fragment = new DetalleStaticsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_detalle_statics, container, false);
        initComponents();
        initActovity();
        return view;
    }

    private void initComponents() {
        textViewStartTime = view.findViewById(R.id.txtview_actividadDetalle_starttime);
        textViewEndTime = view.findViewById(R.id.txtview_actividadDetalle_endtime);
        textViewK = view.findViewById(R.id.txtview_actividadDetalle_kilocalories);
        textViewDistncia = view.findViewById(R.id.txtview_actividadDetalle_distanciaRecorrida);
        textViewDate = view.findViewById(R.id.txtview_actividadDetalle_date);
    }

    private void initActovity() {
        try {
            textViewStartTime.setText(eActivity.getStartTime());
            textViewEndTime.setText(eActivity.getEndTime());
            textViewK.setText(eActivity.getKiloCalories() + "");
            textViewDate.setText(eActivity.getDate());
            Double totalDistance = 0d;
            for (int i = 0; i < eActivity.getERoute().getPositions().size(); i++) {
                totalDistance += eActivity.getERoute().getPositions().get(i).getDistance();
            }
            textViewDistncia.setText(totalDistance + "");
        } catch (Exception e) {
            System.out.println("vizacarraaaa");
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        this.googleMap.setMyLocationEnabled(true);
        this.googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                latLng = new LatLng(location.getLatitude(),location.getLongitude());
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            @Override
            public void onProviderEnabled(String provider) {  }

            @Override
            public void onProviderDisabled(String provider) { }

        };
        googleMap.addPolyline((new PolylineOptions()).color(Color.argb(255,186,74,0)).width(10).addAll(latLngList));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        CameraPosition cameraPosition =  new CameraPosition.Builder().target(latLng).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    private  void initPoliline(){

    }

}