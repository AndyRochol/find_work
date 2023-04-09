package com.example.incomplete_project;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.incomplete_project.Home.HomeActivity;
import com.example.incomplete_project.Home.post_community;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Post_create extends AppCompatActivity {
    EditText description ;
    AppCompatEditText work_need;
    AppCompatButton btn;
    FloatingActionButton floatingActionButton ;
    ImageView post_image;
    ActivityResultLauncher<String> resultLauncher;
    Uri imuri;
    FirebaseAuth auth;
    String mauth , postid;
    FirebaseStorage storage;
    ProgressDialog progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_create);

        description = findViewById(R.id.description);
        work_need = findViewById(R.id.helper_need);
        btn = findViewById(R.id.submit_btn);
        post_image = findViewById(R.id.post_img);
        floatingActionButton = findViewById(R.id.img_select);


        resultLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(), (ActivityResultCallback<Uri>) result -> {
            if(result != null){
                post_image.setImageURI(result);
                imuri = result;
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultLauncher.launch("image/*");

            }
        });



        btn.setOnClickListener(view -> {
            String desc =description.getText().toString();
            String worker = work_need.getText().toString();
            if(imuri == null || description == null || work_need == null) {
                Toast.makeText(getApplicationContext() , "You cannot post empty Data" , Toast.LENGTH_SHORT).show();
            }
            else{
                find_upload(imuri , desc ,worker, mauth);
            }
        });


    }

    private void find_upload(Uri img_url , String Description  , String Work_need ,String mauth){

   /*    StorageReference reference = storage.getReference("community_posts").child(mauth);
        reference.putFile(imuri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.show();
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        FirebaseDatabase.getInstance().getReference().child(datarefer).child(auth).setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
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


*/
        progress = new ProgressDialog(Post_create.this);
        progress.setTitle("Uploading");
        progress.setMessage("Please wait");
        progress.show();
        storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference("community_posts").child(System.currentTimeMillis() + " " + getFilesDir());
        reference.putFile(img_url).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        DatabaseReference ref =  FirebaseDatabase.getInstance().getReference().child("Community_posts");
                        postid = ref.push().getKey();

                        post_community postCommunity = new post_community( uri.toString() , Description , Work_need , mauth ,postid);
                        postCommunity.setPost_id(postid);
                        FirebaseDatabase.getInstance().getReference().child("Community_posts").push()
                                .setValue(postCommunity).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(task.isSuccessful()){
                                            progress.dismiss();
                                            Intent i = new Intent();
                                           // requireActivity().getSupportFragmentManager().beginTransaction().remove(post_update.this).commit();
                                            Toast.makeText(getApplicationContext() , "Uploaded successfully" , Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(Post_create.this , HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

                                        }
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext() , "Try again ," , Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext() , "Hurdles in uploading",Toast.LENGTH_LONG).show();
                    }
                });
            }});


        //FirebaseDatabase.getInstance().getReference("Community_posts").child()

    }

    @Override
    public void onStart() {
        super.onStart();
        auth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = auth.getCurrentUser();
        if(firebaseUser != null){
            mauth = firebaseUser.getUid();
        }
    }

}