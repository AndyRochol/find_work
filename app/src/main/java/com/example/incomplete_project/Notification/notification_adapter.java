package com.example.incomplete_project.Notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.incomplete_project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class notification_adapter extends RecyclerView.Adapter<notification_adapter.viewholder> {

    ArrayList<notification_model> notification_modelArrayList;
    Context context;
    //FirebaseUser user;

    public notification_adapter(ArrayList<notification_model> notification_modelArrayList, Context context) {
        this.notification_modelArrayList = notification_modelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout,parent,false);
        return new notification_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        notification_model notificationModel = notification_modelArrayList.get(position);
       // holder.t2.setText(notificationModel.getShow_text());
        Glide.with(context).load(notificationModel.getImg_url()).into(holder.i2);

        FirebaseDatabase.getInstance().getReference("Users").child(notificationModel.user_id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Glide.with(context).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL))
                        .load(snapshot.child("PgImg").getValue()).into(holder.i1);
                holder.t1.setText(snapshot.child("username").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }

    @Override
    public int getItemCount() {
        return notification_modelArrayList == null ? 0 : notification_modelArrayList.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder{
        ImageView i1 , i2;
        TextView t1 , t2;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            i1 = itemView.findViewById(R.id.requester_img);
            i2  = itemView.findViewById(R.id.post_image);

            t1  = itemView.findViewById(R.id.requester_name);
            t2 = itemView.findViewById(R.id.fix_text);
        }
    }
}
