package com.myappdeport.model.entity.kill;


import android.net.Uri;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;

public class EUserEDWIN implements Serializable {

    public String uid;
    public String name;
    public String fechaNacimiento;
    public String altura;
    public String edad;
    public String peso;
    public Uri photoUrl;

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

    public EUserEDWIN(String uid, String name, String email,String edad,String peso,String altura,String fechaNacimiento,Uri photoUrl) {
        this.uid = uid;
        this.name = name;
        this.email = email;
        this.edad =edad;
        this.peso = peso;
        this.altura = altura;
        this.fechaNacimiento = fechaNacimiento;
        this.photoUrl = photoUrl;
    }
}