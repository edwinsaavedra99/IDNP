package com.myappdeport.repository.room;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.repository.IPositionRepository;
import com.myappdeport.repository.room.dao.PositionRoomDao;

import java.util.List;

public class PositionRoomRepository extends RoomRepository<EPosition, PositionRoomDao> implements IPositionRepository<Long> {
    private static PositionRoomRepository INSTANCE;

    public synchronized static PositionRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new PositionRoomRepository(context);
        return INSTANCE;
    }

    protected PositionRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getPositionRoomDao());
    }

    /**
     * Obtener posiciones por sus ids.
     *
     * @param id_s Son las id con las cuales extraeremos las posiciones.
     * @return La tarea de obtención de posiciones.
     */
    @Override
    public Task<List<EPosition>> findByIds(List<Long> id_s) {
        return Tasks.call(() -> {
            return roomDao.findByIds(id_s);
        });
    }
}
