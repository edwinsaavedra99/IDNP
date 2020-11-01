package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.ESong;

import java.util.List;

@Dao
public abstract class SongRoomDao implements IRoomDao<ESong, Long> {
    @Override
    @Query("SELECT * FROM ESong")
    public abstract List<ESong> findAll();

    @Override
    @Query("DELETE FROM ESong")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(ESong entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<ESong> entities);

    @Override
    @Update
    public abstract void update(ESong entity);

    @Override
    @Update
    public abstract void update(List<ESong> entities);

    @Override
    @Delete
    public abstract void delete(ESong entity);

    @Override
    @Delete
    public abstract void delete(List<ESong> entities);

    @Override
    @Query("SELECT * FROM ESong WHERE id =:identifier")
    public abstract ESong findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM ESong")
    public abstract Integer count();
}
