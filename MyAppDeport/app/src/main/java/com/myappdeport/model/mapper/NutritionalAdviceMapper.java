package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.model.entity.dto.DTONutritionalAdvice;
import com.myappdeport.model.entity.functional.NutritionalAdvice;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

@Mapper
public interface NutritionalAdviceMapper extends GenericMapper<ENutritionalAdvice, DTONutritionalAdvice, NutritionalAdvice> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eNutritionalAdvice Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    DTONutritionalAdvice entityToDto(ENutritionalAdvice eNutritionalAdvice);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoNutritionalAdvice Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    ENutritionalAdvice dtoToEntity(DTONutritionalAdvice dtoNutritionalAdvice);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param nutritionalAdvice Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    ENutritionalAdvice functionalToEntity(NutritionalAdvice nutritionalAdvice);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eNutritionalAdvice Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    NutritionalAdvice entityToFunctional(ENutritionalAdvice eNutritionalAdvice);

    /**
     * Transforma de un functional a un dto.
     *
     * @param nutritionalAdvice Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    DTONutritionalAdvice functionalToDto(NutritionalAdvice nutritionalAdvice);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoNutritionalAdvice Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    NutritionalAdvice dtoToFunctional(DTONutritionalAdvice dtoNutritionalAdvice);
}
