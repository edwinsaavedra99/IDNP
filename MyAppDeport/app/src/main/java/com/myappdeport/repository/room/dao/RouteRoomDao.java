package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.ERoute;

import java.util.List;

@Dao
public abstract class RouteRoomDao implements IRoomDao<ERoute, Long> {
    @Override
    @Query("SELECT * FROM ERoute")
    public abstract List<ERoute> findAll();

    @Override
    @Query("DELETE FROM ERoute")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(ERoute entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<ERoute> entities);

    @Override
    @Update
    public abstract void update(ERoute entity);

    @Override
    @Update
    public abstract void update(List<ERoute> entities);

    @Override
    @Delete
    public abstract void delete(ERoute entity);

    @Override
    @Delete
    public abstract void delete(List<ERoute> entities);

    @Override
    @Query("SELECT * FROM ERoute WHERE id =:identifier")
    public abstract ERoute findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM ERoute")
    public abstract Integer count();
}
