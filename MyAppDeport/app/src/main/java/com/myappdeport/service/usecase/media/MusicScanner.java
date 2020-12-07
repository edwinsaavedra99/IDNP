package com.myappdeport.service.usecase.media;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.util.Log;
import android.webkit.MimeTypeMap;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;
import com.myappdeport.model.entity.functional.Song;
import com.myappdeport.utils.MediaUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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

    public MutableLiveData<List<Song>> getListLiveData() {
        return listLiveData;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    public MusicScanner(Context context) {
        this.context = context;
        this.TAG = MusicScanner.class.getSimpleName();
        this.scanAllDevice();
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
            int albumNameColumn = cursor.getColumnIndex(ALBUM);
            int artistColumn = cursor.getColumnIndex(ARTIST);
            int durationColumn = cursor.getColumnIndex(DURATION);
            //int genreColumn = cursor.getColumnIndex(GENRE);
            do {
                long thisId = cursor.getLong(idColumn);
                Uri songUri;
                if (isExternalOrInternal) {
                    songUri = ContentUris.withAppendedId(EXTERNAL_CONTENT_URI, thisId);
                } else {
                    songUri = ContentUris.withAppendedId(INTERNAL_CONTENT_URI, thisId);
                }
                if (MimeTypeMap.getSingleton().getExtensionFromMimeType(context.getContentResolver().getType(songUri)).equalsIgnoreCase("mp3")) {
                    String thisTitle = cursor.getString(titleColumn);
                    String thisAlbumName = cursor.getString(albumNameColumn);
                    String thisArtist = cursor.getString(artistColumn);
                    //String thisGenre = cursor.getString(genreColumn);
                    long thisDuration = cursor.getLong(durationColumn);
                    songs.add(new Song(thisId, thisTitle, thisAlbumName, thisArtist, thisDuration, songUri));
                }
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
        if (!internalSDCard.isEmpty()) {
            allSongs.addAll(internalSDCard);
        }
        if (!externalSDCard.isEmpty()) {
            allSongs.addAll(externalSDCard);
        }
        this.listLiveData.setValue(allSongs);
    }

    private boolean isSDCardPresent() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public List<MediaDescriptionCompat> getMediaItems() {
        List<Song> allSongInDevice = this.listLiveData.getValue();
        return MediaUtils.songToMediaDescription(Objects.requireNonNull(allSongInDevice), this.context);
    }

    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.Q)
    public List<MediaMetadataCompat> getMetadataItems() {
        List<Song> allSongInDevice = this.listLiveData.getValue();
        return MediaUtils.songToMetadata(Objects.requireNonNull(allSongInDevice));
    }
}
