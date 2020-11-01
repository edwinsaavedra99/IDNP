package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.entity.funcional.Activity;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(uses = RouteMapper.class)
public interface ActivityMapper extends MapperEntityDtoFunctional<EActivity, DTOActivity, Activity> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eActivity Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mappings({
            @Mapping(target = "startTime", source = "eActivity.startTime", defaultValue = "00:00:00"),
            @Mapping(target = "endTime", source = "eActivity.endTime", defaultValue = "00:00:00"),
            @Mapping(target = "kiloCalories", source = "eActivity.kiloCalories", defaultValue = "0.0"),
            @Mapping(target = "dtoRoute", source = "eActivity.routeDocumentId", ignore = true)
    })
    DTOActivity entityToDto(EActivity eActivity);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoActivity Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    EActivity dtoToEntity(DTOActivity dtoActivity);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param activity Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "id", source = "activity.id"),
            @Mapping(target = "documentId", source = "activity.documentId"),
            @Mapping(target = "startTime", source = "activity.startTime", dateFormat = "HH:mm:ss"),
            @Mapping(target = "endTime", source = "activity.endTime", dateFormat = "HH:mm:ss"),
            @Mapping(target = "kiloCalories", source = "activity.kiloCalories"),
            @Mapping(target = "routeDocumentId", source = "activity.route.documentId")
    })
    EActivity functionalToEntity(Activity activity);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eActivity Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    Activity entityToFunctional(EActivity eActivity);

    /**
     * Transforma de un functional a un dto.
     *
     * @param activity Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "startTime", source = "activity.startTime", dateFormat = "HH:mm:ss", defaultValue = "00:00:00"),
            @Mapping(target = "endTime", source = "activity.endTime", dateFormat = "HH:mm:ss", defaultValue = "00:00:00"),
            @Mapping(target = "kiloCalories", source = "activity.kiloCalories", defaultValue = "0.0"),
            @Mapping(target = "dtoRoute", source = "activity.route")
    })
    DTOActivity functionalToDto(Activity activity);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoActivity Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    Activity dtoToFunctional(DTOActivity dtoActivity);
}
