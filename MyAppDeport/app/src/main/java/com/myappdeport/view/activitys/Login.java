package com.myappdeport.view.activitys;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.myappdeport.R;
import com.myappdeport.model.entity.database.EUserEDWIN;
import com.myappdeport.viewmodel.AuthViewModel;

import java.io.InputStream;
import java.net.URL;

import lombok.SneakyThrows;

import static android.os.FileUtils.copy;
import static com.myappdeport.utils.Constants.RC_SIGN_IN;
import static com.myappdeport.utils.Constants.USER;
import static com.myappdeport.utils.HelperClass.logErrorMessage;

//import com.facebook.FacebookSdk;
//import com.facebook.appevents.AppEventsLogger;

public class Login extends AppCompatActivity {
    private static final int IO_BUFFER_SIZE = 10;
    private AuthViewModel authViewModel;
    private GoogleSignInClient googleSignInClient;
    //private String TAG = "Login";
    private CallbackManager callbackManager;
    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialComponents();
    }
    private void initialComponents(){
        initSignInButton();
        initAuthViewModel();
        initGoogleSignInClient();
    }

    private void initSignInButton() {
        Button googleSignInButton = findViewById(R.id.sign_button);
        Button facebook = findViewById(R.id.login_button_facebook);
        LoginButton loginButton = findViewById(R.id.login_button_facebook_gone);
        facebook.setOnClickListener(v -> loginButton.performClick());
        googleSignInButton.setOnClickListener(v -> signIn());
        //auth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions("email","public_profile");
        signInFb(loginButton);
        
    }



    private void handleFacebookToken(AccessToken token){
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        signInWithAuthCredential(credential);
    }

    private void initAuthViewModel() {
        authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
    }

    private void initGoogleSignInClient() {
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);
    }

    @Override
    protected  void onStart(){
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
        super.onStart();
        //FacebookAuthProvider
        //FacebookAuthCredential.
        //Face

    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void signInFb(LoginButton loginButton) {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {}
            @Override
            public void onError(FacebookException error) {}
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        try{
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }catch (Exception e){
            Toast.makeText(this, "Hi, you don't have Internet.", Toast.LENGTH_LONG).show();
            System.out.println(e);
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount googleSignInAccount = task.getResult(ApiException.class);
                if (googleSignInAccount != null) {
                    getGoogleAuthCredential(googleSignInAccount);
                }
            } catch (ApiException e) {
                logErrorMessage(e.getMessage());
                Toast.makeText(this, "Hi, you don't have Internet.", Toast.LENGTH_LONG).show();
            }
        }
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
    private void createNewUser(EUserEDWIN authenticatedUser) {
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
    private void goToMainActivity(EUserEDWIN user) {
        Intent intent = new Intent(Login.this, MenuContainer.class);
        intent.putExtra(USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}