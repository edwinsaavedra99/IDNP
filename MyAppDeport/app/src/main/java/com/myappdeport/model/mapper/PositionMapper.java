package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.dto.DTOPosition;
import com.myappdeport.model.entity.funcional.Position;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface PositionMapper {
    @Mappings({
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    DTOPosition entityToDto(EPosition position);

    @InheritInverseConfiguration(name = "entityToDto")
    EPosition dtoToEntity(DTOPosition position);

    @Mappings({
            @Mapping(target = "id", source = "position.id"),
            @Mapping(target = "documentId", source = "position.documentId"),
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    Position entityToFunctional(EPosition position);

    @InheritInverseConfiguration(name = "entityToFunctional")
    EPosition functionalToEntity(Position position);

    @Mappings({
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    DTOPosition functionalToDto(Position position);

    @InheritInverseConfiguration(name = "functionalToDto")
    Position dtoToFunctional(DTOPosition position);
}
