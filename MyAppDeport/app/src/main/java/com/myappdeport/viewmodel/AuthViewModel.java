package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.myappdeport.model.entity.database.EUserEDWIN;
import com.myappdeport.repository.firebase.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public LiveData<EUserEDWIN> authenticatedUserLiveData;
    public LiveData<EUserEDWIN> createdUserLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    public void signIn(AuthCredential uiAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignIn(uiAuthCredential);
    }
    /*public void signInWithFacebook(AuthCredential facebookAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignIn(facebookAuthCredential);
    }*/
    public void createUser(EUserEDWIN authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }
}