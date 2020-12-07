package com.myappdeport.view.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.myappdeport.R;

public class Configuration extends AppCompatActivity {

    ImageButton main;

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
    }
    private void openMain() {
        Intent intent = new Intent(this, MenuContainer.class);
        startActivity(intent);
    }
}