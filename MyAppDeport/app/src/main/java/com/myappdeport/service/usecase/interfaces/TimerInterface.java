package com.myappdeport.service.usecase.interfaces;

public interface TimerInterface {
    /**
     * Operations offered to Model to communicate with Presenter
     * Handles all data business logic.
     */
    interface TimerInterfaceInteract {
        // Model operations permitted to Presenter
        String getData();
        void setData(String data);
    }
    /**
     * Operations offered to View to communicate with Presenter.
     * Processes user interactions, sends data requests to Model, etc.
     */
    interface TimerInterfacePresenter{
        // Presenter operations permitted to View
        void initPresenter();
        void startChronometer();
        void pauseChronometer();
        void stopChronometer();
    }
    /**
     * Required View methods available to Presenter.
     * A passive layer, responsible to show data
     * and receive user interactions
     */
    interface TimerInterfaceView{
        // View operations permitted to Presenter
        void initView();
        void setViewData(boolean flag);
    }
}

