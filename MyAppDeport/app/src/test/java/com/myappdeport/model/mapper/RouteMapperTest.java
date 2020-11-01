package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.dto.DTOPosition;
import com.myappdeport.model.entity.dto.DTORoute;
import com.myappdeport.model.entity.funcional.Position;
import com.myappdeport.model.entity.funcional.Route;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class RouteMapperTest {

    private final RouteMapper mapper = Mappers.getMapper(RouteMapper.class);
    private Route route;
    private ERoute eRoute;
    private DTORoute dtoRoute;

    @BeforeEach
    protected void setUp() {
        this.route = new Route(1L, "abcde", 23.5, 12.4, Collections.singletonList(new Position(1L, "abcde", 123.1, 23.5, 43.8)));
        this.eRoute = new ERoute(1L, "abcde", 23.5, 12.4, Collections.singletonList("abcde"));
        this.dtoRoute = new DTORoute("23.5", "12.4", Collections.singletonList(new DTOPosition("123.1", "23.5", "43.8")));
    }

    @Test
    @DisplayName("Mapper: Entity to DTO")
    void entityToDto() {
        DTORoute routeMapper = this.mapper.entityToDto(this.eRoute);
        this.dtoRoute.setDtoPositionList(null);
        assertEquals(this.dtoRoute, routeMapper, "La transformación no fue exitosa.");
    }

    @Test
    @DisplayName("Mapper: DTO to Entity")
    void dtoToEntity() {
        ERoute routeMapper = this.mapper.dtoToEntity(this.dtoRoute);
        this.eRoute.setId(null);
        this.eRoute.setDocumentId(null);
        this.eRoute.setPositionDocumentIds(null);
        assertEquals(this.eRoute, routeMapper, "La transformación no fue exitosa.");
    }

    @Test
    @DisplayName("Mapper: Functional to Entity")
    void functionalToEntity() {
        ERoute routeMapper = this.mapper.functionalToEntity(this.route);
        assertEquals(this.eRoute, routeMapper, "La transformación no fue exitosa.");
    }

    @Test
    @DisplayName("Mapper: Entity to Functional")
    void entityToFunctional() {
        Route routeMapper = this.mapper.entityToFunctional(eRoute);
        this.route.getPositionList().get(0).setId(null);
        this.route.getPositionList().get(0).setLatitude(null);
        this.route.getPositionList().get(0).setLongitude(null);
        this.route.getPositionList().get(0).setDistance(null);
        assertEquals(this.route, routeMapper, "La transformación no fue exitosa.");
    }

    @Test
    @DisplayName("Mapper: Functional to DTO")
    void functionalToDto() {
        DTORoute routeMapper = this.mapper.functionalToDto(this.route);
        assertEquals(this.dtoRoute, routeMapper, "La transformación no fue exitosa.");
    }

    @Test
    @DisplayName("Mapper: DTO to Functional")
    void dtoToFunctional() {
        Route routeMapper = this.mapper.dtoToFunctional(this.dtoRoute);
        this.route.setId(null);
        this.route.setDocumentId(null);
        this.route.getPositionList().get(0).setId(null);
        this.route.getPositionList().get(0).setDocumentId(null);
        assertEquals(this.route, routeMapper, "La transformación no fue exitosa.");
    }
}