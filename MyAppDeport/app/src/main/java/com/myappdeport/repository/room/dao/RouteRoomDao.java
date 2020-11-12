package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.relationship.ActivityAndRoute;
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

    @Transaction
    @Query("SELECT * FROM ERoute")
    protected abstract List<RouteWithPosition> _findAllWithPositions();

    @Transaction
    @Query("SELECT * FROM ERoute WHERE id =:identifier")
    protected abstract RouteWithPosition _findByIdWithPositions(Long identifier);

    public List<ERoute> findAllWithPositions() {
        List<RouteWithPosition> routeWithPositions = _findAllWithPositions();
        List<ERoute> eRoutes = new ArrayList<>();
        for (RouteWithPosition routeWithPosition : routeWithPositions) {
            ERoute eRoute = routeWithPosition.getRoute();
            eRoute.setPositions(routeWithPosition.getPositions());
            eRoutes.add(eRoute);
        }
        return eRoutes;
    }

    public ERoute findByIdWithPositions(Long identifier) {
        RouteWithPosition routeWithPosition = _findByIdWithPositions(identifier);
        ERoute eRoute = routeWithPosition.getRoute();
        eRoute.setPositions(routeWithPosition.getPositions());
        return eRoute;
    }

    @Insert
    abstract void _insertAllPositions(List<EPosition> ePositions);

    @Update
    abstract void _updateAllPositions(List<EPosition> ePositions);

    @Delete
    abstract void _deleteAllPositions(List<EPosition> ePositions);

    public Long insertWithPositions(ERoute eRoute) {
        Long id = insert(eRoute);
        for (EPosition ePosition : eRoute.getPositions()) {
            ePosition.setId(id);
        }
        _insertAllPositions(eRoute.getPositions());
        return id;
    }

    public void updateWithPositions(ERoute eRoute) {
        update(eRoute);
        _updateAllPositions(eRoute.getPositions());
    }

    public void deleteWithPositions(ERoute eRoute) {
        delete(eRoute);
        _deleteAllPositions(eRoute.getPositions());
    }
}
