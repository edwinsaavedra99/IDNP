package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.relationship.ActivityAndRoute;
import com.myappdeport.model.entity.database.relationship.RouteWithPosition;

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

    @Insert
    public abstract void _insertWithRoute(ActivityAndRoute activityAndRoute);

    @Update
    public abstract void _updateWithRoute(ActivityAndRoute activityAndRoute);

    @Query("SELECT * FROM EActivity")
    public abstract List<ActivityAndRoute> _findAllWithRoute();

    @Delete
    protected abstract void _deleteWithRoute(ActivityAndRoute activityAndRoute);

    @Query("SELECT * FROM EActivity WHERE id =:identifier")
    protected abstract ActivityAndRoute getByIdWithRoute(Long identifier);

    public List<EActivity> findAllWithRoute() {
        List<ActivityAndRoute> activityAndRoutes = _findAllWithRoute();
        List<EActivity> eActivities = new ArrayList<>();
        for (ActivityAndRoute activityAndRoute : activityAndRoutes) {
            activityAndRoute.getEActivity().setERoute(activityAndRoute.getERoute());
            eActivities.add(activityAndRoute.getEActivity());
        }
        return eActivities;
    }

    public EActivity findByIdWithRoute(Long id) {
        ActivityAndRoute activityAndRoute = getByIdWithRoute(id);
        EActivity eActivity = activityAndRoute.getEActivity();
        eActivity.setERoute(activityAndRoute.getERoute());
        return eActivity;
    }

    public Long insertWithRoute(EActivity eActivity) {
        ActivityAndRoute activityAndRoute = new ActivityAndRoute(eActivity, eActivity.getERoute());
        _insertWithRoute(activityAndRoute);
        return eActivity.getId();
    }

    public List<Long> insertAllWithRoute(List<EActivity> eActivities) {
        List<Long> idEActivities = new ArrayList<>();
        for (EActivity eActivity : eActivities) {
            idEActivities.add(insertWithRoute(eActivity));
        }
        return idEActivities;
    }

    public void updateWithRoute(EActivity eActivity) {
        ActivityAndRoute activityAndRoute = new ActivityAndRoute(eActivity, eActivity.getERoute());
        _updateWithRoute(activityAndRoute);
    }

    public void updateAllWithRoute(List<EActivity> eActivities) {
        for (EActivity eActivity : eActivities) {
            updateWithRoute(eActivity);
        }
    }

    public void deleteWithRoute(EActivity eActivity) {
        ActivityAndRoute activityAndRoute = new ActivityAndRoute(eActivity, eActivity.getERoute());
        _deleteWithRoute(activityAndRoute);
    }

    public void deleteAllWithRoute(List<EActivity> eActivities) {
        for (EActivity eActivity : eActivities) {
            deleteWithRoute(eActivity);
        }
    }
}
