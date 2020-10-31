package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.EActivity;

public class ActivityFireStoreRepository extends FireStoreRepository<EActivity> {

    public ActivityFireStoreRepository() {
        super(EActivity.class);
        super.TAG = ActivityFireStoreRepository.class.getSimpleName();
    }
}
