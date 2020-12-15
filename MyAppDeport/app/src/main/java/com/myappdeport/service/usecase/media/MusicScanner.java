package com.myappdeport.service.usecase.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.OpenableColumns;
import android.util.Log;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import com.myappdeport.model.entity.functional.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Audio.AudioColumns.ALBUM;
import static android.provider.MediaStore.Audio.AudioColumns.ARTIST;
import static android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
import static android.provider.MediaStore.Audio.Media.INTERNAL_CONTENT_URI;
import static android.provider.MediaStore.MediaColumns.DURATION;
import static android.provider.MediaStore.MediaColumns.TITLE;

public class MusicScanner {
    private final String TAG;
    private final Context context;
    private final MutableLiveData<List<Song>> listLiveData = new MutableLiveData<>();
    //private ExecutorService executorService = Executors.newSingleThreadExecutor();

    public MutableLiveData<List<Song>> getListLiveData() {
        return listLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public MusicScanner(Context context) {
        this.context = context;
        this.TAG = MusicScanner.class.getSimpleName();
        new Thread(this::scanAllDevice).start();
        //executorService.submit(this::scanAllDevice);
    }

    private Cursor scanExternalDevice() {
        if (isSDCardPresent()) {
            return scanMp3From(EXTERNAL_CONTENT_URI);
        }
        return null;
    }

    private Cursor scanMp3From(Uri from) {
        ContentResolver contentResolver = this.context.getContentResolver();
        return contentResolver.query(from, null, null, null, null);
    }

    private Cursor scanInternalDevice() {
        return scanMp3From(INTERNAL_CONTENT_URI);
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private List<Song> cursorToSong(Cursor cursor, boolean isExternalOrInternal) {
        if (cursor == null) {
            // query failed, handle error.
            Log.e(TAG, "ERROR IN MUSIC SCANNER");
        } else if (!cursor.moveToFirst()) {
            // no media on the device
            Log.e(TAG, "DON'T EXIST MUSIC ON DEVICE");
        } else {
            Log.e(TAG, "EXIST MUSIC ON DEVICE");
            List<Song> songs = new ArrayList<>();
            int idColumn = cursor.getColumnIndex(_ID);
            int titleColumn = cursor.getColumnIndex(TITLE);
            int displayName = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            int albumNameColumn = cursor.getColumnIndex(ALBUM);
            int artistColumn = cursor.getColumnIndex(ARTIST);
            int durationColumn = cursor.getColumnIndex(DURATION);
            //int genreColumn = cursor.getColumnIndex(GENRE);
            do {
                long thisId = cursor.getLong(idColumn);
                String thisDisplayName = cursor.getString(displayName);
                Uri songUri;
                if (isExternalOrInternal) {
                    songUri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, thisId);
                } else {
                    songUri = ContentUris.withAppendedId(INTERNAL_CONTENT_URI, thisId);
                }
                if (thisDisplayName.endsWith("mp3")) {
                    String thisTitle = cursor.getString(titleColumn);
                    String thisAlbumName = cursor.getString(albumNameColumn);
                    String thisArtist = cursor.getString(artistColumn);
                    //String thisGenre = cursor.getString(genreColumn);
                    long thisDuration = cursor.getLong(durationColumn);
                    songs.add(new Song(thisId, thisTitle, thisAlbumName, thisArtist, thisDuration, songUri));
                }
            } while (cursor.moveToNext());
            Log.e(TAG, String.valueOf(cursor.getCount()));
            return songs;
        }
        return Collections.emptyList();
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public void scanAllDevice() {
        Log.e(TAG, "Ending Scanning Internal");
        List<Song> internalSDCard = this.cursorToSong(this.scanInternalDevice(), false);
        Log.e(TAG, "Ending Scanning External");
        List<Song> externalSDCard = this.cursorToSong(this.scanExternalDevice(), true);
        List<Song> allSongs = new ArrayList<>();
        if (!internalSDCard.isEmpty()) {
            allSongs.addAll(internalSDCard);
        }
        if (!externalSDCard.isEmpty()) {
            allSongs.addAll(externalSDCard);
        }
        Log.e(TAG, "Setting all scanning music.");
        try {
            this.listLiveData.postValue(allSongs);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e.getCause());
        }
    }

    private boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

}
