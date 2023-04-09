package com.example.incomplete_project.Profile;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.incomplete_project.Notification.notification_model;
import com.example.incomplete_project.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class request_adapter extends RecyclerView.Adapter<request_adapter.viewholder> {

    ArrayList<notification_model> alist ;
    FragmentActivity activity;
    FirebaseUser firebaseUser;

    private static final String TAG = "comm_adapter";

    public request_adapter(ArrayList<notification_model> request_model_list, FragmentActivity fragmentActivity) {
        this.alist = request_model_list;
        this.activity = fragmentActivity;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_data_layout,parent,false);
        return new request_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        notification_model search_model = alist.get(position);
        //  holder.t1.setText(search_model.getUser_id());
        //  Glide.with(context).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).load(search_model.getImg_url()).into(holder.i1);
        FirebaseDatabase.getInstance().getReference("Users").child(search_model.getUser_id()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                holder.t1.setText(snapshot.child("username").getValue().toString());
                Glide.with(activity).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).load(snapshot.child("PgImg").getValue().toString()).into(holder.i1);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase.getInstance().getReference("messege_user").child(search_model.getAuth()).child(search_model.getUser_id()).setValue("true").addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(activity.getApplicationContext(), "You can messege now that user" , Toast.LENGTH_LONG).show();
                    }
                });
                FirebaseDatabase.getInstance().getReference("Request_Gallery").child(search_model.getAuth()).child(search_model.user_id).removeValue();
            }
        });



      //  holder.b1.setOnClickListener();
        // holder.t2.setText(search_model.getWork_tag());
    }

    @Override
    public int getItemCount() {
        return alist.isEmpty() ? 0 : alist.size();
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        ImageView i1 ;
        TextView t1 ;
        Button b1;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            i1 = itemView.findViewById(R.id.circleImageView);
            t1  = itemView.findViewById(R.id.name);
            b1 = itemView.findViewById(R.id.accept_btn);
        }
    }

}
