package com.myappdeport.viewmodel.firebase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.repository.firebase.ActivityFireStoreRepository;

import java.util.List;

public class ActivityListViewModel extends ViewModel {
    private final ActivityFireStoreRepository repository = ActivityFireStoreRepository.getInstance();
    ActivityListLiveData liveData = null;

    public LiveData<List<EActivity>> getActivityListLiveData() {
        this.liveData = this.repository.getActivityListLiveData();
        return this.liveData;
    }

    public LiveData<List<EActivity>> getActivityList() {
        return this.liveData;
    }
}
