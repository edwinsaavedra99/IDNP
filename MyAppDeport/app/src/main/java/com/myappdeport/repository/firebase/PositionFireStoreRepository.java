package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.EPosition;

public class PositionFireStoreRepository extends FireStoreRepository<EPosition> {

    public PositionFireStoreRepository() {
        super(EPosition.class);
        this.TAG = PositionFireStoreRepository.class.getSimpleName();
    }
}
