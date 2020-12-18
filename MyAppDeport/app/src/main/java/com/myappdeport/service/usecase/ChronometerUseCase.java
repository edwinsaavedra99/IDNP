package com.myappdeport.service.usecase;

import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import com.myappdeport.service.usecase.interfaces.TimerInterface;

public class ChronometerUseCase implements TimerInterface.TimerInterfaceUseCase {

    private TimerInterface.TimerInterfaceView mView;
    private boolean running;
    private long pauseOffSet;
    private Chronometer chronometer,chronometer2,chronometerExt;
    //private TextView textView;

    /**
     * Constructor de presentador para el activity deport, inicializa el cronometro
     **/

    public ChronometerUseCase(TimerInterface.TimerInterfaceView view, Chronometer chronometer,Chronometer chronometer2 , Chronometer chronometerExt ){

        mView = view;
        this.chronometer = chronometer;
        this.chronometer2 = chronometer2;
        this.chronometerExt = chronometerExt;

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

            chronometer2.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            chronometer2.start();
            chronometerExt.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            chronometerExt.start();
            String d = chronometer.getText().toString();
            //System.out.println(d);
            running = true;
        }
    }
    //El cronometro da una pausa, este metodo tambien reanuda el cronometro
    //se aplica efecto visual
    @Override
    public void pauseChronometer() {
        if(running){
            chronometer.stop();

            chronometer2.stop();
            chronometerExt.stop();
            pauseOffSet = SystemClock.elapsedRealtime() - chronometer2.getBase();
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

        chronometer2.setBase(SystemClock.elapsedRealtime());
        chronometerExt.setBase(SystemClock.elapsedRealtime());
        pauseOffSet = 0;
    }
}
