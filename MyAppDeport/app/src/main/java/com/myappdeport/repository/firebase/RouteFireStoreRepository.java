package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.ERoute;

import java.util.Optional;

public class RouteFireStoreRepository extends FireStoreRepository<ERoute> {

    private static RouteFireStoreRepository INSTANCE;
    private final PositionFireStoreRepository positionRepository;

    public synchronized static RouteFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new RouteFireStoreRepository();
        return INSTANCE;
    }

    private RouteFireStoreRepository() {
        super(ERoute.class);
        this.TAG = RouteFireStoreRepository.class.getSimpleName();
        this.positionRepository = PositionFireStoreRepository.getInstance();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<ERoute>> getRouteWithPositions(String documentId) {
        return this.findById(documentId).continueWithTask(
                task -> {
                    task.getResult().ifPresent(eRoute -> eRoute.setPositions(positionRepository.findByIds(eRoute.getPositionDocumentIds()).getResult()));
                    return task;
                }
        );
    }
}
