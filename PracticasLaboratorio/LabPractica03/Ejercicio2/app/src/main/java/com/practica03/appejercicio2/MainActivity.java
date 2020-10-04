package com.practica03.appejercicio2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private ListView listMusic;
    private String [] items;
    private ImageView play,pause;
    private TextView nameSong;
    private SeekBar seekBar;
    private static MediaPlayer mediaPlayer;
    private int position;
    //private ArrayList<File> mySongs;
    private Thread updateseekbar;
    private ArrayList<File> myMusic;
    private String songName;
    private boolean flagPause;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponents();
        permits();

    }

    protected void onPause() {
        super.onPause();
        seekBar.setMax(mediaPlayer.getDuration());
        if(mediaPlayer.isPlaying()){
            pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24);
            mediaPlayer.pause();
        }

    }

    protected void onRestart() {
        super.onRestart();
        if(!mediaPlayer.isPlaying() && flagPause == false){
            pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
            mediaPlayer.start();
        }
    }

    public void initialComponents(){
        this.listMusic = (ListView) findViewById(R.id.listMusic);
        this.play = findViewById(R.id.play);
        this.pause = findViewById(R.id.pause);
        this.nameSong = findViewById(R.id.nameSong);
        this.seekBar = findViewById(R.id.barSong);
        flagPause = false;
    }

    public ArrayList<File> findMusic(File file){
        ArrayList<File> arrayList =  new ArrayList<>();
        File[] files = file.listFiles();
        if(files!=null){
            for (File singleFile: files){
                if (singleFile.isDirectory() && !singleFile.isHidden()){
                    arrayList.addAll(findMusic(singleFile));
                    System.out.println(singleFile.getName());
                }else{
                    if(singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")){
                        arrayList.add(singleFile);
                    }
                }
            }
        }
        return arrayList;
    }

    void playSongName(String songName, int position){
        seekBar.setProgress(0);
        updateseekbar = new Thread(){
            @Override
            public void run(){
                int totalDuration = mediaPlayer.getDuration();
                int currentPosition = 0;
                while (currentPosition<totalDuration){
                    try {
                        sleep(500);
                        currentPosition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentPosition);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }

        };

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }

        this.nameSong.setText(songName);
        this.nameSong.setSelected(true);
        Uri uri = Uri.parse(myMusic.get(position).toString());
        mediaPlayer=MediaPlayer.create(getApplicationContext(),uri);
        mediaPlayer.start();
        seekBar.setMax(mediaPlayer.getDuration());
        //updateseekbar.start();
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    flagPause = true;
                    pause.setBackgroundResource(R.drawable.ic_baseline_play_circle_filled_24);
                    mediaPlayer.pause();
                }else{
                    pause.setBackgroundResource(R.drawable.ic_baseline_pause_circle_outline_24);
                    mediaPlayer.start();
                }
            }
        });
    }

    void display(){
        this.myMusic = findMusic(Environment.getExternalStorageDirectory());
        items = new String[myMusic.size()];
        System.out.println("display ::"+items.length);
        for( int i = 0 ; i < myMusic.size() ; i++){
            items[i]  = myMusic.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listMusic.setAdapter(arrayAdapter);

        listMusic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                songName = listMusic.getItemAtPosition(position).toString();
                playSongName(songName,position);
            }
        });
    }


    public void permits(){
        if (Build.VERSION.SDK_INT >= 23){
            if(ContextCompat.checkSelfPermission(getApplicationContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                // Permisos concedidos
                display();
                Log.println(Log.INFO,TAG,"API >= 23");
            } else{
                if(shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)){
                    // Mensaje de error
                    Toast.makeText(getApplicationContext(), "External storage and camera permission required to read media", Toast.LENGTH_SHORT).show();
                }
                // Callback para solicitar permisos
                requestPermissions(new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE);
            }
        } else {
            //callCameraApp();
            Log.println(Log.INFO,TAG,"API < 23");
            display();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode == PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getApplicationContext(), "External read permission", Toast.LENGTH_SHORT).show();
                display();//LLAMAR METODO
            }else {
                Toast.makeText(getApplicationContext(), "External read permission has not been granted, cannot open media", Toast.LENGTH_SHORT).show();
            }
        } else {
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


}