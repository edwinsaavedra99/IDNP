package com.myappdeport.viewmodel.firebase;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.firebase.ActivityFireStoreRepository;
import com.myappdeport.repository.room.ActivityRoomRepository;

import java.util.List;

public class ActivityListUserViewModel extends ViewModel {
    private final ActivityFireStoreRepository firebaseRepository = ActivityFireStoreRepository.getInstance();
    public MutableLiveData<List<EActivity>> mutableLiveData = new MutableLiveData<>();
    ActivityListUserLiveData liveData = null;

    public LiveData<List<EActivity>> getActivityListLiveDataByUserId(String documentIdUser) {
        this.liveData = this.firebaseRepository.getActivityListLiveDataByIdUser(documentIdUser);
        return this.liveData;
    }

    public LiveData<List<EActivity>> getActivityList() {
        return this.liveData;
    }
}
