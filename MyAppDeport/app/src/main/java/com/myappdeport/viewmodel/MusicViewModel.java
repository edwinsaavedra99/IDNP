package com.myappdeport.viewmodel;

import android.content.Context;
import android.os.Build;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.service.usecase.media.MusicScanner;

import java.util.List;

public class MusicViewModel extends ViewModel {
    private MutableLiveData<List<Song>> listMutableLiveData = new MutableLiveData<>();
    private MusicScanner musicRepository;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public MutableLiveData<List<Song>> getAllSongs(Context context) {
        if (musicRepository == null) {
            this.musicRepository = new MusicScanner(context);
        }
        this.listMutableLiveData = this.musicRepository.getListLiveData();
        return listMutableLiveData;
    }
}
