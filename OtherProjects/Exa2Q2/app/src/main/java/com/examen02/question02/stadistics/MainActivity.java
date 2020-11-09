package com.examen02.question02.stadistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public LinearLayout linearLayout,linearLayout2;
    private StadisticView stadisticView;
    private BarrasView barrasView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialComponent();
    }
    private void initialComponent(){
        this.linearLayout = findViewById(R.id.grafico);
        this.linearLayout2 = findViewById(R.id.grafico2);
        this.button = findViewById(R.id.simule);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        barrasView = new BarrasView(this,linearLayout2,metrics,getDataTiempo());
        linearLayout2.addView(barrasView);
        stadisticView = new StadisticView(this,linearLayout,metrics,getDataKilometros());
        linearLayout.addView(stadisticView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float number = (float) Math.floor((Math.random()*15));
                stadisticView.actualizarData(number);
                barrasView.actualizarData(number*4);
            }
        });
    }

    private ArrayList<Double> getDataKilometros(){
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(13.33);
        doubleArrayList.add(0.0); //si ese dia no hay registro se considera como cero
        doubleArrayList.add(10.67);
        doubleArrayList.add(4.5d);
        doubleArrayList.add(6.3d);
        doubleArrayList.add(12d);
        doubleArrayList.add(4d);
        doubleArrayList.add(14d);
        return doubleArrayList;
    }
    private ArrayList<Double> getDataTiempo(){
        //simulando ritmo de 4 min por kilometros
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(getDataKilometros().get(0) * 4);
        doubleArrayList.add(getDataKilometros().get(1) * 4);
        doubleArrayList.add(getDataKilometros().get(2) * 4);
        doubleArrayList.add(getDataKilometros().get(3) * 4);
        doubleArrayList.add(getDataKilometros().get(4) * 4);
        doubleArrayList.add(getDataKilometros().get(5) * 4);
        doubleArrayList.add(getDataKilometros().get(6) * 4);
        doubleArrayList.add(getDataKilometros().get(7) * 4);
        return doubleArrayList;
    }


}