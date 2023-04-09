package com.example.incomplete_project.Search;

import android.content.Context;
import android.content.Intent;
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
import com.example.incomplete_project.sign_up_mode;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class search_adapter extends RecyclerView.Adapter<search_adapter.viewholder> {
    ArrayList<search_model> search_model_list;
    Context context;
    FirebaseUser user;

    private final String requested = "REQUESTED";
    private final String request = "REQUEST";

    public search_adapter(ArrayList<search_model> search_model_list , Context context) {
        this.search_model_list = search_model_list;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view  = LayoutInflater.from(parent.getContext()).inflate(R.layout.request_data_layout,parent,false);
        return new search_adapter.viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

        user = FirebaseAuth.getInstance().getCurrentUser();

        final search_model search_mo = search_model_list.get(position);
        holder.t1.setText(search_mo.getName());
        Glide.with(context).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).load(search_mo.getImg()).into(holder.i1);
        holder.t2.setText(search_mo.getTag());
    /*    holder.t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , search_result_activity.class);
                intent.putExtra("work_tag",search_mo.getTag());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });*/



        isrequested(search_mo.getUserid() , holder.b1);
        holder.b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.b1.getTag().toString().equals(request)){
                    FirebaseDatabase.getInstance().getReference("request_list").child(search_mo.getUserid())
                            .child(user.getUid()).setValue(true);


                }
                else{
                    FirebaseDatabase.getInstance().getReference("request_list").child(search_mo.getUserid())
                            .child(user.getUid()).removeValue();


                }

            }
        });

    }

    @Override
    public int getItemCount() {
        return search_model_list == null ? 0 : search_model_list.size();

    }

    public static class viewholder extends RecyclerView.ViewHolder{
        ImageView i1 ;
        TextView t1 , t2;
        Button b1;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            i1 = itemView.findViewById(R.id.circleImageView);
            t1  = itemView.findViewById(R.id.name);
           t2 = itemView.findViewById(R.id.worker);
            b1 = itemView.findViewById(R.id.accept_btn);


        }
    }

    private void isrequested(String user_id, Button b1) {
       // Log.d(TAG , "LOADING THE STATUS");
        user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        if(uid.equals(user_id)){
            b1.setVisibility(View.INVISIBLE);
        }
        else{
            FirebaseDatabase.getInstance().getReference("request_list").child(user_id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(user.getUid()).exists()){
                        b1.setText(requested);
                        b1.setTag("requested");
                       // Log.d(TAG , "LOADING THE REQUESTED");

                    }
                    else{
                       // Log.d(TAG , "LOADING THE DELETE");
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
}
