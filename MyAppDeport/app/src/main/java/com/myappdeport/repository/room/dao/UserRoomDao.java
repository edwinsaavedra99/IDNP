package com.myappdeport.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.model.entity.database.relationship.UserWithActivity;

import java.util.List;

@Dao
public abstract class UserRoomDao implements IRoomDao<EUser, Long> {
    @Override
    @Query("SELECT * FROM EUser")
    public abstract List<EUser> findAll();

    @Override
    @Query("DELETE FROM EUser")
    public abstract void deleteAll();

    @Override
    @Insert
    public abstract Long insert(EUser entity);

    @Override
    @Insert
    public abstract List<Long> insertAll(List<EUser> entities);

    @Override
    @Update
    public abstract void update(EUser entity);

    @Override
    @Update
    public abstract void updateAll(List<EUser> entities);

    @Override
    @Delete
    public abstract void delete(EUser entity);

    @Override
    @Delete
    public abstract void delete(List<EUser> entities);

    @Override
    @Query("SELECT * FROM EUser WHERE id =:identifier")
    public abstract EUser findById(Long identifier);

    @Override
    @Query("SELECT COUNT(*) FROM EUser")
    public abstract Integer count();

    @Insert
    public abstract void _insertAllActivity(List<EActivity> eActivities);

    @Update
    public abstract void _updateAllActivity(List<EActivity> eActivities);

    @Delete
    public abstract void _deleteAllActivity(List<EActivity> eActivities);

    public void deleteUserWithActivity(EUser eUser) {
        delete(eUser);
        _deleteAllActivity(eUser.getEActivityList());
    }

    public void updateUserWithActivity(EUser eUser) {
        update(eUser);
        _updateAllActivity(eUser.getEActivityList());
    }

    public EUser insertUserWithActivity(EUser eUser) {
        Long id = insert(eUser);
        for (EActivity eActivity : eUser.getEActivityList()) {
            eActivity.setIdEUser(id);
        }
        _insertAllActivity(eUser.getEActivityList());
        return eUser;
    }

    public EUser findByEmailWithActivity(String email) {
        UserWithActivity userWithActivity = _findByEmailWithActivity(email);
        EUser eUser = userWithActivity.getEUser();
        eUser.setEActivityList(userWithActivity.getEActivityList());
        return eUser;
    }

    @Query("SELECT * FROM EUser WHERE email =:email")
    public abstract EUser findByEmail(String email);

    @Transaction
    @Query("SELECT * FROM EUser WHERE email =:email")
    public abstract UserWithActivity _findByEmailWithActivity(String email);

}
