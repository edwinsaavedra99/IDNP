package com.myappdeport.model.mapper;

public interface MapperEntityDtoFunctional<Entity, DTO, Functional> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    DTO entityToDto(Entity entity);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    Entity dtoToEntity(DTO dto);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    Entity functionalToEntity(Functional functional);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param entity Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    Functional entityToFunctional(Entity entity);

    /**
     * Transforma de un functional a un dto.
     *
     * @param functional Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    DTO functionalToDto(Functional functional);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dto Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    Functional dtoToFunctional(DTO dto);
}
