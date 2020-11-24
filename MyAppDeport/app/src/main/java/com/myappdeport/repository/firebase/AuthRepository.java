package com.myappdeport.repository.firebase;

import androidx.lifecycle.MutableLiveData;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.myappdeport.model.entity.kill.EUserEDWIN;


import static com.myappdeport.utils.Constants.USERS;
import static com.myappdeport.utils.HelperClass.logErrorMessage;

@SuppressWarnings("ConstantConditions")
public class AuthRepository {
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseFirestore rootRef = FirebaseFirestore.getInstance();
    private CollectionReference usersRef = rootRef.collection(USERS);

    public MutableLiveData<EUserEDWIN> firebaseSignIn(AuthCredential authCredential) {
        MutableLiveData<EUserEDWIN> authenticatedUserMutableLiveData = new MutableLiveData<>();
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(authTask -> {
            if (authTask.isSuccessful()) {
                boolean isNewUser = authTask.getResult().getAdditionalUserInfo().isNewUser();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                if (firebaseUser != null) {
                    String uid = firebaseUser.getUid();
                    String name = firebaseUser.getDisplayName();
                    String email = firebaseUser.getEmail();
                    EUserEDWIN user = new EUserEDWIN(uid, name, email);
                    user.isNew = isNewUser;
                    authenticatedUserMutableLiveData.setValue(user);
                }
            } else {
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
                            //IEST IN LOCALSLQIETE
                        } else {
                            logErrorMessage(userCreationTask.getException().getMessage());
                        }
                    });
                } else {
                    newUserMutableLiveData.setValue(authenticatedUser);
                }
                //
            } else {
                logErrorMessage(uidTask.getException().getMessage());
            }
        });
        return newUserMutableLiveData;
    }
}