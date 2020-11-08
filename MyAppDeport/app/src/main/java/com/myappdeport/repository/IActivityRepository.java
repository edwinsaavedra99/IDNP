package com.myappdeport.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.repository.IRepository;

import java.util.Optional;

public interface IActivityRepository<I> extends IRepository<EActivity, I> {
    Task<Optional<EActivity>> getActivityWithRoute(I id);

    Task<Optional<EActivity>> getActivityWithRouteAndPositions(I id);

    Task<EActivity> saveWithRoute(EActivity eActivity) throws InstantiationException;

    Task<EActivity> saveWithRouteAndPositions(EActivity eActivity) throws IllegalAccessException, InstantiationException;

    Task<Void> updateWithRouteAndPositions(EActivity eActivity) throws IllegalAccessException;

    Task<Void> updateWithRoute(EActivity eActivity);

    Task<Void> deleteWithRoute(EActivity eActivity);

    Task<Void> deleteWithRouteAndPositions(EActivity eActivity);
}
