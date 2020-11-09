package com.myappdeport.repository.room;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.room.dao.RouteRoomDao;

import java.util.Optional;

public class RouteRoomRepository extends RoomRepository<ERoute, RouteRoomDao> implements IRouteRepository<Long> {
    private static RouteRoomRepository INSTANCE;

    public synchronized static RouteRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new RouteRoomRepository(context);
        return INSTANCE;
    }

    protected RouteRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getRouteRoomDao());
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<ERoute>> findByIdWithPositions(Long id) {
        return Tasks.call(() -> Optional.of(this.roomDao.findByIdWithPositions(id)));
    }

    @Override
    public Task<ERoute> saveWithPositions(ERoute eRoute) {
        return Tasks.call(() -> {
            eRoute.setId(this.roomDao.insertWithPositions(eRoute));
            return eRoute;
        });
    }

    @Override
    public Task<Void> updateWithPositions(ERoute eRoute) {
        return Tasks.call(() -> {
            this.roomDao.updateWithPositions(eRoute);
            return null;
        });
    }

    @Override
    public Task<Void> deleteWithPositions(ERoute eRoute) {
        return Tasks.call(() -> {
            this.roomDao.deleteWithPositions(eRoute);
            return null;
        });
    }
}
