package com.myappdeport.view.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.google.common.util.concurrent.ForwardingListeningExecutorService;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.IPositionRepository;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.firebase.ActivityFireStoreRepository;
import com.myappdeport.repository.firebase.PositionFireStoreRepository;
import com.myappdeport.repository.firebase.RouteFireStoreRepository;
import com.myappdeport.repository.room.PositionRoomRepository;
import com.myappdeport.viewmodel.AuthViewModel;

import java.util.Collections;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private AuthViewModel authViewModel;
    private Button iniciarSesion;
    private Button registrate;
    private Button usuarioAnonimo;


    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarSesion = (Button) findViewById(R.id.login);
        registrate = (Button) findViewById(R.id.registrate);
        usuarioAnonimo = (Button) findViewById(R.id.anonimo);

        //authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIniciarSesion();
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });
        usuarioAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuContainer();
            }
        });
    }

    public void openIniciarSesion() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
    //Metodo granulometria


    public void openDeportActivity() {
        Intent intent = new Intent(this, DeportActivity.class);
        startActivity(intent);
    }

    public void openMenuContainer() {
        Intent intent = new Intent(this, MenuContainer.class);
        startActivity(intent);
    }
/*
    @Override
    protected  void onStart(){
        super.onStart();
        GoogleSignInAccount googleSignInAccount =  GoogleSignIn.getLastSignedInAccount(this);
        if (googleSignInAccount != null) {
            getGoogleAuthCredential(googleSignInAccount);
        }
        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
                if (oldAccessToken != null) {
                    handleFacebookToken(oldAccessToken);
                }
            }
        };
        //FacebookAuthProvider
        //FacebookAuthCredential.
        //Face

    }
    private void handleFacebookToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        signInWithAuthCredential(credential);
    }
    private void getGoogleAuthCredential(GoogleSignInAccount googleSignInAccount) {
        String googleTokenId = googleSignInAccount.getIdToken();
        AuthCredential googleAuthCredential = GoogleAuthProvider.getCredential(googleTokenId, null);
        signInWithAuthCredential(googleAuthCredential);
    }

    private void signInWithAuthCredential(AuthCredential googleAuthCredential) {
        authViewModel.signIn(googleAuthCredential);
        authViewModel.authenticatedUserLiveData.observe(this, authenticatedUser -> {
            if (authenticatedUser.isNew) {
                createNewUser(authenticatedUser);
            } else {
                goToMainActivity(authenticatedUser);
            }
        });
    }
    private void createNewUser(EUser authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                toastMessage(user.name);
            }
            goToMainActivity(user);
        });
    }
    private void toastMessage(String name) {
        Toast.makeText(this, "Hi " + name + "!\n" + "Your account was successfully created.", Toast.LENGTH_LONG).show();
    }
    private void goToMainActivity(EUser user) {
        Intent intent = new Intent(MainActivity.this, MenuContainer.class);
        intent.putExtra(USER, user);
        startActivity(intent);
        finish();
    }*/

}