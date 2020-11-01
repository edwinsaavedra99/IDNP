package com.myappdeport.model.entity.database;


import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class EUser implements Serializable {
    public String uid;
    public String name;
    @SuppressWarnings("WeakerAccess")
    public String email;
    @Exclude
    public boolean isAuthenticated;
    @Exclude
    public
    boolean isNew;
    @Exclude
    public
    boolean isCreated;

    public EUser() {}

    public EUser(String uid, String name, String email) {
        this.uid = uid;
        this.name = name;
        this.email = email;
    }
}