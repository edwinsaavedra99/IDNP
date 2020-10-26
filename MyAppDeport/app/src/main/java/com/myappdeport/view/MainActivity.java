package com.myappdeport.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.myappdeport.R;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button iniciarSesion;
    private Button registrate;
    private Button usuarioAnonimo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniciarSesion = (Button) findViewById(R.id.login);
        registrate = (Button) findViewById(R.id.registrate);
        usuarioAnonimo = (Button) findViewById(R.id.anonimo);

        iniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openIniciarSesion();
            }
        });
        registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRegister();
            }
        });

        //el codigo inserta datos a la coleccion -->
        /*try {
            IRepository<PointFirebase> activityRepository = ManagerSingletonRepository.getInstance(PointFirebase.class);
            PointFirebase pointFirebase = new PointFirebase(12.3, 45.5, 32.4);
            Task<PointFirebase> pointTask = activityRepository.save(pointFirebase);
            pointTask.addOnSuccessListener(new OnSuccessListener<PointFirebase>() {
                @Override
                public void onSuccess(PointFirebase pointFirebase) {
                    Log.i(TAG, pointFirebase.toString());
                }
            });
        } catch (InstantiationException e) {
            Log.e(TAG, "Instantiation Exception: ", e.getCause());
        } catch (IllegalAccessException e) {
            Log.e(TAG, "Illegal Access Exception: ", e.getCause());
        }*/
    }

    public void openIniciarSesion(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }
    public void openRegister(){
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}