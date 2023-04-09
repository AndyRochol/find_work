package com.example.incomplete_project.Notification;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.incomplete_project.Home.HomeActivity;
import com.example.incomplete_project.Profile.ProfileActivity;
import com.example.incomplete_project.R;
import com.example.incomplete_project.Search.search_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class notification_activity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<notification_model> modelArrayList;
    notification_adapter notificationAdapter;
    BottomNavigationView bottomNavigationView ;
    FirebaseUser user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.noti_act);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home_act:
                        startActivity(new Intent(notification_activity.this , HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.prof_act:
                        startActivity(new Intent(notification_activity.this , ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.noti_act:
                        return true;

                    case R.id.search_act:
                        startActivity(new Intent(notification_activity.this , search_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0,0);
                        break;


                }
                return false;

            }

        });
        recyclerView = findViewById(R.id.noti_recyle);
        modelArrayList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        notificationAdapter = new notification_adapter(modelArrayList , getApplicationContext());
        recyclerView.setAdapter(notificationAdapter);
        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference("Notification").child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                modelArrayList.clear();
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                 notification_model model = new notification_model();

               // model.setShow_text(dataSnapshot.child("text").getValue().toString());
                model.setUser_id(dataSnapshot.child("requesting_id").getValue().toString());
                model.setImg_url(dataSnapshot.child("img_url").getValue().toString());
                modelArrayList.add(model);
            }
                notificationAdapter.notifyDataSetChanged();
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }
}