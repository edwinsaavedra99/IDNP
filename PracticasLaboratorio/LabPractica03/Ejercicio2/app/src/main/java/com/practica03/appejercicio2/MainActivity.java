package com.practica03.appejercicio2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int PERMISSION_REQUEST_CODE_EXTERNAL_STORAGE = 1;
    private ListView listMusic;
    private String [] items;
    //private ArrayList<File> archivos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialComponents();
        permits();

    }
    public void initialComponents(){
        this.listMusic = (ListView) findViewById(R.id.listMusic);
        //this.archivos = new ArrayList<>();
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

    void display(){
        final ArrayList<File> myMusic = findMusic(Environment.getExternalStorageDirectory());
        items = new String[myMusic.size()];
        System.out.println("display ::"+items.length);
        for( int i = 0 ; i < myMusic.size() ; i++){
            items[i]  = myMusic.get(i).getName().toString().replace(".mp3","").replace(".wav","");
        }
        ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);
        listMusic.setAdapter(arrayAdapter);
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