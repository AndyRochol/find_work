package com.example.incomplete_project;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class communityViewModel extends ViewModel {
    Query query = FirebaseDatabase.getInstance().getReference("Community_posts");
    community_repository repository = new community_repository(query);

    @NonNull
    public LiveData<DataSnapshot> getlivesnapshot() {
        return repository;
    }
}
