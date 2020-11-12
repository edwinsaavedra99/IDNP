package com.myappdeport.repository.room;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.room.dao.ActivityRoomDao;

import java.util.Optional;

public class ActivityRoomRepository extends RoomRepository<EActivity, ActivityRoomDao> implements IActivityRepository<Long> {

    private static ActivityRoomRepository INSTANCE;
    private final RouteRoomRepository routeRoomRepository;

    public synchronized static ActivityRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new ActivityRoomRepository(context);
        return INSTANCE;
    }

    protected ActivityRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getActivityRoomDao());
        this.routeRoomRepository = RouteRoomRepository.getInstance(context);
    }


    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRoute(Long id) {
        return Tasks.call(() -> Optional.of(this.roomDao.findByIdWithRoute(id)));
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRouteAndPositions(Long id) {
        return Tasks.call(() -> {
            EActivity eActivity = this.roomDao.findById(id);
            ERoute eRoute = this.routeRoomRepository.roomDao.findByIdWithPositions(eActivity.getIdERoute());
            eActivity.setERoute(eRoute);
            return Optional.of(eActivity);
        });
    }

    @Override
    public Task<EActivity> saveWithRoute(EActivity eActivity) {
        return Tasks.call(() -> {
            this.roomDao.insertWithRoute(eActivity);
            return eActivity;
        });
    }

    @Override
    public Task<EActivity> saveWithRouteAndPositions(EActivity eActivity) {
        return Tasks.call(() -> {
            Long idRoute = this.routeRoomRepository.roomDao.insertWithPositions(eActivity.getERoute());
            eActivity.setIdERoute(idRoute);
            Long id = roomDao.insert(eActivity);
            eActivity.setId(id);
            return eActivity;
        });
    }

    @Override
    public Task<Void> updateWithRouteAndPositions(EActivity eActivity) {
        return Tasks.call(() -> {
            this.roomDao.update(eActivity);
            this.routeRoomRepository.roomDao.updateWithPositions(eActivity.getERoute());
            return null;
        });
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
        return Tasks.call(() -> {
            this.roomDao.delete(eActivity);
            this.routeRoomRepository.roomDao.deleteWithPositions(eActivity.getERoute());
            return null;
        });
    }
}
