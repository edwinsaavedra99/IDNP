package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.repository.room.dao.IRoomDao;

import java.util.List;

@Dao
public abstract class PositionRoomDao implements IRoomDao<EPosition, Long> {

    @Override
    @Query("SELECT * FROM EPosition")
    public abstract List<EPosition> findAll();

    @Override
    @Query("DELETE FROM EPosition")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(EPosition entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<EPosition> entities);

    @Override
    @Update
    public abstract void update(EPosition entity);

    @Override
    @Update
    public abstract void update(List<EPosition> entities);

    @Override
    @Delete
    public abstract void delete(EPosition entity);

    @Override
    @Delete
    public abstract void delete(List<EPosition> entities);

    @Override
    @Query("SELECT * FROM EPosition WHERE id =:identifier")
    public abstract EPosition findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM EPosition")
    public abstract Integer count();
}
