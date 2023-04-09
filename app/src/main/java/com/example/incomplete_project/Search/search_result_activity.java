package com.example.incomplete_project.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.incomplete_project.Home.HomeActivity;
import com.example.incomplete_project.Notification.notification_activity;
import com.example.incomplete_project.Profile.ProfileActivity;
import com.example.incomplete_project.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class search_result_activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    RecyclerView recyclerView;
    search_adapter adapter;
    ArrayList<search_model> searchModels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        recyclerView = findViewById(R.id.search_recycle);
        bottomNavigationView = findViewById(R.id.bottomNavI);
        bottomNavigationView.setSelectedItemId(R.id.search_act);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.prof_act:
                        startActivity(new Intent(search_result_activity.this , ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.home_act:
                        startActivity(new Intent(search_result_activity.this , HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.noti_act:
                        startActivity(new Intent(search_result_activity.this , notification_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.search_act:
                        return true;


                }
                return false;

            }

        });
        Intent intent = new Intent();
        String s = intent.getStringExtra("work_tag");

        loaduser(s);

        searchModels = new ArrayList<>();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        adapter = new search_adapter(searchModels , getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);


    }

    private void loaduser(String s) {


    }
}