package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.dto.DTOPosition;
import com.myappdeport.model.entity.funcional.Position;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PositionMapperTest {

    private final PositionMapper mapper = Mappers.getMapper(PositionMapper.class);
    private Position position;
    private EPosition ePosition;
    private DTOPosition dtoPosition;

    @BeforeEach
    protected void setUp() {
        this.position = new Position(1L, "abcde", 123.1, 23.5, 43.8);
        this.ePosition = new EPosition(1L, "abcde", 123.1, 23.5, 43.8);
        this.dtoPosition = new DTOPosition("123.1", "23.5", "43.8");
    }

    @Test
    @DisplayName("Mapper: Entity to DTO")
    void entityToDto() {
        DTOPosition positionMapper = this.mapper.entityToDto(this.ePosition);
        assertEquals(positionMapper, this.dtoPosition, "La comprobación de tipos no se realizo de forma exitosa.");
    }

    @Test
    @DisplayName("Mapper: DTO to Entity")
    void dtoToEntity() {
        EPosition positionMapper = this.mapper.dtoToEntity(this.dtoPosition);
        assertEquals(positionMapper, new EPosition(null, null, positionMapper.getLatitude(), positionMapper.getLongitude(), positionMapper.getDistance()), "La comprobación de tipos no se realizo de forma exitosa.");
    }

    @Test
    @DisplayName("Mapper: Entity to Functional")
    void entityToFunctional() {
        Position positionMapper = mapper.entityToFunctional(this.ePosition);
        assertEquals(positionMapper, this.position, "La comprobación de tipos no se realizo de forma exitosa.");
    }

    @Test
    @DisplayName("Mapper: Functional to Entity")
    void functionalToEntity() {
        EPosition positionMapper = mapper.functionalToEntity(this.position);
        assertEquals(positionMapper, this.ePosition, "La comprobación de tipos no se realizo de forma exitosa.");
    }

    @Test
    @DisplayName("Mapper: Functional to DTO")
    void functionalToDto() {
        DTOPosition positionMapper = mapper.functionalToDto(this.position);
        assertEquals(positionMapper, this.dtoPosition, "La comprobación de tipos no se realizo de forma exitosa.");
    }

    @Test
    @DisplayName("Mapper: DTO to Functional")
    void dtoToFunctional() {
        Position positionMapper = mapper.dtoToFunctional(this.dtoPosition);
        assertEquals(positionMapper, new Position(this.position.getLatitude(), this.position.getLongitude(), this.position.getDistance()), "La comprobación de tipos no se realizo de forma exitosa.");
    }
}