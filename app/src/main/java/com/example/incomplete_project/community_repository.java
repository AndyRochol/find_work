package com.example.incomplete_project;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class community_repository extends LiveData<DataSnapshot> {
    private final Query query;
    private final mylistner listner = new mylistner();


    community_repository(Query query){
        this.query = query;
    }

    community_repository(DatabaseReference dref){
        this.query = dref;
    }

    @Override
    protected void onActive() {
        super.onActive();
        query.addValueEventListener(listner);
    }

    @Override
    protected void onInactive() {
        super.onInactive();
        query.removeEventListener(listner);
    }

    private class mylistner implements ValueEventListener{
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            setValue(snapshot);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {
            String tag = "community_repository";
            Log.e(tag, error.toString());
        }
    }
}
