package com.myappdeport.repository;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IRepository;

import java.util.Optional;

public interface IRouteRepository<I> extends IRepository<ERoute, I> {
    Task<Optional<ERoute>> findByIdWithPositions(I id);

    Task<ERoute> saveWithPositions(ERoute eRoute);

    Task<Void> updateWithPositions(ERoute eRoute);

    Task<Void> deleteWithPositions(ERoute eRoute);
}
