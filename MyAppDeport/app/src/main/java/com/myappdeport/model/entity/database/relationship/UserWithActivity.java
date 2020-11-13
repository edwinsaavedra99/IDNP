package com.myappdeport.model.entity.database.relationship;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.EUser;

import java.util.List;

public class UserWithActivity {
    @Embedded
    private EUser eUser;
    @Relation(
            parentColumn = "id",
            entityColumn = "idEUser",
            entity = EActivity.class
    )
    private List<EActivity> eActivityList;

    public UserWithActivity(EUser eUser, List<EActivity> eActivityList) {
        this.eUser = eUser;
        this.eActivityList = eActivityList;
    }

    public UserWithActivity() {
    }

    public EUser getEUser() {
        return this.eUser;
    }

    public List<EActivity> getEActivityList() {
        return this.eActivityList;
    }

    public void setEUser(EUser eUser) {
        this.eUser = eUser;
    }

    public void setEActivityList(List<EActivity> eActivityList) {
        this.eActivityList = eActivityList;
    }

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof UserWithActivity))
            return false;
        final UserWithActivity other = (UserWithActivity) o;
        if (!other.canEqual((Object) this)) return false;
        final Object this$eUser = this.getEUser();
        final Object other$eUser = other.getEUser();
        if (this$eUser == null ? other$eUser != null : !this$eUser.equals(other$eUser))
            return false;
        final Object this$eActivityList = this.getEActivityList();
        final Object other$eActivityList = other.getEActivityList();
        if (this$eActivityList == null ? other$eActivityList != null : !this$eActivityList.equals(other$eActivityList))
            return false;
        return true;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof UserWithActivity;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $eUser = this.getEUser();
        result = result * PRIME + ($eUser == null ? 43 : $eUser.hashCode());
        final Object $eActivityList = this.getEActivityList();
        result = result * PRIME + ($eActivityList == null ? 43 : $eActivityList.hashCode());
        return result;
    }

    public String toString() {
        return "UserWithActivity(eUser=" + this.getEUser() + ", eActivityList=" + this.getEActivityList() + ")";
    }
}
