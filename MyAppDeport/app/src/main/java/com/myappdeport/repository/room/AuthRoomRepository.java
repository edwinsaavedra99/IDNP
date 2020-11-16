package com.myappdeport.repository.room;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.IAuthRepository;

public class AuthRoomRepository implements IAuthRepository {
    /**
     * Iniciar sesión.
     *
     * @param authCredential Credenciales para ingreso de sesión.
     * @return
     */
    @Override
    public Task<EUser> signIn(AuthCredential authCredential) {
        return null;
    }

    /**
     * Registrar un usuario
     *
     * @param eUser Es el usuario que se registrara.
     * @return
     */
    @Override
    public Task<EUser> register(EUser eUser) {
        return null;
    }

    /**
     * Es el usuario que inicio sesion ahora.
     *
     * @return
     */
    @Override
    public Task<EUser> getCurrentUser() {
        return null;
    }

    /**
     * Es para cerrar sesión del ususario.
     *
     * @return
     */
    @Override
    public Task<Void> singOut() {
        return null;
    }
}
