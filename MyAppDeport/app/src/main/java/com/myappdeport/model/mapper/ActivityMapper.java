package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.entity.functional.Activity;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = RouteMapper.class)
public interface ActivityMapper extends GenericMapper<EActivity, DTOActivity, Activity> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eActivity Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mapping(target = "startTime", source = "eActivity.startTime", defaultValue = "00:00:00")
    @Mapping(target = "endTime", source = "eActivity.endTime", defaultValue = "00:00:00")
    @Mapping(target = "kiloCalories", source = "eActivity.kiloCalories", defaultValue = "0.0")
    @Mapping(target = "dtoRoute", source = "eActivity.ERoute")
    DTOActivity entityToDto(EActivity eActivity);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoActivity Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    EActivity dtoToEntity(DTOActivity dtoActivity);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param activity Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mapping(target = "startTime", source = "activity.startTime", dateFormat = "HH:mm:ss")
    @Mapping(target = "endTime", source = "activity.endTime", dateFormat = "HH:mm:ss")
    @Mapping(target = "kiloCalories", source = "activity.kiloCalories", defaultValue = "0.0")
    @Mapping(target = "date", source = "activity.date", dateFormat = "dd/MM/yyyy", defaultValue = "01/01/0001")
    @Mapping(target = "ERoute", source = "activity.route")
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
    @Mapping(target = "startTime", source = "activity.startTime", dateFormat = "HH:mm:ss", defaultValue = "00:00:00")
    @Mapping(target = "endTime", source = "activity.endTime", dateFormat = "HH:mm:ss", defaultValue = "00:00:00")
    @Mapping(target = "kiloCalories", source = "activity.kiloCalories", defaultValue = "0.0")
    @Mapping(target = "date", source = "activity.date", dateFormat = "dd/MM/yyyy", defaultValue = "01/01/0001")
    @Mapping(target = "dtoRoute", source = "activity.route")
    DTOActivity functionalToDto(Activity activity);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoActivity Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    Activity dtoToFunctional(DTOActivity dtoActivity);
}
