package com.example.incomplete_project.Search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;

import com.example.incomplete_project.Home.HomeActivity;
import com.example.incomplete_project.Profile.ProfileActivity;
import com.example.incomplete_project.R;
import com.example.incomplete_project.Notification.notification_activity;
import com.example.incomplete_project.sign_up_mode;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_activity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    SearchView searchView;
    RecyclerView recyclerView;
    search_adapter adapter;
    ArrayList<search_model> searchModels;
    private static final String TAG = "search_act";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.search_recycler);
        bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setSelectedItemId(R.id.search_act);
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                //Fragment fragment = null;

                switch (item.getItemId()) {
                    case R.id.prof_act:
                        startActivity(new Intent(search_activity.this , ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.home_act:
                        startActivity(new Intent(search_activity.this , HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.noti_act:
                        startActivity(new Intent(search_activity.this , notification_activity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                        overridePendingTransition(0 , 0);
                        break;

                    case R.id.search_act:
                        return true;


                }
                return false;

            }

        });

        loaduser();

        searchModels = new ArrayList<>();
        LinearLayoutManager linearLayout = new LinearLayoutManager(getApplicationContext());
        adapter = new search_adapter(searchModels , getApplicationContext());
        recyclerView.setLayoutManager(linearLayout);
        recyclerView.setAdapter(adapter);

     /*   searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //loadusers(query.toLowerCase());
                Log.d(TAG , "lets start");
              Query query1 = FirebaseDatabase.getInstance().getReference("Community_posts").orderByChild("worker_need")
                       .startAt(query).endAt(query +"\uf8ff");

              query1.addValueEventListener(new ValueEventListener() {
                  @Override
                  public void onDataChange(@NonNull DataSnapshot snapshot) {
                      searchModels.clear();
                      Log.d(TAG , "STEP 2");
                      for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                          search_model model = new search_model();
                         // model.setUsername(dataSnapshot.child("username").getValue().toString());
                        //  model.setImglink(dataSnapshot.child("PgImg").getValue().toString());
                          model.setTag(dataSnapshot.child("worker_need").getValue().toString());
                         // model.setUid(dataSnapshot.child("user_id").getValue().toString());

                          searchModels.add(model);
                      }
                      adapter.notifyDataSetChanged();
                  }

                  @Override
                  public void onCancelled(@NonNull DatabaseError error) {

                  }
              });

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

*/

    }

    private void loaduser() {

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG , "loading it2");
                searchModels.clear();
                if(TextUtils.isEmpty(searchView.getQuery().toString())){
                    for(DataSnapshot dataSnapshot :snapshot.getChildren()){
                    search_model mode = new search_model();
                    mode.setName(dataSnapshot.child("username").getValue().toString());
                    mode.setImg(dataSnapshot.child("PgImg").getValue().toString());
                    mode.setTag(dataSnapshot.child("worker_need").getValue().toString());
                    mode.setUserid(dataSnapshot.child("user_id").getValue().toString());

                        searchModels.add(mode);
                    }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
