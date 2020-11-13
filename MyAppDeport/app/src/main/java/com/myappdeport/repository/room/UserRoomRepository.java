package com.myappdeport.repository.room;

import android.content.Context;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.firebase.ActivityFireStoreRepository;
import com.myappdeport.repository.firebase.RouteFireStoreRepository;
import com.myappdeport.repository.room.dao.UserRoomDao;

public class UserRoomRepository extends RoomRepository<EUser, UserRoomDao> {
    private static UserRoomRepository INSTANCE;

    public synchronized static UserRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new UserRoomRepository(context);
        return INSTANCE;
    }

    protected UserRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getUserRoomDao());
    }

}
