package com.myappdeport.repository.firebase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.Query;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.viewmodel.firebase.ActivityListLiveData;
import com.myappdeport.viewmodel.firebase.ActivityListUserLiveData;

import java.util.List;
import java.util.Optional;

public class ActivityFireStoreRepository extends FireStoreRepository<EActivity> implements IActivityRepository<String> {
    private static ActivityFireStoreRepository INSTANCE;
    private final RouteFireStoreRepository routeFireStoreRepository;

    public synchronized static ActivityFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new ActivityFireStoreRepository();
        return INSTANCE;
    }

    private ActivityFireStoreRepository() {
        super(EActivity.class);
        this.TAG = ActivityFireStoreRepository.class.getSimpleName();
        this.routeFireStoreRepository = RouteFireStoreRepository.getInstance();
    }

    @Override
    public Task<List<EActivity>> getActivityByIdUser(String idUser) {
        return this.collectionReference.whereEqualTo("userDocumentId", idUser).get().onSuccessTask(queryDocumentSnapshots -> Tasks.forResult(queryDocumentSnapshots.toObjects(entityClass)));
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRoute(String documentId) {
        return this.findById(documentId).continueWithTask(task -> {
            task.getResult().ifPresent(eActivity -> routeFireStoreRepository.findById(documentId).getResult().ifPresent(eActivity::setERoute));
            return task;
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Optional<EActivity>> getActivityWithRouteAndPositions(String documentId) {
        return this.findById(documentId).continueWithTask(task -> {
            task.getResult().ifPresent(eActivity -> routeFireStoreRepository.findByIdWithPositions(documentId).getResult().ifPresent(eActivity::setERoute));
            return task;
        });
    }

    @Override
    public Task<EActivity> saveWithRoute(EActivity eActivity) {
        return this.routeFireStoreRepository.save(eActivity.getERoute()).onSuccessTask(eRoute -> {
            eActivity.setERoute(eRoute);
            eActivity.setRouteDocumentId(eRoute.getDocumentId());
            return save(eActivity);
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<EActivity> saveWithRouteAndPositions(EActivity eActivity) {
        return this.routeFireStoreRepository.saveWithPositions(eActivity.getERoute()).onSuccessTask(eRoute -> {
            eActivity.setERoute(eRoute);
            eActivity.setRouteDocumentId(eRoute.getDocumentId());
            return save(eActivity);
        });
    }

    @Override
    @RequiresApi(api = Build.VERSION_CODES.N)
    public Task<Void> updateWithRouteAndPositions(EActivity eActivity) {
        return this.routeFireStoreRepository.updateWithPositions(eActivity.getERoute()).onSuccessTask(aVoid -> update(eActivity));
    }

    @Override
    public Task<Void> updateWithRoute(EActivity eActivity) {
        return this.routeFireStoreRepository.update(eActivity.getERoute()).onSuccessTask(aVoid -> update(eActivity));
    }

    @Override
    public Task<Void> deleteWithRoute(EActivity eActivity) {
        return this.routeFireStoreRepository.delete(eActivity.getERoute()).onSuccessTask(voids -> delete(eActivity));
    }

    @Override
    public Task<Void> deleteWithRouteAndPositions(EActivity eActivity) {
        return this.routeFireStoreRepository.deleteWithPositions(eActivity.getERoute()).onSuccessTask(voids -> delete(eActivity));
    }

    public ActivityListLiveData getActivityListLiveData() {
        CollectionReference collectionReference = this.collectionReference;
        return new ActivityListLiveData(collectionReference);
    }

    public ActivityListUserLiveData getActivityListLiveDataByIdUser(String userDocumentId) {
        Query query = this.collectionReference.whereEqualTo("userDocumentId", userDocumentId);
        return new ActivityListUserLiveData(query);
    }
}
