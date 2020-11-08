package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.relationship.RouteWithPosition;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class RouteRoomDao implements IRoomDao<ERoute, Long> {
    @Override
    @Query("SELECT * FROM ERoute")
    public abstract List<ERoute> findAll();

    @Override
    @Query("DELETE FROM ERoute")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(ERoute entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<ERoute> entities);

    @Override
    @Update
    public abstract void update(ERoute entity);

    @Override
    @Update
    public abstract void updateAll(List<ERoute> entities);

    @Override
    @Delete
    public abstract void delete(ERoute entity);

    @Override
    @Delete
    public abstract void delete(List<ERoute> entities);

    @Override
    @Query("SELECT * FROM ERoute WHERE id =:identifier")
    public abstract ERoute findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM ERoute")
    public abstract Integer count();

    @Insert
    protected abstract void _insertWithPositions(RouteWithPosition routeWithPosition);

    @Update
    protected abstract void _updateWithPositions(RouteWithPosition routeWithPosition);

    @Query("SELECT * FROM ERoute")
    protected abstract List<RouteWithPosition> _findAllWithPositions();

    @Delete
    protected abstract void _deleteWithPositions(RouteWithPosition routeWithPosition);

    @Query("SELECT * FROM ERoute WHERE id =:identifier")
    protected abstract RouteWithPosition getByIdWithPositions(Long identifier);

    public List<ERoute> findAllWithPositions() {
        List<RouteWithPosition> routeWithPositionList = _findAllWithPositions();
        List<ERoute> routes = new ArrayList<>();
        for (RouteWithPosition routeWithPosition : routeWithPositionList) {
            routeWithPosition.getRoute().setPositions(routeWithPosition.getPositions());
            routes.add(routeWithPosition.getRoute());
        }
        return routes;
    }

    public ERoute findByIdWithPositions(Long id) {
        RouteWithPosition routeWithPosition = getByIdWithPositions(id);
        ERoute eRoute = routeWithPosition.getRoute();
        eRoute.setPositions(routeWithPosition.getPositions());
        return eRoute;
    }

    public Long insertWithPositions(ERoute eRoute) {
        RouteWithPosition routeWithPosition = new RouteWithPosition(eRoute, eRoute.getPositions());
        _insertWithPositions(routeWithPosition);
        return eRoute.getId();
    }

    public List<Long> insertAllWithPositions(List<ERoute> eRoutes) {
        List<Long> idERoutes = new ArrayList<>();
        for (ERoute eRoute : eRoutes) {
            idERoutes.add(insertWithPositions(eRoute));
        }
        return idERoutes;
    }

    public void updateWithPositions(ERoute eRoute) {
        RouteWithPosition routeWithPosition = new RouteWithPosition(eRoute, eRoute.getPositions());
        _updateWithPositions(routeWithPosition);
    }

    public void updateAllWithPositions(List<ERoute> eRoutes) {
        for (ERoute eRoute : eRoutes) {
            updateWithPositions(eRoute);
        }
    }

    public void deleteWithPositions(ERoute eRoute) {
        RouteWithPosition routeWithPosition = new RouteWithPosition(eRoute, eRoute.getPositions());
        _deleteWithPositions(routeWithPosition);
    }

    public void deleteAllWithPositions(List<ERoute> eRoutes) {
        for (ERoute eRoute : eRoutes) {
            deleteWithPositions(eRoute);
        }
    }

}
