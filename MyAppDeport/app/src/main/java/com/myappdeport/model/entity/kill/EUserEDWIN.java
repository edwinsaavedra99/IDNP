package com.myappdeport.model.entity.kill;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class EUserEDWIN implements Serializable {
    public String uid;
    public String name;

    @SuppressWarnings("WeakerAccess")
    public String email;
    @Exclude
    public boolean isAuthenticated;
    @Exclude
    public boolean isNew;
    @Exclude
    public boolean isCreated;
    @Exclude
    public boolean isError;
    @Exclude
    public String password;

    public EUserEDWIN() {
    }

    public EUserEDWIN(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}