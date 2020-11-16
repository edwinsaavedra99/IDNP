package com.myappdeport.repository.firebase;

import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.myappdeport.model.entity.database.EUser;
import com.myappdeport.repository.IUserRepository;

public class UserFireStoreRepository extends FireStoreRepository<EUser> implements IUserRepository<String> {
    private static UserFireStoreRepository INSTANCE;
    private final ActivityFireStoreRepository activityFireStoreRepository;

    public synchronized static UserFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new UserFireStoreRepository();
        return INSTANCE;
    }

    private UserFireStoreRepository() {
        super(EUser.class);
        this.TAG = UserFireStoreRepository.class.getSimpleName();
        this.activityFireStoreRepository = ActivityFireStoreRepository.getInstance();
    }

    @Override
    public Task<EUser> getByEmail(String email) {
        return this.collectionReference.whereEqualTo("email", email).get().onSuccessTask(queryDocumentSnapshots -> Tasks.call(() -> queryDocumentSnapshots.getDocuments().get(0).toObject(entityClass)));
    }

    @Override
    public Task<EUser> getByEmailWithActivity(String email) {
        return getByEmail(email).continueWithTask(task -> this.activityFireStoreRepository.getActivityByIdUser(email).onSuccessTask(eActivities -> {
            task.getResult().setEActivityList(eActivities);
            return task;
        }));
    }
}
