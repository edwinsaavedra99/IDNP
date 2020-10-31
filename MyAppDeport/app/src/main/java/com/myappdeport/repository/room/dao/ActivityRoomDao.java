package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EActivity;

import java.util.List;

@Dao
public abstract class ActivityRoomDao implements IRoomDao<EActivity, Long> {
    @Override
    @Query("SELECT * FROM EActivity")
    public abstract List<EActivity> findAll();

    @Override
    @Query("DELETE FROM EActivity")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(EActivity entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<EActivity> entities);

    @Override
    @Update
    public abstract void update(EActivity entity);

    @Override
    @Update
    public abstract void update(List<EActivity> entities);

    @Override
    @Delete
    public abstract void delete(EActivity entity);

    @Override
    @Delete
    public abstract void delete(List<EActivity> entities);

    @Override
    @Query("SELECT * FROM EActivity WHERE id =:identifier")
    public abstract EActivity findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM EActivity")
    public abstract Integer count();
}
