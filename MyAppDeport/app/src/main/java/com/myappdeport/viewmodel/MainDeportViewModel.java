package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.IAuthRepository;
import com.myappdeport.repository.IPositionRepository;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.room.ActivityRoomRepository;
import com.myappdeport.repository.room.PositionRoomRepository;
import com.myappdeport.repository.room.RouteRoomRepository;

/**
 * Este metodo contiene diferentes casos de uso
 * Estado : En progreso
 * */


public class MainDeportViewModel extends AndroidViewModel {
    private final IActivityRepository iActivityRepository;
    private final IRouteRepository iRouteRepository;
    private final IPositionRepository iPositionRepository;

    //private final IAuthRepository


    public MainDeportViewModel(Application application) {
        super(application);
        this.iActivityRepository = ActivityRoomRepository.getInstance(application);
        this.iRouteRepository = RouteRoomRepository.getInstance(application);
        this.iPositionRepository = PositionRoomRepository.getInstance(application);

    }

    /*public void createNewActivity(EActivity eActivity)
    }*/

    public void saveActivity(EActivity eActivity)  throws InstantiationException, IllegalAccessException {
        //EActivity activity = new EActivity("","",12,)
        iActivityRepository.saveWithRouteAndPositions(eActivity).addOnSuccessListener(eActivity1 -> {
            System.out.println(eActivity1.toString());
        });
    }
}
