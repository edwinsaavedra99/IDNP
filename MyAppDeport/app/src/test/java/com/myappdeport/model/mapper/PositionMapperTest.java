package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.funcional.Position;

import junit.framework.TestCase;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

public class PositionMapperTest extends TestCase {

    private final PositionMapper positionMapper = Mappers.getMapper(PositionMapper.class);
    private Position position;
    private EPosition ePosition;

    @Override
    protected void setUp() throws Exception {
        position = new Position(1L, "abcde", 123.1, 23., 43.8);
        ePosition = new EPosition(1L, "123.1",23.3, 123.5,32.4);
    }

    @Test
    void entityToDto() {
    }

    @Test
    void dtoToEntity() {
    }

    @Test
    void entityToFunctional() {
    }

    @Test
    void functionalToEntity() {
    }

    @Test
    void functionalToDto() {
    }

    @Test
    void dtoToFunctional() {
    }
}