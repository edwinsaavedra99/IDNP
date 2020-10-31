package com.myappdeport.view.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.myappdeport.R;
import com.myappdeport.view.fragments.EatingTips;
import com.myappdeport.view.fragments.MapsFragment;
import com.myappdeport.view.fragments.MusicPlayer;
import com.myappdeport.view.fragments.Profile;
import com.myappdeport.view.fragments.Statics;

public class MenuContainer extends AppCompatActivity {

    EatingTips eatingTips = new EatingTips();
    MusicPlayer musicPlayer = new MusicPlayer();
    Statics statics = new Statics();
    Profile profile = new Profile();
    MapsFragment maps = new MapsFragment();

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_container);

        bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        loadFragment(maps);

        bottomNavigationView.setSelectedItemId(R.id.item3);

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @SuppressLint("NonConstantResourceId")
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.item1:
                    loadFragment(eatingTips);
                    return true;
                case R.id.item2:
                    loadFragment(musicPlayer);
                    return true;
                case R.id.item3:
                    loadFragment(maps);
                    return true;
                case R.id.item4:
                    loadFragment(statics);
                    return true;
                case R.id.item5:
                    loadFragment(profile);
                    return true;
            }
            return false;
        }
    };

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.commit();
    }
}