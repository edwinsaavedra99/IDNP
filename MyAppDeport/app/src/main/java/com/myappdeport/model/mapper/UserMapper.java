package com.myappdeport.model.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.entity.dto.DTOUser;
import com.myappdeport.model.entity.functional.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = {ActivityMapper.class})
public interface UserMapper extends GenericMapper<EUser, DTOUser, User> {

    @Mapping(target = "documentId", source = "firebaseUser.uid")
    @Mapping(target = "name", source = "firebaseUser.displayName")
    @Mapping(target = "email", source = "firebaseUser.email")
    @Mapping(target = "birthday", dateFormat = "dd/MM/yyyy", defaultValue = "01/01/0001")
    @Mapping(target = "eActivityList", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "isAuthenticated", ignore = true)
    @Mapping(target = "isCreated", ignore = true)
    @Mapping(target = "isNew", ignore = true)
    @Mapping(target = "EActivityList", ignore = true)
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "height", ignore = true)
    @Mapping(target = "weight", ignore = true)
    EUser convertFirebaseUserToEUser(FirebaseUser firebaseUser);

    /**
     * Transforma de una entidad a un dto.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mapping(target = "dtoActivities", source = "entity.EActivityList")
    DTOUser entityToDto(EUser entity);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    EUser dtoToEntity(DTOUser dto);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mapping(target = "EActivityList", source = "functional.activities")
    EUser functionalToEntity(User functional);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    User entityToFunctional(EUser entity);

    /**
     * Transforma de un functional a un dto.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mapping(target = "dtoActivities", source = "functional.activities")
    DTOUser functionalToDto(User functional);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    User dtoToFunctional(DTOUser dto);
}
