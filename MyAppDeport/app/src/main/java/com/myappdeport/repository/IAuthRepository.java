package com.myappdeport.repository;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.mapper.UserMapper;

import org.mapstruct.factory.Mappers;

public interface IAuthRepository {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    /**
     * Iniciar sesión.
     *
     * @param authCredential Credenciales para ingreso de sesión.
     * @return
     */
    Task<EUser> signIn(AuthCredential authCredential);

    /**
     * Registrar un usuario
     *
     * @param eUser Es el usuario que se registrara.
     * @return
     */
    Task<EUser> register(EUser eUser);

    /**
     * Es el usuario que inicio sesion ahora.
     *
     * @return
     */
    Task<EUser> getCurrentUser();

    /**
     * Es para cerrar sesión del ususario.
     *
     * @return
     */
    Task<Void> singOut();
}
