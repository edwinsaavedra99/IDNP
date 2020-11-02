package com.myappdeport.viewmodel;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.myappdeport.model.entity.funcional.Audio;
import com.myappdeport.model.entity.funcional.GetAudios;

import java.util.List;

public class AudioViewModel extends ViewModel {
    private MutableLiveData<List<Audio>> liveData;
    private GetAudios getAudios = GetAudios.getInstance();

    public LiveData<List<Audio>> getAudios(Cursor cursor)
    {
        liveData = getAudios.getAllAudioFromDevice(cursor);
        return liveData;
    }
}
