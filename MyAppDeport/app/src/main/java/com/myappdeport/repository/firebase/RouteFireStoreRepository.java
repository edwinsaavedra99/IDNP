package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.ERoute;

public class RouteFireStoreRepository extends FireStoreRepository<ERoute> {
    public RouteFireStoreRepository() {
        super(ERoute.class);
        this.TAG = RouteFireStoreRepository.class.getSimpleName();
    }
}
