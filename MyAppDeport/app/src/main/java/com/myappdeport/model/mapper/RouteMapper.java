package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.dto.DTORoute;
import com.myappdeport.model.entity.functional.Route;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = PositionMapper.class)
public interface RouteMapper extends GenericMapper<ERoute, DTORoute, Route> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eRoute Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mapping(target = "rhythm", source = "eRoute.rhythm", defaultValue = "0.0")
    @Mapping(target = "totalDistance", source = "eRoute.totalDistance", defaultValue = "0.0")
    @Mapping(target = "dtoPositionList", source = "eRoute.positions")
    DTORoute entityToDto(ERoute eRoute);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoRoute Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    ERoute dtoToEntity(DTORoute dtoRoute);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param route Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mapping(target = "positions", source = "route.positionList")
    ERoute functionalToEntity(Route route);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eRoute Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    Route entityToFunctional(ERoute eRoute);

    /**
     * Transforma de un functional a un dto.
     *
     * @param route Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mapping(target = "totalDistance", source = "route.totalDistance", defaultValue = "0.0")
    @Mapping(target = "rhythm", source = "route.rhythm", defaultValue = "0.0")
    @Mapping(target = "dtoPositionList", source = "route.positionList")
    DTORoute functionalToDto(Route route);


    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoRoute Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    Route dtoToFunctional(DTORoute dtoRoute);
}
