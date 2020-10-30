package com.myappdeport.repository.room;

import android.content.Context;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.EntityDatabase;
import com.myappdeport.repository.room.dao.IRoomDao;

public abstract class FactoryDaoRoom {

    @SuppressWarnings("unchecked")
    public static <E extends EntityDatabase, I> IRoomDao<E, I> getRoomDao(Class<E> entityClass, Context context) {
        ConnectionRoomDatabase connectionRoomDatabase = ConnectionRoomDatabase.getDatabase(context);
        if (entityClass.getSimpleName().equals(EPosition.class.getSimpleName())) {
            return (IRoomDao<E, I>) connectionRoomDatabase.getPointRoomDao();
        } else if (entityClass.getSimpleName().equals(EActivity.class.getSimpleName())) {
            return (IRoomDao<E, I>) connectionRoomDatabase.getActivityRoomDao();
        } else if (entityClass.getSimpleName().equals(ERoute.class.getSimpleName())) {
            return (IRoomDao<E, I>) connectionRoomDatabase.getRouteRoomDao();
        } else {
            return null;
        }
    }
}
