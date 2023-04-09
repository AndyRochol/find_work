package com.example.incomplete_project.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.incomplete_project.Home.HomeActivity;
import com.example.incomplete_project.R;
import com.example.incomplete_project.Notification.notification_activity;
import com.example.incomplete_project.Search.search_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer2 , new profile_frag()).commit();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.prof_act);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.prof_act:
                        return true;

                    case R.id.home_act:
                        startActivity(new Intent(ProfileActivity.this , HomeActivity.class));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.noti_act:
                        startActivity(new Intent(ProfileActivity.this , notification_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.search_act:
                        startActivity(new Intent(ProfileActivity.this , search_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0,0);
                        break;


                }
                return false;

            }

        });
    }
}