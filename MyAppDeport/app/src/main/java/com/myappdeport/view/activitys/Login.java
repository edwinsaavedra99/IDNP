package com.myappdeport.view.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import com.facebook.*;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.GoogleAuthProvider;
import com.myappdeport.R;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.viewmodel.AuthViewModel;
import lombok.SneakyThrows;

import static com.myappdeport.utils.Constants.RC_SIGN_IN;
import static com.myappdeport.utils.Constants.USER;
import static com.myappdeport.utils.HelperClass.logErrorMessage;


public class Login extends AppCompatActivity {
    private static final int IO_BUFFER_SIZE = 10;
    private AuthViewModel authViewModel;
    private GoogleSignInClient googleSignInClient;
    //private String TAG = "Login";
    private CallbackManager callbackManager;
    private EditText email;
    private EditText password;
    private Button buttonLogin;


    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialComponents();
    }
    private void initialComponents(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        buttonLogin = findViewById(R.id.button);
        initSignInButton();
        initAuthViewModel();
        initGoogleSignInClient();
        initLoginEmail();
    }


    private void initLoginEmail(){
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email_string = String.valueOf(email.getText());
                String pass_string = String.valueOf(password.getText());
                if (checkCredentials(email_string,pass_string)){
                    AuthCredential credential = EmailAuthProvider.getCredential(email_string, pass_string);
                    signInWithAuthCredential(credential);
                }
            }
        });
    }


    private void initSignInButton() {
        Button googleSignInButton = findViewById(R.id.sign_button);
        Button facebook = findViewById(R.id.login_button_facebook);
        LoginButton loginButton = findViewById(R.id.login_button_facebook_gone);
        facebook.setOnClickListener(v -> loginButton.performClick());
        googleSignInButton.setOnClickListener(v -> signIn());
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
        /*GoogleSignInAccount googleSignInAccount =  GoogleSignIn.getLastSignedInAccount(this);
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
        };*/
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
            //Toast.makeText(this, "Hi, you don't have Internet.", Toast.LENGTH_LONG).show();
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
              //  Toast.makeText(this, "Hi, you don't have Internet.", Toast.LENGTH_LONG).show();
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
                System.out.println("Si entra***********");
                createNewUser(authenticatedUser);
            } else if(authenticatedUser.isError) {
                Toast.makeText(this,"Error en Credenciales",Toast.LENGTH_SHORT).show();
            }else{
                goToMainActivity(authenticatedUser);
            }
        });
    }
    private void createNewUser(EUserEDWIN authenticatedUser) {
        authViewModel.createUser(authenticatedUser);
        authViewModel.createdUserLiveData.observe(this, user -> {
            if (user.isCreated) {
                toastMessage(user.name);
                Intent intent = new Intent(Login.this, CompleteRegister.class);
                //intent.putExtra(USER, user);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
            //goToMainActivity(user);
        });
    }
    private void toastMessage(String name) {
        Toast.makeText(this, "Hi " + name + "!\n" + "Your account was successfully created.", Toast.LENGTH_LONG).show();
    }
    private void goToMainActivity(EUserEDWIN user) {
        Toast.makeText(this, "Welcome !.", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Login.this, MenuContainer.class);
        //intent.putExtra(USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private boolean checkCredentials(String email, String password){
        if(!email.contains("@")||email.length()<6){
            Toast.makeText(this,"Email invalid",Toast.LENGTH_SHORT).show();
            return  false;
        }else if (password.length()<6){
            Toast.makeText(this,"Password invalid",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }



}