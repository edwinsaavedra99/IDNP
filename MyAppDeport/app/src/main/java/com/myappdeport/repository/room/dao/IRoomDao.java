package com.myappdeport.repository.room.dao;

import com.myappdeport.model.entity.database.EntityDatabase;

import java.util.List;

public interface IRoomDao<E extends EntityDatabase, I> {

    I insert(E entity);

    List<I> insertAll(List<E> entities);

    void update(E entity);

    void update(List<E> entities);

    void delete(E entity);

    void delete(List<E> entities);

    List<E> findAll();

    E findById(I identifier);

    Integer count();

    void deleteAll();
}
