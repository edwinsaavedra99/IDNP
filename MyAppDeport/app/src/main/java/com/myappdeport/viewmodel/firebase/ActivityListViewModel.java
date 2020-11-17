package com.myappdeport.viewmodel.firebase;

import androidx.lifecycle.ViewModel;

import com.myappdeport.repository.firebase.ActivityFireStoreRepository;

public class ActivityListViewModel extends ViewModel {
    private ActivityListLiveData activityListLiveData;
    private final ActivityFireStoreRepository repository = ActivityFireStoreRepository.getInstance();

    private ActivityListLiveData getActivityListLiveData() {
        if (this.activityListLiveData.getValue() != null)
            this.activityListLiveData = this.repository.getActivityListLiveData();
        return this.activityListLiveData;
    }
}
