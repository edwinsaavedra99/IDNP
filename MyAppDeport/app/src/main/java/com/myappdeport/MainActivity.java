package com.myappdeport;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.myappdeport.model.database.firebase.ActivityFirebase;
import com.myappdeport.model.database.firebase.PointFirebase;
import com.myappdeport.repository.IRepository;
import com.myappdeport.repository.ManagerSingletonRepository;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
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
        }
    }
}