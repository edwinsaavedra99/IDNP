package com.myappdeport.service.usecase.interfaces;

public interface TimerInterface {

    /**
     * Operations Use Case _ control de cronometro
     */
    interface TimerInterfaceUseCase{
        // Presenter operations permitted to View
        void initPresenter();
        void startChronometer();
        void pauseChronometer();
        void stopChronometer();
    }
    /**
     * UI cargar datos y metodo para actualizar
     */
    interface TimerInterfaceView{
        // View operations permitted to Presenter
        void initView();
        void setViewData(boolean flag);
    }
}

