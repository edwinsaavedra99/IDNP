package com.myappdeport.repository.firebase;

import com.myappdeport.model.entity.database.ENutritionalAdvice;

public class NutritionalAdviceFireStoreRepository extends FireStoreRepository<ENutritionalAdvice> {
    /**
     * Falta gestionar el guardado de imagenes con archivos o URLs
     */
    public NutritionalAdviceFireStoreRepository() {
        super(ENutritionalAdvice.class);
        super.TAG = NutritionalAdviceFireStoreRepository.class.getSimpleName();
    }
}
