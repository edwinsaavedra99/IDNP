package com.myappdeport.model.entity.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;

import com.google.firebase.firestore.Exclude;

import java.util.List;

@Entity(indices = @Index("email"))
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

    public List<EActivity> getEActivityList() {
        return this.eActivityList;
    }

    public void setEActivityList(List<EActivity> eActivityList) {
        this.eActivityList = eActivityList;
    }

    public String getBirthday() {
        return this.birthday;
    }

    public String getWeight() {
        return this.weight;
    }

    public Integer getAge() {
        return this.age;
    }

    public Double getHeight() {
        return this.height;
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof EUser)) return false;
        final EUser other = (EUser) o;
        if (!other.canEqual((Object) this)) return false;
        if (!super.equals(o)) return false;
        final Object this$name = this.getName();
        final Object other$name = other.getName();
        if (this$name == null ? other$name != null : !this$name.equals(other$name)) return false;
        final Object this$email = this.getEmail();
        final Object other$email = other.getEmail();
        if (this$email == null ? other$email != null : !this$email.equals(other$email))
            return false;
        final Object this$birthday = this.getBirthday();
        final Object other$birthday = other.getBirthday();
        if (this$birthday == null ? other$birthday != null : !this$birthday.equals(other$birthday))
            return false;
        final Object this$weight = this.getWeight();
        final Object other$weight = other.getWeight();
        if (this$weight == null ? other$weight != null : !this$weight.equals(other$weight))
            return false;
        final Object this$age = this.getAge();
        final Object other$age = other.getAge();
        if (this$age == null ? other$age != null : !this$age.equals(other$age)) return false;
        final Object this$height = this.getHeight();
        final Object other$height = other.getHeight();
        if (this$height == null ? other$height != null : !this$height.equals(other$height))
            return false;
        final Object this$isNew = this.getIsNew();
        final Object other$isNew = other.getIsNew();
        if (this$isNew == null ? other$isNew != null : !this$isNew.equals(other$isNew))
            return false;
        final Object this$isCreated = this.getIsCreated();
        final Object other$isCreated = other.getIsCreated();
        if (this$isCreated == null ? other$isCreated != null : !this$isCreated.equals(other$isCreated))
            return false;
        final Object this$isAuthenticated = this.getIsAuthenticated();
        final Object other$isAuthenticated = other.getIsAuthenticated();
        if (this$isAuthenticated == null ? other$isAuthenticated != null : !this$isAuthenticated.equals(other$isAuthenticated))
            return false;
        final Object this$eActivityList = this.getEActivityList();
        final Object other$eActivityList = other.getEActivityList();
        if (this$eActivityList == null ? other$eActivityList != null : !this$eActivityList.equals(other$eActivityList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof EUser;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = super.hashCode();
        final Object $name = this.getName();
        result = result * PRIME + ($name == null ? 43 : $name.hashCode());
        final Object $email = this.getEmail();
        result = result * PRIME + ($email == null ? 43 : $email.hashCode());
        final Object $birthday = this.getBirthday();
        result = result * PRIME + ($birthday == null ? 43 : $birthday.hashCode());
        final Object $weight = this.getWeight();
        result = result * PRIME + ($weight == null ? 43 : $weight.hashCode());
        final Object $age = this.getAge();
        result = result * PRIME + ($age == null ? 43 : $age.hashCode());
        final Object $height = this.getHeight();
        result = result * PRIME + ($height == null ? 43 : $height.hashCode());
        final Object $isNew = this.getIsNew();
        result = result * PRIME + ($isNew == null ? 43 : $isNew.hashCode());
        final Object $isCreated = this.getIsCreated();
        result = result * PRIME + ($isCreated == null ? 43 : $isCreated.hashCode());
        final Object $isAuthenticated = this.getIsAuthenticated();
        result = result * PRIME + ($isAuthenticated == null ? 43 : $isAuthenticated.hashCode());
        final Object $eActivityList = this.getEActivityList();
        result = result * PRIME + ($eActivityList == null ? 43 : $eActivityList.hashCode());
        return result;
    }

    public String toString() {
        return "EUser(super=" + super.toString() + ", name=" + this.getName() + ", email=" + this.getEmail() + ", birthday=" + this.getBirthday() + ", weight=" + this.getWeight() + ", age=" + this.getAge() + ", height=" + this.getHeight() + ", isNew=" + this.getIsNew() + ", isCreated=" + this.getIsCreated() + ", isAuthenticated=" + this.getIsAuthenticated() + ", eActivityList=" + this.getEActivityList() + ")";
    }
}
