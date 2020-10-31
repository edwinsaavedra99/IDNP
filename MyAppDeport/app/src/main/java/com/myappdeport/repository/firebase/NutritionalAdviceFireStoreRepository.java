package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.ENutritionalAdvice;

public class NutritionalAdviceFireStoreRepository extends FireStoreRepository<ENutritionalAdvice> {
    public NutritionalAdviceFireStoreRepository() {
        super(ENutritionalAdvice.class);
        super.TAG = NutritionalAdviceFireStoreRepository.class.getSimpleName();
    }
}
