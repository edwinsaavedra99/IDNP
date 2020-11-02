package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.firebase.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private AuthRepository authRepository;
    public LiveData<EUser> authenticatedUserLiveData;
    public LiveData<EUser> createdUserLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    public void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }
    public void signInWithFacebook(AuthCredential facebookAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(facebookAuthCredential);
    }
    public void createUser(EUser authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }
}