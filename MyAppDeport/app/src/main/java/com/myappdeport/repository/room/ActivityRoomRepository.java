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
            EActivity eActivity = this.roomDao.findByIdWithRoute(id);
            ERoute eRoute = this.routeRoomRepository.roomDao.findByIdWithPositions(eActivity.getERoute().getId());
            eActivity.setERoute(eRoute);
            return Optional.of(eActivity);
        });
    }

    @Override
    public Task<EActivity> saveWithRoute(EActivity eActivity) {
        return Tasks.call(() -> {
            Long id = this.roomDao.insertWithRoute(eActivity);
            eActivity.setId(id);
            return eActivity;
        });
    }

    @Override
    public Task<EActivity> saveWithRouteAndPositions(EActivity eActivity) {
        return this.routeRoomRepository.saveWithPositions(eActivity.getERoute()).continueWithTask(task -> Tasks.call(() -> {
            eActivity.setId(this.roomDao.insertWithRoute(eActivity));
            return eActivity;
        }));
    }

    @Override
    public Task<Void> updateWithRouteAndPositions(EActivity eActivity) {
        return this.routeRoomRepository.updateWithPositions(eActivity.getERoute()).continueWithTask(task -> Tasks.call(() -> {
            this.roomDao.updateWithRoute(eActivity);
            return null;
        }));
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
        return this.routeRoomRepository.deleteWithPositions(eActivity.getERoute()).continueWithTask(task -> {
            this.roomDao.deleteWithRoute(eActivity);
            return null;
        });
    }
}
