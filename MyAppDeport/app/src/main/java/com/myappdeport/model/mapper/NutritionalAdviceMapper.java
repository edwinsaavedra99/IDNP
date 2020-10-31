package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.ENutritionalAdvice;
import com.myappdeport.model.entity.dto.DTONutritionalAdvice;
import com.myappdeport.model.entity.funcional.NutritionalAdvice;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper
public interface NutritionalAdviceMapper extends MapperEntityDtoFunctional<ENutritionalAdvice, DTONutritionalAdvice, NutritionalAdvice> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eNutritionalAdvice Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mappings({
            @Mapping(target = "title", source = "eNutritionalAdvice.title"),
            @Mapping(target = "shortDescription", source = "eNutritionalAdvice.shortDescription"),
            @Mapping(target = "longDescription", source = "eNutritionalAdvice.longDescription"),
            @Mapping(target = "imageUrlCloudStorage", source = "eNutritionalAdvice.imageUrlCloudStorage")
    })
    DTONutritionalAdvice entityToDto(ENutritionalAdvice eNutritionalAdvice);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoNutritionalAdvice Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true),
            @Mapping(target = "image", ignore = true)
    })
    ENutritionalAdvice dtoToEntity(DTONutritionalAdvice dtoNutritionalAdvice);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param nutritionalAdvice Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "title", source = "nutritionalAdvice.title"),
            @Mapping(target = "shortDescription", source = "nutritionalAdvice.shortDescription"),
            @Mapping(target = "longDescription", source = "nutritionalAdvice.longDescription"),
            @Mapping(target = "imageUrlCloudStorage", source = "nutritionalAdvice.imageUrlCloudStorage")
    })
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
    @Mappings({
            @Mapping(target = "documentId", ignore = true),
            @Mapping(target = "image", ignore = true)
    })
    NutritionalAdvice dtoToFunctional(DTONutritionalAdvice dtoNutritionalAdvice);
}
