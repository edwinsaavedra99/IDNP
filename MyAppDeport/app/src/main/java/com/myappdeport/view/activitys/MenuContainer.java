package com.myappdeport.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myappdeport.R;
import com.myappdeport.utils.Constants;
import com.myappdeport.view.fragments.EatingTips;
import com.myappdeport.view.fragments.MapsFragment;
import com.myappdeport.view.fragments.MusicPlayer;
import com.myappdeport.view.fragments.Profile;
import com.myappdeport.view.fragments.Statics;

public class MenuContainer extends AppCompatActivity {

    private static final String TAG = "MenuContainer";
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_REQUEST_LOCATION = 2;
    EatingTips eatingTips = new EatingTips();
    MusicPlayer musicPlayer = new MusicPlayer();
    Statics statics = new Statics();
    Profile profile = new Profile();
    MapsFragment maps = new MapsFragment();
    MapsFragment maps2 = new MapsFragment();

    private Fragment currentFragment;

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_container);

        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        bottomNavigationView.setSelectedItemId(R.id.item3);
        //loadFragment(maps);
        //currentFragment = maps;
        //loadFragment(maps,Constants.TAG_F_MAP);
        permits();
        permitsMaps();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    //currentFragment = eatingTips;
                    loadFragment(eatingTips,Constants.TAG_F_NUTRITION);
                    return true;
                case R.id.item2:
                    //currentFragment = musicPlayer;
                    loadFragment(musicPlayer,Constants.TAG_F_MUSIC);
                    return true;
                case R.id.item3:
                    //currentFragment = maps;
                    loadFragment(maps2,Constants.TAG_F_MAP);
                    return true;
                case R.id.item4:
                    //currentFragment = statics;
                    loadFragment(statics,Constants.TAG_F_STATISTIC);
                    return true;
                case R.id.item5:
                    //currentFragment = profile;
                    loadFragment(profile,Constants.TAG_F_PROFILE);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment,String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if(fragment.isAdded()){
            if(currentFragment != null)
                transaction.hide(currentFragment).show(fragment);
        }else{
            if(currentFragment !=null)
                transaction.hide(currentFragment).add(R.id.container,fragment, tag);
            else
                transaction.add(R.id.container,fragment, tag);
        }
        //transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
        currentFragment = fragment;
    }

    public void permits(){
        if (Build.VERSION.SDK_INT >= 23){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){ // Permits ready
                display();
                Log.println(Log.INFO,TAG,"API >= 23");
            } else{
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) // Message of error
                    Toast.makeText(getApplicationContext(), "External storage and camera permission required to read media", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE); // Callback for permits
            }
        } else {
            Log.println(Log.INFO,TAG,"API < 23");
            display();
        }
    }

    public void permitsMaps(){
        if (Build.VERSION.SDK_INT >= 23){
            if(ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){ // Permits ready
                display();
                Log.println(Log.INFO,TAG,"API >= 23");
            } else{
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) // Message of error
                    Toast.makeText(getApplicationContext(), "GPS required permits", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION); // Callback for permits
            }
        } else {
            Log.println(Log.INFO,TAG,"API < 23");
            display();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "External read permission", Toast.LENGTH_SHORT).show();
                display();
            } else {
                Toast.makeText(getApplicationContext(), "External read permission has not been granted, cannot open media", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == PERMISSION_REQUEST_LOCATION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "External location permission", Toast.LENGTH_SHORT).show();
                display();
            } else {
                Toast.makeText(getApplicationContext(), "External location permission has not been granted, cannot use gps", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }
    void display(){
        //get repository
    }
}