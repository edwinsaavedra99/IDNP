package com.myappdeport.view.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

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
import android.widget.Toast;

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
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.functional.Activity;
import com.myappdeport.model.entity.functional.Route;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.service.usecase.ChronometerUseCase;
import com.myappdeport.service.usecase.interfaces.TimerInterface;
import com.myappdeport.utils.ParseMetrics;

import com.myappdeport.viewmodel.AuthViewModel;
import com.myappdeport.viewmodel.MainDeportViewModel;
import com.myappdeport.view.activitys.MenuContainer;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import lombok.SneakyThrows;

import static android.view.View.GONE;

public class MapsFragment extends Fragment implements TimerInterface.TimerInterfaceView,OnMapReadyCallback {

    GoogleMap map;
    private Chronometer chronometer,chronometer2;
    private TextView textView,min_dist,distance;
    private TimerInterface.TimerInterfaceUseCase mUCTimer;
    private FloatingActionButton btnStart,floatingMetaButton,floatingPauseButton;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private Location location;
    private LatLng previoL;
    private List<LatLng> latLngList = new ArrayList<>();
    private List<EPosition> ePositions = new ArrayList<>();
    private ERoute route = new ERoute();
    private MainDeportViewModel mainDeportViewModel;
    private boolean flag = false;
    private boolean flagDistance = false;
    private Date objDateinicial = new Date();
    private Date objDateinicia2 = new Date();
    private Date objDateinicia3 = new Date();
    String initialTimeFecha;
    private String initialFecha;
    // L
    private EUserEDWIN datos;
    String id;


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
            //map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(.getLatitude(),location.getLongitude()));
            //CameraPosition cameraPosition =  new CameraPosition.Builder().target(latLng).zoom(16)/*.bearing(90).tilt(45)*/.build();
            //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            locationListener = new LocationListener() {
                @SuppressLint("SetTextI18n")
                @Override
                public void onLocationChanged(Location location) {
                    LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                    //map.addMarker(new MarkerOptions().position(latLng).title("Mi posicion"));
                    if(flag) {
                        try{
                            if(flagDistance || distanceLtnLong(latLngList.get(latLngList.size()-1),latLng)>15 ) {
                                latLngList.add(latLng);
                                flagDistance = false;
                                map.addPolyline((new PolylineOptions()).color(Color.argb(255,186,74,0)).width(10).addAll(latLngList));
                                DecimalFormat df = new DecimalFormat("0.00");
                                textView.setText(df.format(ParseMetrics.mtoKm(distaceAll(latLngList))) +"");
                                distance.setText(df.format(ParseMetrics.mtoKm(distaceAll(latLngList))) +"");
                                min_dist.setText(df.format(distaceAll(latLngList)+""));
                                /*
                                 * llamar para guardar
                                 * */
                                //EPosition ePosition = ;
                                ePositions.add(new EPosition(latLng.latitude,latLng.longitude,distaceAll(latLngList)));
                            }
                        }catch (Exception e){
                            System.out.print("error in activity ");
                        }

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
        floatingMetaButton = viewGroup.findViewById(R.id.floatingMetaButton);
        floatingPauseButton = viewGroup.findViewById(R.id.floatingPauseButton);
        chronometer = viewGroup.findViewById(R.id.timeChr);
        chronometer2 = viewGroup.findViewById(R.id.time);
        textView = viewGroup.findViewById(R.id.textView20);
        min_dist = viewGroup.findViewById(R.id.min_dist);
        distance = viewGroup.findViewById(R.id.distance);
        initAuthViewModel();
        //textViewCrono = viewGroup.findViewById(R.id.timer);
        chronometer2 = viewGroup.findViewById(R.id.time);
        initView();
            //L

        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.userLogin();
        authViewModel.userEDWINLiveData.observe(Objects.requireNonNull(getActivity()), user -> {
            if (user.isAuthenticated) {
                datos = user;
            }
        });
        id ="";
        if (datos == null ){
            id = "testUser";
        }else{
            if(datos.uid!=null) id = datos.uid;
            else id = "testUser";
        }
            //L
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
        String initialFecha;
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

        chronometer2.setBase(SystemClock.elapsedRealtime());

        chronometer2.setFormat("00:%s");
        //Listener en escuchador de reloj , cambia el formato a hh:mm:ss
        chronometer2.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
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
  
        mUCTimer = new ChronometerUseCase(this,chronometer,chronometer2, MenuContainer.chronometerExt);

        btnStart.setOnClickListener(new View.OnClickListener() {
            private Date objDateinicial=new Date();
            @Override
            public void onClick(View v) {
                ePositions.clear();
                mUCTimer.startChronometer();
                btnStart.setVisibility(GONE);
                floatingMetaButton.setVisibility(View.VISIBLE);
                floatingPauseButton.setVisibility(View.VISIBLE);
                flag = true;
                flagDistance = true;
                String strDateFormat2 = "hh: mm: ss a";
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat2);
                initialTimeFecha = objSDF.format(this.objDateinicial);
            }
        });
        floatingMetaButton.setOnClickListener(new View.OnClickListener() {

            private Date objDateinicial=new Date();

            @SneakyThrows
            @Override
            public void onClick(View v) {
                flag = false;
                flagDistance = false;
                floatingMetaButton.setVisibility(GONE);
                floatingPauseButton.setVisibility(GONE);
                btnStart.setVisibility(View.VISIBLE);
                route = new ERoute(Double.valueOf(textView.getText().toString()),123.3,ePositions);
                Toast.makeText(getActivity(),"Su información fue guardada ... ",Toast.LENGTH_SHORT).show();
                mUCTimer.stopChronometer();
                String strDateFormat = "hh: mm: ss a";
                String strDateFormat2 = "hh: mm: ss a";
                String strDateFormat3 = "dd-MMM-aaaa";
                SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
                SimpleDateFormat objSDF2 = new SimpleDateFormat(strDateFormat3);
                System.out.println(objSDF.format(this.objDateinicial));
                System.out.println(objSDF2.format(this.objDateinicial));
                EActivity  eActivity = new EActivity(initialTimeFecha,objSDF.format(this.objDateinicial),
                        12.2,objSDF2.format(this.objDateinicial),"Actividad",
                        "Actividad definida","exclude",
                        id,null,null,route);
               mainDeportViewModel.saveActivity(eActivity);
            }
        });
        floatingPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
                mUCTimer.pauseChronometer();
                //floatingPauseButton.setBackground(R.drawable.play);
            }
        });
    }

    @Override
    public void setViewData(boolean flag) {

    }
    private double distanceLtnLong(LatLng latLng1, LatLng latLng2){
        Location locationA = new Location("A");
        locationA.setLatitude(latLng1.latitude);
        locationA.setLongitude(latLng1.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(latLng2.latitude);
        locationB.setLongitude(latLng2.longitude);
        return locationA.distanceTo(locationB);
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
    private void initAuthViewModel() {
        mainDeportViewModel = new ViewModelProvider(this).get(MainDeportViewModel.class);
    }
}