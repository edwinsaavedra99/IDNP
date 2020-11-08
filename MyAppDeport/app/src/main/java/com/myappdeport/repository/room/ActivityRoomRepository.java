package com.myappdeport.repository.room;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.firebase.FireStoreRepository;
import com.myappdeport.repository.room.dao.ActivityRoomDao;

import java.util.Optional;

public class ActivityRoomRepository extends RoomRepository<EActivity, ActivityRoomDao> implements IActivityRepository<Long> {

    private RouteRoomRepository routeRoomRepository;

    protected ActivityRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getActivityRoomDao());
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRoute(Long id) {
        return Tasks.call(() -> {
            return Optional.of(this.roomDao.findByIdWithRoute(id));
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRouteAndPositions(Long id) {
        return Tasks.call(() -> {
            EActivity eActivity = this.roomDao.findByIdWithRoute(id);
            ERoute eRoute = this.routeRoomRepository.roomDao.findByIdWithPositions(eActivity.getERoute().getId());
            eActivity.setERoute(eRoute);
            return Optional.of(eActivity);
        });
    }

    @Override
    public Task<EActivity> saveWithRoute(EActivity eActivity) throws InstantiationException {
        return Tasks.call(() -> {
            Long id = this.roomDao.insertWithRoute(eActivity);
            eActivity.setId(id);
            return eActivity;
        });
    }

    @Override
    public Task<EActivity> saveWithRouteAndPositions(EActivity eActivity) throws IllegalAccessException, InstantiationException {
        return null;
    }

    @Override
    public Task<Void> updateWithRouteAndPositions(EActivity eActivity) throws IllegalAccessException {
        return null;
    }

    @Override
    public Task<Void> updateWithRoute(EActivity eActivity) {
        return Tasks.call(() -> {
            this.roomDao.updateWithRoute(eActivity);
            return null;
        });
    }

    @Override
    public Task<Void> deleteWithRoute(EActivity eActivity) {
        return Tasks.call(() -> {
            this.roomDao.deleteWithRoute(eActivity);
            return null;
        });
    }

    @Override
    public Task<Void> deleteWithRouteAndPositions(EActivity eActivity) {
        return null;
    }
}
