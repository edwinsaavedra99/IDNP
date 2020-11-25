package com.myappdeport.repository;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.mapper.UserMapper;
import org.mapstruct.factory.Mappers;

/**
 * Es el servicio de autenticación.
 *
 * @param <C> Criterio de inicio de sesión.
 */
public interface IAuthRepository<C> {

    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    /**
     * Iniciar sesión.
     *
     * @param authCredential Credenciales para ingreso de sesión.
     * @return
     */
    Task<EUser> signIn(C authCredential);

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
