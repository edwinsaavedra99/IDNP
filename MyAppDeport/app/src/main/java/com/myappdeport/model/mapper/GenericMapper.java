package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EntityDatabase;
import com.myappdeport.model.entity.dto.DTOEntity;
import com.myappdeport.model.entity.functional.Entity;

public interface GenericMapper<E extends EntityDatabase, D extends DTOEntity, F extends Entity> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    D entityToDto(E entity);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    E dtoToEntity(D dto);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    E functionalToEntity(F functional);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    F entityToFunctional(E entity);

    /**
     * Transforma de un functional a un dto.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    D functionalToDto(F functional);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    F dtoToFunctional(D dto);
}
