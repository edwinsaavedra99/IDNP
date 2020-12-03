package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.repository.firebase.AuthRepository;

public class AuthViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    public LiveData<EUserEDWIN> authenticatedUserLiveData;
    public LiveData<EUserEDWIN> createdUserLiveData;
    public LiveData<EUserEDWIN>  createdUserEmailLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
    }

    public void signIn(AuthCredential uiAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignIn(uiAuthCredential);
    }

    public void createUser(EUserEDWIN authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }

    public void createUserEmail(EUserEDWIN eUserEDWIN){
        createdUserEmailLiveData = authRepository.createUserWithEmailAndPassword(eUserEDWIN);
    }
}