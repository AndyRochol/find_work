package com.example.incomplete_project.Home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.incomplete_project.Post_create;
import com.example.incomplete_project.Profile.ProfileActivity;
import com.example.incomplete_project.R;
import com.example.incomplete_project.communityViewModel;
import com.example.incomplete_project.Notification.notification_activity;
import com.example.incomplete_project.Search.search_activity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home_Activty";

    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerView recyclerView;
    community_adapter  communityAdapter;
    ArrayList<post_community> communityModelArrayList;
    FloatingActionButton floatingActionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        recyclerView = findViewById(R.id.community_recyc);
        floatingActionButton = findViewById(R.id.btn_float);



       // getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer , new home_frag()).commit();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.home_act);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.home_act:
                        return true;

                    case R.id.prof_act:
                        startActivity(new Intent(HomeActivity.this , ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.noti_act:
                        startActivity(new Intent(HomeActivity.this , notification_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.search_act:
                        startActivity(new Intent(HomeActivity.this , search_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;

            }

        });

        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        communityViewModel viewModel = new ViewModelProvider(this).get(communityViewModel.class);
        LiveData<DataSnapshot> liveData = viewModel.getlivesnapshot();

       // linearLayout.setStackFromEnd(true);

        recyclerView.setLayoutManager(linearLayout);
       // recyclerView.setHasFixedSize(true);
        communityModelArrayList =  new ArrayList<>();
        communityAdapter = new community_adapter(communityModelArrayList , getApplicationContext());
        recyclerView.setAdapter(communityAdapter);

     /*  readdata(new firebase() {
           @Override
           public void oncall(ArrayList<post_community> communityModelArrayList) {
                Log.d(TAG , "enter in trap");
           }
       });*/




        FirebaseDatabase.getInstance().getReference("Community_posts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                communityModelArrayList.clear();

                for (DataSnapshot users : snapshot.getChildren()) {
                    Log.d(TAG , "JUST AFTER SNAPSHOT");
                    post_community model = new post_community();
                    //sign_up_mode sign = new sign_up_mode();
                    model.setPublisher_id(users.child("publisher_id").getValue().toString());
                    model.setDescription(users.child("description").getValue().toString());
                    model.setImg_Url(users.child("img_Url").getValue().toString());
                    model.setPost_id(users.child("post_id").getValue().toString());
                    model.setWorker_need(users.child("worker_need").getValue().toString());

                    communityModelArrayList.add(model);
                }

                //fire.oncall(communityModelArrayList);
                communityAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "i m sory" , Toast.LENGTH_LONG).show();

            }

        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this , Post_create.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });


    }



    @Override
    protected void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

    }


}