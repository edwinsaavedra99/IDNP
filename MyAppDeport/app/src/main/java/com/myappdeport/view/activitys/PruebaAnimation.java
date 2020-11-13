package com.myappdeport.view.activitys;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.myappdeport.R;

public class PruebaAnimation extends AppCompatActivity {

    AnimationDrawable timeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_animation);

        ImageView imageAnimation = (ImageView) findViewById(R.id.imageAnimation);
        imageAnimation.setBackgroundResource(R.drawable.animation);
        timeAnimation = (AnimationDrawable) imageAnimation.getBackground();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        timeAnimation.start();
    }
}