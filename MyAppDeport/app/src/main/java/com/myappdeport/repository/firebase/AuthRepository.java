package com.myappdeport.repository.firebase;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.myappdeport.model.entity.kill.EUserEDWIN;
import com.myappdeport.repository.IUserRepository;
import com.myappdeport.repository.room.UserRoomRepository;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

import static com.myappdeport.utils.Constants.TAG;
import static com.myappdeport.utils.Constants.USERS;
import static com.myappdeport.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
public class AuthRepository {

    private static AuthRepository INSTANCE;


    public synchronized static AuthRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AuthRepository();
        return INSTANCE;
    }

    private AuthRepository() {
        super();
    }


    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(USERS);


    public MutableLiveData<EUserEDWIN> userLogin(){
        MutableLiveData<EUserEDWIN> userMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            final EUserEDWIN user = new EUserEDWIN();
            String uid = firebaseUser.getUid();
            String name = firebaseUser.getDisplayName();
            String email = firebaseUser.getEmail();
            DocumentReference uidRef = usersRef.document(uid);
            uidRef.get().addOnCompleteListener(uidTask -> {
                if (uidTask.isSuccessful()) {
                    DocumentSnapshot document = uidTask.getResult();
                    if (document.exists()) {
                        user.fechaNacimiento = document.getString("fechaNacimiento");
                        user.altura = document.getString("altura");
                        user.edad = document.getString("edad");
                        user.name = document.getString("name");
                        user.peso = document.getString("peso");
                        userMutableLiveData.setValue(user);
                    } else {
                        logErrorMessage(uidTask.getException().getMessage());
                    }
                } else {
                    logErrorMessage(uidTask.getException().getMessage());
                }
            });
            //ser =
            user.photoUrl = String.valueOf(firebaseUser.getPhotoUrl());
            user.isAuthenticated = true;
            userMutableLiveData.setValue(user);
        }else{
            EUserEDWIN user = new EUserEDWIN("", "", "");
            user.isError = true;
            userMutableLiveData.setValue(user);
        }
        return  userMutableLiveData;
    }


    public MutableLiveData<EUserEDWIN> userCompleteRegisterData(EUserEDWIN user){
        MutableLiveData<EUserEDWIN> userMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            Map<String, Object> data = new HashMap<>();
            data.put("altura", user.altura);
            data.put("edad", user.edad);
            data.put("name", firebaseUser.getDisplayName());
            data.put("peso",user.peso);
            data.put("fechaNacimiento",user.fechaNacimiento);
            usersRef.document(uid).set(data, SetOptions.merge());
            user.photoUrl = String.valueOf(firebaseUser.getPhotoUrl());
            user.isAuthenticated = true;
            user.isCreated = true;
            userMutableLiveData.setValue(user);
        }else{
            EUserEDWIN users = new EUserEDWIN("", "", "");
            users.isError = true;
            userMutableLiveData.setValue(users);
        }
        return  userMutableLiveData;
    }


    public MutableLiveData<EUserEDWIN> updateDataUser(EUserEDWIN user){
        MutableLiveData<EUserEDWIN> userMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
            String uid = firebaseUser.getUid();
            Map<String, Object> data = new HashMap<>();
            data.put("altura", user.altura);
            data.put("name", user.name);
            data.put("peso",user.peso);
            //data.put("fechaNacimiento",user.fechaNacimiento);
            usersRef.document(uid).set(data, SetOptions.merge());
            //user.photoUrl = String.valueOf(firebaseUser.getPhotoUrl());
            user.isAuthenticated = true;
            user.isCreated = true;
            userMutableLiveData.setValue(user);
        }else{
            EUserEDWIN users = new EUserEDWIN("", "", "");
            users.isError = true;
            userMutableLiveData.setValue(users);
        }
        return  userMutableLiveData;
    }


    public MutableLiveData<EUserEDWIN> firebaseSignIn(AuthCredential authCredential) {
        MutableLiveData<EUserEDWIN> authenticatedUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                System.out.println("Si entrav1***********");
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    String name = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    EUserEDWIN user = new EUserEDWIN(uid, name, email);
                    user.photoUrl = String.valueOf(firebaseUser.getPhotoUrl());
                    user.isNew = isNewUser;
                    authenticatedUserMutableLiveData.setValue(user);
                }
            } else {
                System.out.println("Error en Credenciales");
                EUserEDWIN user = new EUserEDWIN("", "", "");
                user.isError = true;
                authenticatedUserMutableLiveData.setValue(user);
                logErrorMessage(authTask.getException().getMessage());
            }
        });
        return authenticatedUserMutableLiveData;
    }

    public MutableLiveData<EUserEDWIN> createUserInFirestoreIfNotExists(EUserEDWIN authenticatedUser) {
        MutableLiveData<EUserEDWIN> newUserMutableLiveData = new MutableLiveData<>();
        DocumentReference uidRef = usersRef.document(authenticatedUser.uid);
        uidRef.get().addOnCompleteListener(uidTask -> {
            if (uidTask.isSuccessful()) {
                DocumentSnapshot document = uidTask.getResult();
                if (!document.exists()) {
                    uidRef.set(authenticatedUser).addOnCompleteListener(userCreationTask -> {
                        if (userCreationTask.isSuccessful()) {
                            authenticatedUser.isCreated = true;
                            newUserMutableLiveData.setValue(authenticatedUser);
                        } else {
                            logErrorMessage(userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    newUserMutableLiveData.setValue(authenticatedUser);
                }
            } else {
                logErrorMessage(uidTask.getException().getMessage());
            }
        });
        return newUserMutableLiveData;
    }


    public MutableLiveData<EUserEDWIN> createUserWithEmailAndPassword(EUserEDWIN authenticatedUser) {
        MutableLiveData<EUserEDWIN> newUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.createUserWithEmailAndPassword(authenticatedUser.email, authenticatedUser.password)
                .addOnCompleteListener(authTask -> {
                    if (authTask.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if(user!=null){
                                EUserEDWIN userg = new EUserEDWIN("", "", "");
                                authenticatedUser.uid = user.getUid();
                                authenticatedUser.email = user.getEmail();
                                authenticatedUser.photoUrl = String.valueOf(user.getPhotoUrl());
                                //authenticatedUser.name = user.getDisplayName();
                                /*authenticatedUser.edad =
                                authenticatedUser.peso =*/
                                authenticatedUser.isCreated = true;
                                newUserMutableLiveData.setValue(authenticatedUser);
                                createUserInFirestoreIfNotExists(authenticatedUser);
                            }else{
                                EUserEDWIN userg = new EUserEDWIN("", "", "");
                                userg.isError = true;
                                newUserMutableLiveData.setValue(userg);
                            }
                    } else {
                            // If sign in fails, display a message to the user.
                            EUserEDWIN userg = new EUserEDWIN("", "", "");
                            userg.isError = true;
                            newUserMutableLiveData.setValue(userg);
                            Log.w(TAG, "createUserWithEmail:failure", authTask.getException());
                    }
                });
        return newUserMutableLiveData;
    }
    public void cerrarSesion(){
        firebaseAuth.signOut();
    }
}