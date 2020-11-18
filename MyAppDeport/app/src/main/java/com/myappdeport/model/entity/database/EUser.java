package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@Entity(indices = @Index("email"))
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EUser extends EntityDatabase {
    private String name;
    private String email;
    private String birthday;
    private String weight;
    private Integer age;
    private Double height;
    @Exclude
    @Ignore
    public Boolean isNew;
    @Exclude
    @Ignore
    public Boolean isCreated;
    @Exclude
    @Ignore
    public Boolean isAuthenticated;
    @Exclude
    @Ignore
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

    public String getBirthday() {
        return birthday;
    }

    public String getWeight() {
        return weight;
    }

    public Integer getAge() {
        return age;
    }

    public Double getHeight() {
        return height;
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

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public void setIsCreated(Boolean isCreated) {
        this.isCreated = isCreated;
    }

    public void setIsAuthenticated(Boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    public List<EActivity> getEActivityList() {
        return this.eActivityList;
    }

    public void setEActivityList(List<EActivity> eActivityList) {
        this.eActivityList = eActivityList;
    }
}