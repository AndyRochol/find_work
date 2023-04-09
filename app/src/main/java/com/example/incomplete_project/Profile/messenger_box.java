package com.example.incomplete_project.Profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.incomplete_project.R;


public class messenger_box extends Fragment {

    public messenger_box() {
        // Required empty public constructor
    }

    RecyclerView recy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_messenger_box, container, false);

        recy = view.findViewById(R.id.messege_recy);

        return view;
    }
}