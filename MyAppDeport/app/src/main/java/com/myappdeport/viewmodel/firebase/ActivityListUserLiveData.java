package com.myappdeport.viewmodel.firebase;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.myappdeport.model.entity.database.EActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityListUserLiveData extends LiveData<List<EActivity>> implements EventListener<QuerySnapshot> {
    private static final String TAG = ActivityListUserLiveData.class.getSimpleName();
    private final Query query;
    private ListenerRegistration listenerRegistration = () -> {
    };
    private List<EActivity> eActivities = new ArrayList<>();

    public ActivityListUserLiveData(Query query) {
        this.query = query;
    }

    @Override
    protected void onActive() {
        this.listenerRegistration = query.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        this.listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
        if (querySnapshot != null && !querySnapshot.isEmpty()) {
            eActivities.clear();
            eActivities = querySnapshot.toObjects(EActivity.class);
            this.setValue(eActivities);
        } else if (error != null)
            Log.e(TAG, error.getMessage(), error.getCause());
    }
}
