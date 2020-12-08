package com.myappdeport.view.activitys;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.rpc.context.AttributeContext;
import com.myappdeport.R;
import com.myappdeport.viewmodel.AuthViewModel;

import static com.myappdeport.utils.Constants.USER;

public class Configuration extends AppCompatActivity {

    ImageButton main;
    Button cerrarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuration);

        main = findViewById(R.id.back);
        main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMain();
            }
        });
        cerrarSesion = findViewById(R.id.cerrarSesion);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cerrarSesionAuth();
            }
        });
    }
    private void cerrarSesionAuth(){
        AuthViewModel authViewModel = new ViewModelProvider(this).get(AuthViewModel.class);
        authViewModel.cerrarSesion();
        Intent intent = new Intent(Configuration.this, MainActivity.class);
        //intent.putExtra(USER, user);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void openMain() {
        //Intent intent = new Intent(this, MenuContainer.class);
        //startActivity(intent);
        finish();
    }
}