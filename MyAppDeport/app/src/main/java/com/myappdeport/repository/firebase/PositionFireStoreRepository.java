package com.myappdeport.repository.firebase;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.repository.IPositionRepository;

import java.util.List;
import java.util.Objects;

public class PositionFireStoreRepository extends FireStoreRepository<EPosition> implements IPositionRepository<String> {

    private static PositionFireStoreRepository INSTANCE;

    public synchronized static PositionFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new PositionFireStoreRepository();
        return INSTANCE;
    }

    private PositionFireStoreRepository() {
        super(EPosition.class);
        this.TAG = PositionFireStoreRepository.class.getSimpleName();
    }

    @Override
    public Task<List<EPosition>> findByIdERoute(String routeDocumentId) {
        return this.collectionReference.whereEqualTo("eRouteDocumentId", routeDocumentId).get().continueWithTask(task -> Tasks.forResult(Objects.requireNonNull(task.getResult()).toObjects(entityClass)));
    }
}
