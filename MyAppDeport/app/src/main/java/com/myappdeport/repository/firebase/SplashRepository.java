package com.myappdeport.repository.firebase;


import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myappdeport.model.entity.kill.EUserEDWIN;

import static com.myappdeport.utils.Constants.USERS;
import static com.myappdeport.utils.HelperClass.logErrorMessage;

//import ro.alexmamo.firebaseauthapp.auth.User;

//import static ro.alexmamo.firebaseauthapp.utils.Constants.USERS;
//import static ro.alexmamo.firebaseauthapp.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
public class SplashRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private EUserEDWIN user = new EUserEDWIN();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(USERS);

    public MutableLiveData<EUserEDWIN> checkIfUserIsAuthenticatedInFirebase() {
        MutableLiveData<EUserEDWIN> isUserAuthenticateInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        } else {
            user.uid = firebaseUser.getUid();

            user.isAuthenticated = true;
            isUserAuthenticateInFirebaseMutableLiveData.setValue(user);
        }
        return isUserAuthenticateInFirebaseMutableLiveData;
    }

    public MutableLiveData<EUserEDWIN> addUserToLiveData(String uid) {
        MutableLiveData<EUserEDWIN> userMutableLiveData = new MutableLiveData<>();
        usersRef.document(uid).get().addOnCompleteListener(userTask -> {
            if (userTask.isSuccessful()) {
                DocumentSnapshot document = userTask.getResult();
                if(document.exists()) {
                    EUserEDWIN user = document.toObject(EUserEDWIN.class);
                    userMutableLiveData.setValue(user);
                }
            } else {
                logErrorMessage(userTask.getException().getMessage());
            }
        });
        return userMutableLiveData;
    }
}

