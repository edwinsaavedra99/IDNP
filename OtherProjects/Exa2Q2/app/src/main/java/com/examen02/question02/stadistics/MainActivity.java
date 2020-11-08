package com.examen02.question02.stadistics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public LinearLayout linearLayout;
    private StadisticView stadisticView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        initialComponent();
    }
    private void initialComponent(){
        this.linearLayout = findViewById(R.id.grafico);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        System.out.println("linear: "+ linearLayout.getWidth()+ " ; "+linearLayout.getHeight());

      //  LinearLayout linearLayout = (LinearLayout)findViewById(R.id.layout);
        /*ViewTreeObserver viewTreeObserver = linearLayout.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override public void onGlobalLayout() {
                linearLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                int width = linearLayout.getMeasuredWidth();
                int height = linearLayout.getMeasuredHeight(); }
        });*/

        stadisticView = new StadisticView(this,linearLayout,metrics,getData());
        linearLayout.addView(stadisticView);
    }

    private ArrayList<Double> getData(){
        ArrayList<Double> doubleArrayList = new ArrayList<>();
        doubleArrayList.add(13.33);
        doubleArrayList.add(12.0);
        doubleArrayList.add(10.67);
        doubleArrayList.add(4.5d);
        doubleArrayList.add(6.3d);
        doubleArrayList.add(12d);
        doubleArrayList.add(4d);
        return doubleArrayList;
    }

}