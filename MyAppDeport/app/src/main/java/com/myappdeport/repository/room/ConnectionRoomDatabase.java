package com.myappdeport.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.model.entity.database.ESong;
import com.myappdeport.repository.room.dao.ActivityRoomDao;
import com.myappdeport.repository.room.dao.PositionRoomDao;
import com.myappdeport.repository.room.dao.RouteRoomDao;
import com.myappdeport.repository.room.dao.SongRoomDao;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {EActivity.class, EPosition.class, ERoute.class, ESong.class}, version = 1, exportSchema = false)
public abstract class ConnectionRoomDatabase extends RoomDatabase {
    public abstract PositionRoomDao getPointRoomDao();

    public abstract RouteRoomDao getRouteRoomDao();

    public abstract ActivityRoomDao getActivityRoomDao();

    public abstract SongRoomDao getSongRoomDao();

    private static volatile ConnectionRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    private static final String DATABASE_NAME = "room_database_testing";
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static ConnectionRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ConnectionRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room
                            .databaseBuilder(context.getApplicationContext(), ConnectionRoomDatabase.class, DATABASE_NAME)
                            /*
                             * Permite hacer pruebas desde el hilo principal.
                             */
                            .allowMainThreadQueries()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
