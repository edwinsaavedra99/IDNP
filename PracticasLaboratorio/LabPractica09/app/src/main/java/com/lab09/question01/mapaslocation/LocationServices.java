package com.lab09.question01.mapaslocation;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class LocationServices extends Service {

    public static boolean isMyServiceRunning = false;
    public static GoogleMap mMap ;
    private static ArrayList<LatLng> posiciones = new ArrayList<>();

    private Location mCurrentLocation;
    private LocationCallback locationCallback =  new LocationCallback(){
        @Override
        public void onLocationResult(LocationResult locationResult){
            super.onLocationResult(locationResult);
            mCurrentLocation = locationResult.getLocations().get(0);
            if (mCurrentLocation != null) {
                Log.e("Location(Lat)==", "" + mCurrentLocation.getLatitude());
                Log.e("Location(Long)==", "" + mCurrentLocation.getLongitude());
            }
            if(mMap !=null){
                LatLng sydney = new LatLng(mCurrentLocation.getLatitude(),mCurrentLocation.getLongitude());
                posiciones.add(sydney);
                mMap.addPolyline(new PolylineOptions().addAll(posiciones));
                //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
                float zoomLevel = 16.0f;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, zoomLevel));
                //mMap.clear();
                //MarkerOptions options = new MarkerOptions();
                //options.position(new LatLng(mCurrentLocation.getLatitude(), mCurrentLocation.getLongitude()));
                //BitmapDescriptor icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                //options.icon(icon);
                //Marker marker = mMap.addMarker(options);
                //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(marker.getPosition(), 17));

            }

        }

    };

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yed implement");
    }

    private void startLocationService() {
        String channelId = "location_notification_channel";
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Intent resultIntent = new Intent();
        PendingIntent pendingIntent = PendingIntent.getActivity(
                getApplicationContext(), 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT
        );
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle("Location Service - Fused");
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentText("Running");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            if (notificationManager != null && notificationManager.getNotificationChannel(channelId) == null) {
                NotificationChannel notificationChannel = new NotificationChannel(channelId, "Location Service", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setDescription("This channel is used by location service");
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(4000);
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this)
                .requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        startForeground(Constants.LOCATION_SERVICES_ID,builder.build());

        isMyServiceRunning = true;
    }

    private void stopLocationService(){

        com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(this)
                .removeLocationUpdates(locationCallback);
        stopForeground(true);
        stopSelf();
        isMyServiceRunning = false;
    }



    @Override
    public int onStartCommand(Intent intent,int flags,int startId){
        if(intent!=null){
            String action = intent.getAction();
            if(action != null){
                if(action.equals(Constants.ACTION_START_LOCATION_SERVICE)){
                    startLocationService();
                }else  if(action.equals(Constants.ACTION_STOP_LOCATION_SERVICE)){
                    stopLocationService();
                }
            }
        }
        return super.onStartCommand(intent,flags,startId);
    }


}
