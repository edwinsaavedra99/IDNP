package com.myappdeport.service.usecase;

import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.TextView;

import com.myappdeport.service.usecase.interfaces.TimerInterface;

public class ChronometerUseCase implements TimerInterface.TimerInterfaceUseCase {

    private TimerInterface.TimerInterfaceView mView;
    private boolean running;
    private long pauseOffSet;
    public static Chronometer chronometer;
    public static Chronometer textView;

    /**
     * Constructor de presentador para el activity deport, inicializa el cronometro
     **/
    public ChronometerUseCase(TimerInterface.TimerInterfaceView view, Chronometer chronometer,Chronometer textView){
        mView = view;
        this.chronometer = chronometer;
        this.textView = textView;
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
            textView.setBase(SystemClock.elapsedRealtime() - pauseOffSet);
            chronometer.start();
            textView.start();
            /*String d = chronometer.getText().toString();
            System.out.println(d);*/

            running = true;
        }
    }
    //El cronometro da una pausa, este metodo tambien reanuda el cronometro
    //se aplica efecto visual
    @Override
    public void pauseChronometer() {
        if(running){
            chronometer.stop();
            textView.stop();
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
        textView.setBase(SystemClock.elapsedRealtime());
        chronometer.stop();
        textView.stop();
        pauseOffSet = 0;
    }
}
