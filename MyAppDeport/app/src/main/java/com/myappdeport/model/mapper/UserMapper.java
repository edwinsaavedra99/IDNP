package com.myappdeport.model.mapper;

import com.google.firebase.auth.FirebaseUser;
import com.myappdeport.model.entity.database.EUser;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface UserMapper {
    @Mappings({
            @Mapping(target = "documentId", source = "firebaseUser.uid"),
            @Mapping(target = "name", source = "firebaseUser.displayName"),
            @Mapping(target = "email", source = "firebaseUser.email"),
            @Mapping(target = "eActivityList", ignore = true),
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "isAuthenticated", ignore = true),
            @Mapping(target = "isCreated", ignore = true),
            @Mapping(target = "isNew", ignore = true),
            @Mapping(target = "EActivityList", ignore = true)
    })
    EUser convertFirebaseUserToEUser(FirebaseUser firebaseUser);
}
