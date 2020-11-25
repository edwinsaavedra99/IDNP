package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.dto.DTOPosition;
import com.myappdeport.model.entity.functional.Position;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface PositionMapper extends GenericMapper<EPosition, DTOPosition, Position> {
    @Override
    @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0")
    @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0")
    @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    DTOPosition entityToDto(EPosition position);

    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    EPosition dtoToEntity(DTOPosition position);

    @Override
    @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0")
    @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0")
    @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    Position entityToFunctional(EPosition position);

    @Override
    @InheritInverseConfiguration(name = "entityToFunctional")
    EPosition functionalToEntity(Position position);

    @Override
    @Mapping(target = "latitude", source = "position.latitude", defaultValue = "0.0")
    @Mapping(target = "longitude", source = "position.longitude", defaultValue = "0.0")
    @Mapping(target = "distance", source = "position.distance", defaultValue = "0.0")
    DTOPosition functionalToDto(Position position);

    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    Position dtoToFunctional(DTOPosition position);
}
