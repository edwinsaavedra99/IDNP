package com.myappdeport.repository.room;

import android.content.Context;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.IUserRepository;
import com.myappdeport.repository.room.dao.UserRoomDao;

public class UserRoomRepository extends RoomRepository<EUser, UserRoomDao> implements IUserRepository<Long> {
    private static UserRoomRepository INSTANCE;

    public synchronized static UserRoomRepository getInstance(Context context) {
        if (INSTANCE == null)
            INSTANCE = new UserRoomRepository(context);
        return INSTANCE;
    }

    protected UserRoomRepository(Context context) {
        super(ConnectionRoomDatabase.getDatabase(context).getUserRoomDao());
    }

    @Override
    public Task<EUser> getByEmail(String email) {
        return Tasks.call(() -> this.roomDao.findByEmail(email));
    }

    @Override
    public Task<EUser> getByEmailWithActivity(String email) {
        return Tasks.call(() -> this.roomDao.findByEmailWithActivity(email));
    }
}
