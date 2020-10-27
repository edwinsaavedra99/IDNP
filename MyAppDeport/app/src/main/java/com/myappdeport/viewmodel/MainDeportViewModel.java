package com.myappdeport.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myappdeport.service.usecase.ChronometerUseCase;

/**
 * Este metodo contiene diferentes casos de uso
 * Estado : En progreso
 * */


public class MainDeportViewModel extends ViewModel {
    private MutableLiveData<String> timer;
    public void eventViewModel(){

    }
    public LiveData<String> getResultad(){
        return timer;
    }

    public void startChr(){
        //timer.setValue(ChronometerUseCase.);
    }
}
