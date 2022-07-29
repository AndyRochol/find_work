package com.example.incomplete_project.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.incomplete_project.R;
import com.example.incomplete_project.sign_up;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


public class profile_frag extends Fragment {
    ImageView iv  , ig , bg;
    ActivityResultLauncher<String> launcher;
    Uri iguri;
    FirebaseStorage storage ;
    FirebaseApp firebaseApp;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase database;
    String auth;
    ProgressDialog progress;
    TextView username;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_frag, container, false);

        ig = view.findViewById(R.id.pro);
        bg = view.findViewById(R.id.back);

        iv = view.findViewById(R.id.messege_ic);
        username = view.findViewById(R.id.user_name);

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), (ActivityResultCallback<Uri>) result -> {

        });

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
                if(iguri != null){
                    //
                    String refer = "User_pg";
                    String datarefer = "PgImg";
                    uploadbg(auth,refer,datarefer);
                }

            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
                if(iguri != null){
                    //
                    String refer = "User_bg";
                    String datarefer = "BgImg";
                    uploadbg(auth , refer , datarefer);
                }

            }
        });






        return view;
    }

    private void uploadbg(String auth , String refernce  , String datarefer) {

        progress = new ProgressDialog(getContext());
        progress.setTitle("Uploading");
        progress.setMessage("Please wait");
        progress.show();
        StorageReference reference = storage.getReference(refernce).child(auth);
        reference.putFile(iguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        database.getReference().child(datarefer).child(auth).setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                progress.dismiss();
                                Toast.makeText(getContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progress.dismiss();
                                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            DatabaseReference query = FirebaseDatabase.getInstance().getReference().child(firebaseUser.getUid());
           username.setText( query.getKey());
        }
    }
}