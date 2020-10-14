package com.example.ejercicio1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class SaludoActivity extends AppCompatActivity  {

    private GestureDetectorCompat gestureDetectorCompat;
    private TextView texto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saludo);
        texto = findViewById(R.id.textView);
        //texto.setText("ya ps");
        gestureDetectorCompat = new GestureDetectorCompat(this, new GestureListener());
    }
    private  class  GestureListener  extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onSingleTapUp", Toast.LENGTH_SHORT).show();
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onLongPress", Toast.LENGTH_SHORT).show();
            super.onLongPress(e);
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Toast.makeText(SaludoActivity.this, "onFling", Toast.LENGTH_SHORT).show();
            return super.onFling(e1, e2, velocityX, velocityY);
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onDown", Toast.LENGTH_SHORT).show();
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onDoubleTap", Toast.LENGTH_SHORT).show();
            return super.onDoubleTap(e);
        }

        @Override
        public boolean onDoubleTapEvent(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onDoubletevent", Toast.LENGTH_SHORT).show();
            return super.onDoubleTapEvent(e);
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Toast.makeText(SaludoActivity.this, "onsigletapconfirmed", Toast.LENGTH_SHORT).show();
            return super.onSingleTapConfirmed(e);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.gestureDetectorCompat.onTouchEvent(event);
        return super.onTouchEvent(event);
    }
}