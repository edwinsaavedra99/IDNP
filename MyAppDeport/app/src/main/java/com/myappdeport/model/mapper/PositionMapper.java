package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.dto.DTOPosition;
import com.myappdeport.model.entity.funcional.Position;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface PositionMapper extends MapperEntityDtoFunctional<EPosition, DTOPosition, Position> {
    @Override
    @Mappings({
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    DTOPosition entityToDto(EPosition position);

    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true),
            @Mapping(target = "idERoute", ignore = true),
            @Mapping(target = "ERouteDocumentId", ignore = true)
    })
    EPosition dtoToEntity(DTOPosition position);

    @Override
    @Mappings({
            @Mapping(target = "id", source = "position.id"),
            @Mapping(target = "documentId", source = "position.documentId"),
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    Position entityToFunctional(EPosition position);

    @Override
    @InheritInverseConfiguration(name = "entityToFunctional")
    @Mappings({
            @Mapping(target = "idERoute", ignore = true),
            @Mapping(target = "ERouteDocumentId", ignore = true)
    })
    EPosition functionalToEntity(Position position);

    @Override
    @Mappings({
            @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0"),
            @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0"),
            @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    })
    DTOPosition functionalToDto(Position position);

    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    Position dtoToFunctional(DTOPosition position);
}
