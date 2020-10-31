package com.myappdeport.model.mapper;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.dto.DTORoute;
import com.myappdeport.model.entity.funcional.Position;
import com.myappdeport.model.entity.funcional.Route;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(uses = PositionMapper.class)
public interface RouteMapper extends MapperEntityDtoFunctional<ERoute, DTORoute, Route> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eRoute Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mappings({
            @Mapping(target = "rhythm", source = "eRoute.rhythm"),
            @Mapping(target = "totalDistance", source = "eRoute.totalDistance"),
            @Mapping(target = "dtoPositionList", source = "eRoute.positionDocumentIds", ignore = true)
    })
    DTORoute entityToDto(ERoute eRoute);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoRoute Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    ERoute dtoToEntity(DTORoute dtoRoute);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param route Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "id", source = "route.id"),
            @Mapping(target = "documentId", source = "route.documentId"),
            @Mapping(target = "rhythm", source = "route.rhythm", defaultValue = "0.0"),
            @Mapping(target = "totalDistance", source = "route.totalDistance", defaultValue = "0.0"),
            @Mapping(target = "positionDocumentIds", source = "route.positionList", qualifiedByName = "positionDocumentIdsToPositionList")
    })
    ERoute functionalToEntity(Route route);

    /**
     * Transforma de una lista de posiciones a una lista con sus documentIds.
     *
     * @param positionList Es la lista de posiciones.
     * @return Es la lista con los Ids.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Named("positionDocumentIdsToPositionList")
    default List<String> positionDocumentIdsToPositionList(List<Position> positionList) {
        return positionList.stream().map(Position::getDocumentId).collect(Collectors.toList());
    }

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eRoute Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    @Mappings({
            @Mapping(target = "positionList", source = "eRoute.positionDocumentIds", qualifiedByName = "positionListToPositionDocumentIds")
    })
    Route entityToFunctional(ERoute eRoute);

    /**
     * Transforma de una lista de posiciones a una lista con sus documentIds.
     *
     * @param positionDocumentIds Es la lista de los ids de las posiciones.
     * @return Es la lista de posiciones.
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Named("positionListToPositionDocumentIds")
    default List<Position> positionListToPositionDocumentIds(List<String> positionDocumentIds) {
        return positionDocumentIds.stream().map(s -> {
            Position position = new Position();
            position.setDocumentId(s);
            return position;
        }).collect(Collectors.toList());
    }

    /**
     * Transforma de un functional a un dto.
     *
     * @param route Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "totalDistance", source = "route.totalDistance", defaultValue = "0.0"),
            @Mapping(target = "rhythm", source = "route.rhythm", defaultValue = "0.0"),
            @Mapping(target = "dtoPositionList", source = "route.positionList")
    })
    DTORoute functionalToDto(Route route);


    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoRoute Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    Route dtoToFunctional(DTORoute dtoRoute);
}
