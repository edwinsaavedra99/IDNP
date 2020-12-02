package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.repository.firebase.SplashRepository;

//import ro.alexmamo.firebaseauthapp.auth.User;

public class SplashViewModel extends AndroidViewModel {
    private SplashRepository splashRepository;
    public LiveData<EUserEDWIN> isUserAuthenticatedLiveData;
    public LiveData<EUserEDWIN> userLiveData;

    public SplashViewModel(Application application) {
        super(application);
        splashRepository = new SplashRepository();
    }

    public void checkIfUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkIfUserIsAuthenticatedInFirebase();
    }

    public void setUid(String uid) {
        userLiveData = splashRepository.addUserToLiveData(uid);
    }
}