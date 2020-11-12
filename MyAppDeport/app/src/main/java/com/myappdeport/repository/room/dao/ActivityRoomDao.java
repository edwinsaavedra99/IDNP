package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.relationship.ActivityAndRoute;

import java.util.ArrayList;
import java.util.List;

@Dao
public abstract class ActivityRoomDao implements IRoomDao<EActivity, Long> {
    @Override
    @Query("SELECT * FROM EActivity")
    public abstract List<EActivity> findAll();

    @Override
    @Query("DELETE FROM EActivity")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(EActivity entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<EActivity> entities);

    @Override
    @Update
    public abstract void update(EActivity entity);

    @Override
    @Update
    public abstract void updateAll(List<EActivity> entities);

    @Override
    @Delete
    public abstract void delete(EActivity entity);

    @Override
    @Delete
    public abstract void delete(List<EActivity> entities);

    @Override
    @Query("SELECT * FROM EActivity WHERE id =:identifier")
    public abstract EActivity findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM EActivity")
    public abstract Integer count();

    @Transaction
    @Query("SELECT * FROM EActivity")
    public abstract List<ActivityAndRoute> _findAllWithRoute();

    @Transaction
    @Query("SELECT * FROM EActivity WHERE id =:identifier")
    protected abstract ActivityAndRoute _findByIdWithRoute(Long identifier);

    public List<EActivity> findAllWithRoute() {
        List<ActivityAndRoute> activityAndRoutes = _findAllWithRoute();
        List<EActivity> eActivities = new ArrayList<>();
        for (ActivityAndRoute activityAndRoute : activityAndRoutes) {
            EActivity eActivity = activityAndRoute.getEActivity();
            eActivity.setERoute(activityAndRoute.getERoute());
            eActivities.add(eActivity);
        }
        return eActivities;
    }

    public EActivity findByIdWithRoute(Long identifier) {
        ActivityAndRoute activityAndRoute = _findByIdWithRoute(identifier);
        EActivity eActivity = activityAndRoute.getEActivity();
        eActivity.setERoute(activityAndRoute.getERoute());
        return eActivity;
    }

    @Insert
    abstract Long _insertRoute(ERoute eRoute);

    @Update
    abstract void _updateRoute(ERoute eRoute);

    @Delete
    abstract void _deleteRoute(ERoute eRoute);

    public Long insertWithRoute(EActivity eActivity) {
        Long id = _insertRoute(eActivity.getERoute());
        eActivity.setIdERoute(id);
        return insert(eActivity);
    }

    public void updateWithRoute(EActivity eActivity) {
        _updateRoute(eActivity.getERoute());
        update(eActivity);
    }

    public void deleteWithRoute(EActivity eActivity) {
        _deleteRoute(eActivity.getERoute());
        delete(eActivity);
    }


}
