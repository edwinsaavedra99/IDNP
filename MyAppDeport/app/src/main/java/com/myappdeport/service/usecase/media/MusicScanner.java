package com.myappdeport.service.usecase.media;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.myappdeport.model.entity.functional.Song;
import lombok.Data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.MediaStore.Audio.Media.*;

@Data
public class MusicScanner {
    private final String TAG;
    private final Application application;
    private LiveData<List<Song>> listLiveData;

    public MusicScanner(Application application) {
        this.application = application;
        this.TAG = MusicScanner.class.getSimpleName();
    }

    private Cursor scanExternalDevice() {
        if (isSDCardPresent()) {
            ContentResolver contentResolver = this.application.getContentResolver();
            return contentResolver.query(EXTERNAL_CONTENT_URI, null, null, null, null);
        }
        return null;
    }

    private Cursor scanInternalDevice() {
        ContentResolver contentResolver = this.application.getContentResolver();
        return contentResolver.query(INTERNAL_CONTENT_URI, null, null, null, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private List<Song> cursorToSong(Cursor cursor, boolean isExternalOrInternal) {
        if (cursor == null) {
            // query failed, handle error.
            Log.e(TAG, "ERROR IN MUSIC PLAYER");
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.e(TAG, "DON'T EXIST MUSIC ON DEVICE");
        } else {
            List<Song> songs = new ArrayList<>();
            int idColumn = cursor.getColumnIndex(_ID);
            int titleColumn = cursor.getColumnIndex(DISPLAY_NAME);
            int albumColumn = cursor.getColumnIndex(ALBUM);
            int artistColumn = cursor.getColumnIndex(ARTIST);
            int durationColumn = cursor.getColumnIndex(DURATION);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisTitle = cursor.getString(titleColumn);
                String thisAlbum = cursor.getString(albumColumn);
                String thisArtist = cursor.getString(artistColumn);
                String thisDuration = cursor.getString(durationColumn);
                Uri contentUri;
                if (isExternalOrInternal) {
                    contentUri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, thisId);
                } else {
                    contentUri = ContentUris.withAppendedId(INTERNAL_CONTENT_URI, thisId);
                }
                songs.add(new Song(thisId, thisTitle, thisAlbum, thisArtist, thisDuration, contentUri));
            } while (cursor.moveToNext());
            return songs;
        }
        return Collections.emptyList();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void scanAllDevice() {
        List<Song> internalSDCard = this.cursorToSong(this.scanInternalDevice(), false);
        List<Song> externalSDCard = this.cursorToSong(this.scanExternalDevice(), true);
        List<Song> allSongs = new ArrayList<>();
        if (!internalSDCard.isEmpty() && !externalSDCard.isEmpty()) {
            allSongs.addAll(internalSDCard);
            allSongs.addAll(externalSDCard);
        }
        this.listLiveData = new MutableLiveData<>(allSongs);
    }

    private boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }
}
