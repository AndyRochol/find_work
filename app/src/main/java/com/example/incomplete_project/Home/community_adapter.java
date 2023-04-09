package com.example.incomplete_project.Home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.incomplete_project.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class community_adapter extends  RecyclerView.Adapter<community_adapter.viewholder> {
    ArrayList<post_community> communityModelArrayList ;
   Context context ;
    FirebaseUser firebaseUser;

    private final String requested = "REQUESTED";
    private final String request = "REQUEST";
    private final String text = "requesting you";

    private static final String TAG = "comm_adapter";

    public community_adapter(ArrayList<post_community> communityModelArrayList,Context context) {
        this.communityModelArrayList = communityModelArrayList;
        //this.activity = activity;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d(TAG , "ON_CREATE_VIEW");
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.community_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        post_community postCommunity = communityModelArrayList.get(position);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        Log.d(TAG , "ON_BIND_VIEW" );

        FirebaseDatabase.getInstance().getReference().child("Users").child(postCommunity.getPublisher_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               // sign_up_mode sign = snapshot.getValue(sign_up_mode.class);
                holder.t1.setText(snapshot.child("username").getValue().toString());
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).
                        load(snapshot.child("PgImg").getValue().toString()).into(holder.i1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Log.d(TAG , "after name getting");
        holder.t2.setText(communityModelArrayList.get(position).getDescription());
        holder.t3.setText(communityModelArrayList.get(position).getWorker_need());
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().override(730 , 656)).
                load(communityModelArrayList.get(position).getImg_Url()).into(holder.i2);


        userornot(holder.b1 , postCommunity.publisher_id , postCommunity.getPost_id());
       // isrequested(holder.b1 , postCommunity.getPost_id());



        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.b1.getTag().toString().equals(request)){
                   FirebaseDatabase.getInstance().getReference("Request_Gallery").child(postCommunity.getPost_id())
                            .child(firebaseUser.getUid()).setValue(true);
                    notification(postCommunity.getImg_Url() , postCommunity.getPublisher_id() , firebaseUser.getUid());
                  //request_list(firebaseUser.getUid() , postCommunity.publisher_id);
                }
                else{
                    FirebaseDatabase.getInstance().getReference("Request_Gallery").child(postCommunity.getPost_id())
                                    .child(firebaseUser.getUid()).removeValue();
                }

            }
        });
    }



    @Override
    public int getItemCount() {
        return communityModelArrayList == null ? 0 : communityModelArrayList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        ImageView i1 , i2;
        TextView t1 , t2 , t3;
        Button b1;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            i1 = itemView.findViewById(R.id.top_img);
            i2  = itemView.findViewById(R.id.post_img);

            t1  = itemView.findViewById(R.id.com_name);
            t2 = itemView.findViewById(R.id.caption);
            t3 = itemView.findViewById(R.id.work_tag);

            b1 = itemView.findViewById(R.id.request);
        }
    }

    private void userornot(Button b1 , String publisher_id , String post_id){
        String user = firebaseUser.getUid();
        if(user.equals(publisher_id)){
            b1.setVisibility(View.INVISIBLE);
        }
        else{
            FirebaseDatabase.getInstance().getReference("Request_Gallery").child(post_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(user).exists()) {
                        b1.setText(requested);
                        b1.setTag("requested");
                        Log.d(TAG, "LOADING THE REQUESTED");

                    } else {
                        Log.d(TAG, "LOADING THE DELETE");
                        b1.setText(request);
                        b1.setTag(request);

                    }
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }



  /*  private void isrequested( Button b1 , String post_id) {
        Log.d(TAG , "LOADING THE STATUS");

        FirebaseDatabase.getInstance().getReference("Request_Gallery").child(post_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.child(firebaseUser.getUid()).exists()) {
                        b1.setText(requested);
                        b1.setTag("requested");
                        Log.d(TAG, "LOADING THE REQUESTED");

                    } else {
                        Log.d(TAG, "LOADING THE DELETE");
                        b1.setText(request);
                        b1.setTag(request);

                    }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }*/

    private void notification(String s1 , String s2 , String s3){
        HashMap<String , Object> map = new HashMap<>();
        map.put("img_url",s1);
        map.put("requesting_id" , s3);
        //map.put("text",text);
        FirebaseDatabase.getInstance().getReference("Notification").child(s2).push().setValue(map);
    }




}
