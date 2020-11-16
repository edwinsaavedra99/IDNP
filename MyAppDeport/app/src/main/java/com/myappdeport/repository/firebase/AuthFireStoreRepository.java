package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.IAuthRepository;

public class AuthFireStoreRepository implements IAuthRepository {
    private static AuthFireStoreRepository INSTANCE;
    private final UserFireStoreRepository userFireStoreRepository;
    private final FirebaseAuth firebaseAuth;

    public synchronized static AuthFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new AuthFireStoreRepository();
        return INSTANCE;
    }

    private AuthFireStoreRepository() {
        this.userFireStoreRepository = UserFireStoreRepository.getInstance();
        this.firebaseAuth = FirebaseAuth.getInstance();
    }

    /**
     * Iniciar sesión.
     *
     * @param authCredential Credenciales para ingreso de sesión.
     * @return
     */
    @Override
    public Task<EUser> signIn(AuthCredential authCredential) {
        return firebaseAuth.signInWithCredential(authCredential).onSuccessTask(authResult -> Tasks.forResult(MAPPER.convertFirebaseUserToEUser(authResult.getUser())));
    }

    /**
     * Registrar un usuario
     *
     * @param eUser Es el usuario que se registrara.
     * @return
     */
    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<EUser> register(EUser eUser) {
        return this.userFireStoreRepository.collectionReference.document(eUser.getDocumentId()).get().onSuccessTask(documentSnapshot -> {
            if (!documentSnapshot.exists()) {
                return this.userFireStoreRepository.save(eUser).onSuccessTask(eUser1 -> Tasks.call(() -> {
                    eUser1.setIsCreated(true);
                    return eUser1;
                }));
            } else {
                throw new NullPointerException("Don't exist document reference.");
            }
        });
    }

    /**
     * Es el usuario que inicio sesion ahora.
     *
     * @return
     */
    @Override
    public Task<EUser> getCurrentUser() {
        return Tasks.call(() -> MAPPER.convertFirebaseUserToEUser(this.firebaseAuth.getCurrentUser()));
    }

    /**
     * Es para cerrar sesión del ususario.
     *
     * @return
     */
    @Override
    public Task<Void> singOut() {
        return Tasks.call(() -> {
            this.firebaseAuth.signOut();
            return null;
        });
    }
}
