package com.example.incomplete_project.Profile;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class messengerbox_adapter extends RecyclerView.Adapter<messengerbox_adapter.viewholder> {


    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class viewholder extends RecyclerView.ViewHolder{

        public viewholder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
