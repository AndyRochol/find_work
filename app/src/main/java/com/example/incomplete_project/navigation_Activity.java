package com.example.incomplete_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.incomplete_project.Views.home_frag;
import com.example.incomplete_project.Views.notification_frag;
import com.example.incomplete_project.Views.profile_frag;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class navigation_Activity extends AppCompatActivity {

    Fragment prof_frag = new profile_frag();
    Fragment home_frag = new home_frag();
    Fragment noti_frag = new notification_frag();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_);


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.navi_lay);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home_act:
                        fragment = home_frag;
                        break;

                    case R.id.noti_act:
                        fragment = noti_frag;
                        break;
                    case R.id.prof_act:
                        fragment = prof_frag;
                        break;
                    default:
                        return false;

                }
                loadfragments(fragment);

                return true;
        }

    });
}

    private void loadfragments(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainer, fragment)
                    .addToBackStack(null)
                    .commit();

        }
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragmentContainer) instanceof home_frag) {
            finish();
        } else if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStackImmediate();
        } else
            super.onBackPressed();
    }
}