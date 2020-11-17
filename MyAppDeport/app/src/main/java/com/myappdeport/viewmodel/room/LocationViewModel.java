package com.myappdeport.viewmodel.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.google.android.gms.tasks.Task;
import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.room.ActivityRoomRepository;

public class LocationViewModel extends AndroidViewModel {
    private Task<EActivity> eActivityTask;
    private IRouteRepository<String> routeRepository;
    private IActivityRepository activityRepository;

    public LocationViewModel(@NonNull Application application) {
        super(application);
        activityRepository = ActivityRoomRepository.getInstance(application);
    }

    public void insert(EActivity eActivity) {activityRepository.save(eActivity);}
}
