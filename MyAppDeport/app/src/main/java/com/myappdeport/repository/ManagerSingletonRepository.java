package com.myappdeport.repository;

import androidx.annotation.NonNull;

import com.myappdeport.model.entity.database.firebase.FirebaseEntity;

import java.util.HashMap;
import java.util.Map;

public class ManagerSingletonRepository implements Cloneable {

    private static final Map<Class<? extends FirebaseEntity>, FireStoreRepository<? extends FirebaseEntity>> INSTANCES_MAP = new HashMap<>();

    private ManagerSingletonRepository() {
    }

    public static <E extends FirebaseEntity> FireStoreRepository<E> getInstance(Class<E> instanceClass) throws InstantiationException, IllegalAccessException {
        if (INSTANCES_MAP.containsKey(instanceClass)) {
            //noinspection unchecked
            return (FireStoreRepository<E>) INSTANCES_MAP.get(instanceClass);
        } else {
            FireStoreRepository<E> instance = new FireStoreRepository<E>(instanceClass);
            INSTANCES_MAP.put(instanceClass, instance);
            return instance;
        }
    }

    @NonNull
    @Override
    public ManagerSingletonRepository clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Can't clone this object.(This is a singleton)");
    }
}
