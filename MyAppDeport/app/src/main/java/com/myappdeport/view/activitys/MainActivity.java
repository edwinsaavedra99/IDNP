package com.myappdeport.view.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.myappdeport.R;
import com.myappdeport.model.entity.database.EPosition;
import com.myappdeport.model.entity.database.ERoute;
import com.myappdeport.repository.IRouteRepository;
import com.myappdeport.repository.firebase.PositionFireStoreRepository;
import com.myappdeport.repository.firebase.RouteFireStoreRepository;

import java.util.Collections;

import lombok.SneakyThrows;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Button iniciarSesion;
    private Button registrate;
    private Button usuarioAnonimo;

    @SneakyThrows
    @RequiresApi(api = Build.VERSION_CODES.N)
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
        usuarioAnonimo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMenuContainer();
            }
        });
        PositionFireStoreRepository repository = PositionFireStoreRepository.getInstance();
        IRouteRepository repository2 = RouteFireStoreRepository.getInstance();
        repository2.saveWithPositions(new ERoute(12.3, 32.4, null, Collections.singletonList(new EPosition(12.4, 32.4, 23.5))))
                .addOnSuccessListener(eRoute -> {
                    Log.e(TAG, eRoute.toString());
                })
                .addOnFailureListener(e -> {
                    Log.e(TAG, e.getMessage(), e.getCause());
                }).addOnCompleteListener(task -> {
            repository2.count().addOnCompleteListener(task1 -> {
                Log.e(TAG, "ERoute: " + task1.getResult());
            });
        });

    }

    public void openIniciarSesion() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void openDeportActivity() {
        Intent intent = new Intent(this, DeportActivity.class);
        startActivity(intent);
    }

    public void openMenuContainer() {
        Intent intent = new Intent(this, MenuContainer.class);
        startActivity(intent);
    }
}