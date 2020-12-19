package com.myappdeport.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GestureDetectorCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Chronometer;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myappdeport.R;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.utils.Constants;
import com.myappdeport.utils.onFragmentBtnSelected;
import com.myappdeport.view.fragments.*;

import static com.myappdeport.utils.Constants.USER;

public class MenuContainer extends AppCompatActivity implements onFragmentBtnSelected {

    private static final String TAG = "MenuContainer";
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private static final int PERMISSION_REQUEST_LOCATION = 2;
    EatingTips eatingTips = new EatingTips();
    MusicPlayer musicPlayer = new MusicPlayer();
    Statics statics = new Statics();
    Profile profile;
    MapsFragment maps = new MapsFragment();
    MapsFragment maps2 = new MapsFragment();
    LinearLayout linearLayoutBlock;
    private GestureDetectorCompat gestureDetectorCompat;
    private boolean flag;
    public static Chronometer chronometerExt;


    private Fragment currentFragment;
    private Bundle args;
    BottomNavigationView bottomNavigationView;
    ImageButton configuration;
    ImageButton locking;
    TextView title;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_container);
        Intent recibir = getIntent();
        EUserEDWIN datos = (EUserEDWIN) recibir.getSerializableExtra(USER);

        // Creamos un nuevo Bundle
        args = new Bundle();
        args.putSerializable(USER, datos);
        profile = new Profile();
        profile.setArguments(args);
        linearLayoutBlock = findViewById(R.id.lock_screen_hide);
            linearLayoutBlock.setVisibility(View.GONE);
        locking = findViewById(R.id.lock);
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureListener());
        chronometerExt = findViewById(R.id.lock_cronometer);

        flag = false;
        title = findViewById(R.id.textView_Main_title);
        frameLayout = findViewById(R.id.container);

        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        bottomNavigationView.setSelectedItemId(R.id.item3);

        configuration = findViewById(R.id.conf);
        configuration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openConfiguration();
            }
        });
        locking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LockScreen();
            }
        });
        //loadFragment(maps);
        //currentFragment = maps;
        //loadFragment(maps,Constants.TAG_F_MAP);

        permits();
        permitsMaps();
    }

    private void LockScreen() {
        bottomNavigationView.setVisibility(View.GONE);
        locking.setVisibility(View.GONE);
        title.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        configuration.setVisibility(View.GONE);
        linearLayoutBlock.setVisibility(View.VISIBLE);
        flag=true;
    }

    private void InLockScreen() {
        linearLayoutBlock.setVisibility(View.GONE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        locking.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.VISIBLE);
        configuration.setVisibility(View.VISIBLE);
        flag=false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Fragment fragment = profile;
        fragment.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


    private void openConfiguration() {
        Intent intent = new Intent(this, Configuration.class);
        startActivity(intent);
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    //currentFragment = eatingTips;
                    Log.e(TAG, "Eating Tips");
                    loadFragment(eatingTips, Constants.TAG_F_NUTRITION);
                    return true;
                case R.id.item2:
                    //currentFragment = musicPlayer;
                    Log.e(TAG, "Music Player");
                    loadFragment(musicPlayer, Constants.TAG_F_MUSIC);
                    return true;
                case R.id.item3:
                    //currentFragment = maps;
                    Log.e(TAG, "Map");
                    loadFragment(maps2, Constants.TAG_F_MAP);
                    return true;
                case R.id.item4:
                    //currentFragment = statics;
                    Log.e(TAG, "Statistics");
                    loadFragment(statics, Constants.TAG_F_STATISTIC);
                    return true;
                case R.id.item5:
                    //currentFragment = profile;
                    Log.e(TAG, "Profile");
                    loadFragment(profile, Constants.TAG_F_PROFILE);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment, String tag) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);
        if (fragment.isAdded()) {
            if (currentFragment != null)
                transaction.hide(currentFragment).show(fragment);
        } else {
            if (currentFragment != null)
                transaction.hide(currentFragment).add(R.id.container, fragment, tag);
            else
                transaction.add(R.id.container, fragment, tag);
        }
        //transaction.replace(R.id.container, fragment);
        //transaction.addToBackStack(null);
        transaction.commit();
        currentFragment = fragment;
    }

    public void permits() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) { // Permits ready
                display();
                Log.println(Log.INFO, TAG, "API >= 23");
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) // Message of error
                    Toast.makeText(getApplicationContext(), "External storage and camera permission required to read media", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE); // Callback for permits
            }
        } else {
            Log.println(Log.INFO, TAG, "API < 23");
            display();
        }
    }

    public void permitsMaps() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) { // Permits ready
                display();
                Log.println(Log.INFO, TAG, "API >= 23");
            } else {
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) // Message of error
                    Toast.makeText(getApplicationContext(), "GPS required permits", Toast.LENGTH_SHORT).show();
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_LOCATION); // Callback for permits
            }
        } else {
            Log.println(Log.INFO, TAG, "API < 23");
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
        } else if (requestCode == PERMISSION_REQUEST_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "External location permission", Toast.LENGTH_SHORT).show();
                display();
            } else {
                Toast.makeText(getApplicationContext(), "External location permission has not been granted, cannot use gps", Toast.LENGTH_SHORT).show();
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    void display() {
        //get repository
    }

    @Override
    public void onButtonSelected(Fragment fragment) {
        loadFragment(fragment,"ISF");
    }

    private  class  GestureListener  extends GestureDetector.SimpleOnGestureListener {
         @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(flag){
                InLockScreen();
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
        }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}