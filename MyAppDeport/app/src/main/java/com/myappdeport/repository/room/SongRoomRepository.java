package com.myappdeport.repository.room;

import android.content.Context;

import com.myappdeport.model.entity.database.ESong;
import com.myappdeport.repository.IRepository;
import com.myappdeport.repository.room.dao.SongRoomDao;

public class SongRoomRepository extends RoomRepository<ESong, SongRoomDao> implements IRepository<ESong, Long> {
    private static SongRoomRepository INSTANCE;

    public synchronized static SongRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new SongRoomRepository(context);
        return INSTANCE;
    }

    protected SongRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getSongRoomDao());
    }
}
