package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.ESong;
import com.myappdeport.model.entity.dto.DTOSong;
import com.myappdeport.model.entity.functional.Song;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SongMapper extends GenericMapper<ESong, DTOSong, Song> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eSong Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Mapping(target = "author", ignore = true)
    @Override
    DTOSong entityToDto(ESong eSong);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoSong Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    ESong dtoToEntity(DTOSong dtoSong);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param song Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mapping(target = "duration", source = "song.duration", dateFormat = "hh:mm:ss", defaultValue = "00:00:00")
    @Mapping(target = "songRoute", ignore = true)
    ESong functionalToEntity(Song song);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eSong Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    Song entityToFunctional(ESong eSong);

    /**
     * Transforma de un functional a un dto.
     *
     * @param song Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mapping(target = "author", ignore = true)
    @Mapping(target = "duration", source = "song.duration", dateFormat = "HH:mm:ss", defaultValue = "00:00:00")
    @Mapping(target = "songRoute", ignore = true)
    DTOSong functionalToDto(Song song);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoSong Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    Song dtoToFunctional(DTOSong dtoSong);
}
