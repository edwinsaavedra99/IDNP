package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.ESong;
import com.myappdeport.model.entity.dto.DTOSong;
import com.myappdeport.model.entity.funcional.Song;

import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.io.File;

@Mapper(imports = {File.class})
public interface SongMapper extends MapperEntityDtoFunctional<ESong, DTOSong, Song> {
    /**
     * Transforma de una entidad a un dto.
     *
     * @param eSong Es la entidad a ser transformada.
     * @return Es el dto generado por la entidad.
     */
    @Override
    @Mappings({
            @Mapping(target = "author", source = "eSong.author"),
            @Mapping(target = "duration", source = "eSong.duration", dateFormat = "hh:mm:ss", defaultValue = "00:00:00"),
            @Mapping(target = "name", source = "eSong.name"),
            @Mapping(target = "songRoute", source = "eSong.songRoute")
    })
    DTOSong entityToDto(ESong eSong);

    /**
     * Transforma de una dto a una entidad.
     *
     * @param dtoSong Es el dto a ser transformada.
     * @return Es la entidad generada por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "entityToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "documentId", ignore = true)
    })
    ESong dtoToEntity(DTOSong dtoSong);

    /**
     * Transforma de un functional a una entidad.
     *
     * @param song Es el functional a ser transformada.
     * @return Es la entidad generada por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "id", source = "song.id"),
            @Mapping(target = "documentId", ignore = true),
            @Mapping(target = "author", source = "song.author"),
            @Mapping(target = "duration", source = "song.duration", dateFormat = "hh:mm:ss", defaultValue = "00:00:00"),
            @Mapping(target = "name", source = "song.name"),
            @Mapping(target = "songRoute", source = "song.songRoute")
    })
    ESong functionalToEntity(Song song);

    /**
     * Transforma de una entidad a un functional.
     *
     * @param eSong Es la entidad a ser transformada.
     * @return Es el functional generada por la entidad.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToEntity")
    @Mappings({
            @Mapping(target = "songFile", expression = "java(new File(eSong.getSongRoute()))")
    })
    Song entityToFunctional(ESong eSong);

    /**
     * Transforma de un functional a un dto.
     *
     * @param song Es el functional a ser transformada.
     * @return Es el dto generado por el functional.
     */
    @Override
    @Mappings({
            @Mapping(target = "author", source = "song.author"),
            @Mapping(target = "duration", source = "song.duration", dateFormat = "hh:mm:ss", defaultValue = "00:00:00"),
            @Mapping(target = "name", source = "song.name"),
            @Mapping(target = "songRoute", source = "song.songRoute")
    })
    DTOSong functionalToDto(Song song);

    /**
     * Transforma de un dto a un functional.
     *
     * @param dtoSong Es el dto a ser transformada.
     * @return Es el functional generado por el dto.
     */
    @Override
    @InheritInverseConfiguration(name = "functionalToDto")
    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "songFile", expression = "java(new File( dtoSong.getSongRoute() ))")
    })
    Song dtoToFunctional(DTOSong dtoSong);
}
