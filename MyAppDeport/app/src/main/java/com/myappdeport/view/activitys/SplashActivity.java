package com.myappdeport.view.activitys;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.viewmodel.SplashViewModel;

import static com.myappdeport.utils.Constants.USER;

public class SplashActivity extends AppCompatActivity {
    SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initSplashViewModel();
        checkIfUserIsAuthenticated();
    }

    private void initSplashViewModel() {
        splashViewModel = new ViewModelProvider(this).get(SplashViewModel.class);
    }

    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkIfUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, user -> {
            if (!user.isAuthenticated) {
                goToAuthInActivity();
                finish();
            } else {
                getUserFromDatabase(user.uid);
            }
        });
    }

    private void goToAuthInActivity() {
        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
        startActivity(intent);
    }

    private void getUserFromDatabase(String uid) {
        splashViewModel.setUid(uid);
        splashViewModel.userLiveData.observe(this, user -> {
            goToMainActivity(user);
            finish();
        });
    }

    private void goToMainActivity(EUserEDWIN user) {
        Intent intent = new Intent(SplashActivity.this, MenuContainer.class);
        intent.putExtra(USER, user);
        startActivity(intent);
    }
}
