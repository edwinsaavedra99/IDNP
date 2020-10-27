package com.myappdeport.viewmodel;

import android.os.SystemClock;
import android.widget.Chronometer;
import com.myappdeport.service.usecase.interfaces.TimerInterface;

public class MainDeportPresenter implements TimerInterface.TimerInterfacePresenter {

    private TimerInterface.TimerInterfaceView mView;
    private boolean running;
    private long pauseOffSet;
    private Chronometer chronometer;

    /**
     * Constructor de presentador para el activity deport, inicializa el cronometro
     **/
    public MainDeportPresenter(TimerInterface.TimerInterfaceView view,Chronometer chronometer){
        mView = view;
        this.chronometer = chronometer;
        initPresenter();
    }


    @Override
    public void initPresenter() {
        //mView.initView();
    }
    //El cronometro inicia
    @Override
    public void startChronometer() {
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            chronometer.start();
            running = true;
        }
    }
    //El cronometro da una pausa, este metodo tambien reanuda el cronometro
    //se aplica efecto visual
    @Override
    public void pauseChronometer() {
        if(running){
            chronometer.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer.getBase();
            running = false;
        }else {
            startChronometer();
        }
        mView.setViewData(running);
    }
    //detiene el cronometro
    @Override
    public void stopChronometer() {
        chronometer.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
    }
}
