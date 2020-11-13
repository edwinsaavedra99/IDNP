package com.myappdeport.model.entity.database;

import androidx.room.Entity;

import com.google.firebase.firestore.Exclude;

import java.util.List;

import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EUser extends EntityDatabase {
    public String name;
    public String email;
    @Exclude
    public Boolean isNew;
    @Exclude
    public Boolean isCreated;
    @Exclude
    public Boolean isAuthenticated;
    @Exclude
    public List<EActivity> eActivityList;

    public EUser(String name, String email, Boolean isNew, Boolean isCreated, Boolean isAuthenticated) {
        this.name = name;
        this.email = email;
        this.isNew = isNew;
        this.isCreated = isCreated;
        this.isAuthenticated = isAuthenticated;
    }

    public EUser() {
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public Boolean getIsNew() {
        return this.isNew;
    }

    public Boolean getIsCreated() {
        return this.isCreated;
    }

    public Boolean getIsAuthenticated() {
        return this.isAuthenticated;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setIsNew(Boolean isNew) {
        this.isNew = isNew;
    }

    public void setIsCreated(Boolean isCreated) {
        this.isCreated = isCreated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }
}
