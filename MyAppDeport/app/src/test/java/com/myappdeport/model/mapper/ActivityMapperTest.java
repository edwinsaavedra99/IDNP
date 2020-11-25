package com.myappdeport.model.mapper;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.dto.DTOActivity;
import com.myappdeport.model.entity.functional.Activity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Disabled
class ActivityMapperTest {

    private final ActivityMapper mapper = Mappers.getMapper(ActivityMapper.class);
    private EActivity eActivity;
    private Activity activity;
    private DTOActivity dtoActivity;

    @BeforeEach
    protected void setUp() {
        /*this.eActivity = new EActivity(1L, "abcde", "00:00:00", "00:00:00", 12.5, "abcde");
        this.activity = new Activity(1L, "abcde", LocalTime.of(0, 0, 0), LocalTime.of(0, 0, 0), 12.5, new Route(1L, "abcde", 23.5, 12.4, Collections.singletonList(new Position(1L, "abcde", 123.1, 23.5, 43.8))));
        this.dtoActivity = new DTOActivity("00:00:00", "00:00:00", "12.5", new DTORoute("23.5", "12.4", Collections.singletonList(new DTOPosition("123.1", "23.5", "43.8"))));*/
    }

    @Test
    @DisplayName("Mapper: Entity to DTO")
    void entityToDto() {
        DTOActivity activityMapper = this.mapper.entityToDto(this.eActivity);
        this.dtoActivity.setDtoRoute(null);
        assertEquals(this.dtoActivity, activityMapper);
    }

    @Test
    @DisplayName("Mapper: DTO to Entity")
    void dtoToEntity() {
        EActivity activityMapper = this.mapper.dtoToEntity(this.dtoActivity);
        this.eActivity.setId(null);
        this.eActivity.setDocumentId(null);
        this.eActivity.setRouteDocumentId(null);
        assertEquals(this.eActivity, activityMapper);
    }

    @Test
    @DisplayName("Mapper: Functional to Entity")
    void functionalToEntity() {
        EActivity activityMapper = this.mapper.functionalToEntity(this.activity);
        assertEquals(this.eActivity, activityMapper);
    }

    @Test
    @DisplayName("Mapper: Entity to Functional")
    void entityToFunctional() {
        Activity activityMapper = this.mapper.entityToFunctional(this.eActivity);
        this.activity.getRoute().setId(null);
        this.activity.getRoute().setRhythm(null);
        this.activity.getRoute().setTotalDistance(null);
        this.activity.getRoute().setPositionList(null);
        assertEquals(this.activity, activityMapper);
    }

    @Test
    @DisplayName("Mapper: Functional to DTO")
    void functionalToDto() {
        EActivity activityMapper = this.mapper.functionalToEntity(this.activity);
        assertEquals(this.eActivity, activityMapper);
    }

    @Test
    @DisplayName("Mapper: DTO to Functional")
    void dtoToFunctional() {
        Activity activityMapper = this.mapper.dtoToFunctional(this.dtoActivity);
        this.activity.setId(null);
        this.activity.setDocumentId(null);
        this.activity.getRoute().setId(null);
        this.activity.getRoute().setDocumentId(null);
        this.activity.getRoute().getPositionList().get(0).setId(null);
        this.activity.getRoute().getPositionList().get(0).setDocumentId(null);
        assertEquals(this.activity, activityMapper);
    }
}