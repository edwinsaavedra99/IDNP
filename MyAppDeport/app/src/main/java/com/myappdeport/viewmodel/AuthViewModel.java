package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.google.firebase.auth.AuthCredential;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.repository.IAuthRepository;
import com.myappdeport.repository.firebase.AuthRepository;
import com.myappdeport.repository.room.AuthRoomRepository;
import com.myappdeport.repository.room.UserRoomRepository;

import java.util.Objects;

public class AuthViewModel extends AndroidViewModel {
    private final AuthRepository authRepository;
    private final IAuthRepository iAuthRepository;
    public LiveData<EUserEDWIN> authenticatedUserLiveData;
    public LiveData<EUserEDWIN> createdUserLiveData;
    public LiveData<EUserEDWIN>  createdUserEmailLiveData;
    public LiveData<EUserEDWIN>  userEDWINLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = AuthRepository.getInstance();
        iAuthRepository = AuthRoomRepository.getInstance(application);
    }

    public void signIn(AuthCredential uiAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignIn(uiAuthCredential);
    }

    public void createUser(EUserEDWIN authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
        if(!Objects.requireNonNull(createdUserLiveData.getValue()).isError){
            EUser I = new EUser(createdUserLiveData.getValue().name,createdUserLiveData.getValue().email,true,true,true);
            I.setDocumentId(createdUserLiveData.getValue().uid);
            iAuthRepository.register(I);
        }
    }

    public void createUserEmail(EUserEDWIN eUserEDWIN){
        createdUserEmailLiveData = authRepository.createUserWithEmailAndPassword(eUserEDWIN);
/*        if(!Objects.requireNonNull(createdUserEmailLiveData.getValue()).isError){
            EUser I = new EUser(createdUserEmailLiveData.getValue().name,createdUserEmailLiveData.getValue().email,true,true,true);
            I.setDocumentId(createdUserEmailLiveData.getValue().uid);
            iAuthRepository.register(I);
        }*/
    }

    public void cerrarSesion(){
        authRepository.cerrarSesion();
    }

    public void userLogin(){
        userEDWINLiveData = authRepository.userLogin();
    }
}