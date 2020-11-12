package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.ENutritionalAdvice;

public class NutritionalAdviceFireStoreRepository extends FireStoreRepository<ENutritionalAdvice> {
    private static NutritionalAdviceFireStoreRepository INSTANCE;

    public synchronized static NutritionalAdviceFireStoreRepository getInstance() {
        if (INSTANCE == null)
            INSTANCE = new NutritionalAdviceFireStoreRepository();
        return INSTANCE;
    }

    private NutritionalAdviceFireStoreRepository() {
        super(ENutritionalAdvice.class);
        this.TAG = NutritionalAdviceFireStoreRepository.class.getSimpleName();
    }
}
