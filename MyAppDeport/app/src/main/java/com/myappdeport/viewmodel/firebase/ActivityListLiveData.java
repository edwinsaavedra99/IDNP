package com.myappdeport.viewmodel.firebase;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.QuerySnapshot;
import com.myappdeport.model.entity.database.EActivity;

import java.util.List;

public class ActivityListLiveData extends LiveData<List<EActivity>> implements EventListener<QuerySnapshot> {
    private static final String TAG = ActivityListLiveData.class.getSimpleName();
    private final CollectionReference collectionReference;
    private ListenerRegistration listenerRegistration;

    public ActivityListLiveData(CollectionReference collectionReference) {
        this.collectionReference = collectionReference;
    }

    @Override
    protected void onActive() {
        this.listenerRegistration = collectionReference.addSnapshotListener(this);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        this.listenerRegistration.remove();
        super.onInactive();
    }

    @Override
    public void onEvent(@Nullable QuerySnapshot querySnapshot, @Nullable FirebaseFirestoreException error) {
        if (querySnapshot != null && !querySnapshot.getDocuments().isEmpty())
            this.setValue(querySnapshot.toObjects(EActivity.class));
        else if (error != null)
            Log.e(TAG, error.getMessage(), error.getCause());
    }
}
