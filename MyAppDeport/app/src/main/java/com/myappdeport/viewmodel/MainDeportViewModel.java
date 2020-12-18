package com.myappdeport.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.myappdeport.model.entity.database.EActivity;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IActivityRepository;
import com.myappdeport.repository.IAuthRepository;
import com.myappdeport.repository.IPositionRepository;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.firebase.ActivityFireStoreRepository;
import com.myappdeport.repository.room.ActivityRoomRepository;
import com.myappdeport.repository.room.PositionRoomRepository;
import com.myappdeport.repository.room.RouteRoomRepository;

/**
 * Este metodo contiene diferentes casos de uso
 * Estado : En progreso
 * */


public class MainDeportViewModel extends AndroidViewModel {
    private final IActivityRepository iActivityRepository1,iActivityRepository2;
    private final IRouteRepository iRouteRepository;
    private final IPositionRepository iPositionRepository;

    //private final IAuthRepository


    public MainDeportViewModel(Application application) {
        super(application);
        this.iActivityRepository1 = ActivityRoomRepository.getInstance(application);
        this.iActivityRepository2 = ActivityFireStoreRepository.getInstance();
        this.iRouteRepository = RouteRoomRepository.getInstance(application);
        this.iPositionRepository = PositionRoomRepository.getInstance(application);

    }

    /*public void createNewActivity(EActivity eActivity)
    }*/

    public void saveActivity(EActivity eActivity)  throws InstantiationException, IllegalAccessException {
        //EActivity activity = new EActivity("","",12,)
        iActivityRepository1.saveWithRouteAndPositions(eActivity).addOnSuccessListener(eActivity1 -> {
            System.out.println(eActivity1.toString());
        });
        iActivityRepository2.saveWithRouteAndPositions(eActivity).addOnSuccessListener(eActivity1->{
            System.out.println(eActivity1.toString());
        });
    }
}
