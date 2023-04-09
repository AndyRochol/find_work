package com.example.incomplete_project.Profile;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.example.incomplete_project.Notification.notification_model;
import com.example.incomplete_project.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


public class profile_frag extends Fragment {
    ImageView   ig , bg;
    ActivityResultLauncher<String> launcher , launcher1;
    private static final String TAG = "profile_frag";
    Uri iguri , bguri;
    FirebaseStorage storage ;
    RecyclerView rcyv;
    int x ;
    FirebaseAuth firebaseAuth;
   // private static boolean flag = false;
    FirebaseDatabase database;
    String auth;
    request_adapter requestAdapter;
  //  ArrayList<notification_model> arrayList;
    ProgressDialog progress;
    TextView username, requirement , country , iv , tr;
    EditText ed1;
    CardView cv , cv2 , cv3;

    String st , req_id;
    FloatingActionButton floatingActionButton;
    //TextView name , country;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_frag, container, false);

        ig = view.findViewById(R.id.pro);
        bg = view.findViewById(R.id.back);
        cv2 = view.findViewById(R.id.cardView5);
        iv = view.findViewById(R.id.rqr_text);
        username = view.findViewById(R.id.user_name);
        country = view.findViewById(R.id.location);
        requirement = view.findViewById(R.id.rquire_tet);
        cv3 = view.findViewById(R.id.cardView6);
        cv = view.findViewById(R.id.rquir_cv);
        floatingActionButton = view.findViewById(R.id.fab);
        tr = view.findViewById(R.id.tr_view);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user != null){
            auth = user.getUid();
        }


        progress = new ProgressDialog(getContext());
        progress.setTitle("Uploading");
        progress.setMessage("Please wait");

        Query databaseReference =  FirebaseDatabase.getInstance().getReference("Users").child(auth);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String im1 = snapshot.child("BgImg").getValue(String.class);
                String im2 = snapshot.child("PgImg").getValue(String.class);
                if(im1 != null) {
                    Glide.with(requireActivity()).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).load(im1).into(bg);
                }
                if(im2 != null){
                    Glide.with(requireActivity()).applyDefaultRequestOptions(new RequestOptions().override(Target.SIZE_ORIGINAL)).load(im2).into(ig);
                }
                username.setText(snapshot.child("username").getValue().toString());
                country.setText(snapshot.child("country").getValue().toString());
                requirement.setText(snapshot.child("work_tag").getValue().toString());
                iv.setText(snapshot.child("need_in_no").getValue().toString());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), (CharSequence) error,Toast.LENGTH_SHORT).show();
            }
        });


        FirebaseDatabase.getInstance().getReference("Request_Gallery").child(auth).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tr.setText(String.valueOf( snapshot.getChildrenCount()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        launcher = registerForActivityResult(new ActivityResultContracts.GetContent(), (ActivityResultCallback<Uri>) result -> {
            if(result != null){
                progress.show();
                ig.setImageURI(result);
                iguri = result;
                String refer = "User_pg";
                String datarefer = "PgImg";
                Toast.makeText(getActivity() , "get into process",Toast.LENGTH_LONG).show();

                uploadbg(auth,refer,datarefer , iguri);
            }
        });

        launcher1 = registerForActivityResult(new ActivityResultContracts.GetContent(),(ActivityResultCallback<Uri>) result ->{
            if(result != null){

                progress.show();
                bg.setImageURI(result);
                bguri = result;
                String refer = "User_bg";
                String datarefer = "BgImg";
                uploadbg(auth , refer , datarefer , bguri);
            }
        });

        ig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launcher.launch("image/*");
            }
        });

        bg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(getActivity() , "LETS GO",Toast.LENGTH_LONG).show();
                launcher1.launch("image/*");
            }
        });

        cv2.setOnClickListener(v ->{
            AlertDialog.Builder show_request = new AlertDialog.Builder(requireActivity());
            LayoutInflater inflate = requireActivity().getLayoutInflater();
            View view1= inflate.inflate(R.layout.request_list , null);

            Log.d("pro" , "start cv response");
            rcyv = view1.findViewById(R.id.recyc);
            LinearLayoutManager linearLayout = new LinearLayoutManager(requireActivity() , LinearLayoutManager.VERTICAL , false);
            Log.d("pro" , "start cv response2323");

            rcyv.setLayoutManager(linearLayout);
            ArrayList<notification_model> arrayList;
            arrayList = new ArrayList<>();
            requestAdapter = new request_adapter(arrayList , requireActivity());
            rcyv.setAdapter(requestAdapter);


            FirebaseDatabase.getInstance().getReference("Notification").child(auth).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                   // Log.d("pro", "start getting");
                    arrayList.clear();
                    for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                        notification_model model = new notification_model();
                         model.setUser_id(dataSnapshot.child("requesting_id").getValue().toString());
                         model.setAuth(auth);

                        arrayList.add(model);
                    }

                    requestAdapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

            show_request.setView(view1);
            show_request.setNegativeButton(android.R.string.cancel , ((dialog, which) -> {
                dialog.dismiss();

            }));

            show_request.show();


        });

        cv3.setOnClickListener( v ->{
            Fragment newfragment = new messenger_box();
            FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
            transaction.replace(R.id.fragmentContainer2 , newfragment ).addToBackStack("").commit();
        });


        cv.setOnClickListener(v -> {
            AlertDialog.Builder take_input = new AlertDialog.Builder(requireActivity());
            take_input.setTitle("Your Requirement");
            LayoutInflater inflat = requireActivity().getLayoutInflater();
            View view1 = inflat.inflate(R.layout.progress_dialog , null);
            take_input.setView(view1);
             ed1 = view1.findViewById(R.id.work_req_id);
             EditText ed2 = view1.findViewById(R.id.no_of_work);
             ed2.setInputType(InputType.TYPE_CLASS_NUMBER);
             ed1.setInputType(InputType.TYPE_CLASS_TEXT);

            take_input.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                st = ed1.getText().toString();
                x = Integer.parseInt(ed2.getText().toString());
                if(!st.isEmpty() && ed2.getText().toString() == null){
                    Toast.makeText(getContext(),"put number of worker needed",Toast.LENGTH_SHORT).show();
                }
                if(st.isEmpty() && ed2.getText().toString() != null){
                    Toast.makeText(getContext(),"Type of worker need",Toast.LENGTH_SHORT).show();
                }
                else{
                         dialog.dismiss();
                         progress.show();
                          requirement.setText(st.toLowerCase(Locale.ROOT));
                          requirement.setTextColor(Color.parseColor("#FF000000"));
                          iv.setText(String.valueOf(x));
                          iv.setTextColor(Color.parseColor("#FF000000"));

                        FirebaseDatabase.getInstance().getReference("Users").child(auth).child("work_tag").setValue(st.toLowerCase(Locale.ROOT));
                        FirebaseDatabase.getInstance().getReference("Users").child(auth).child("need_in_no").setValue(x);

                        progress.dismiss();
                }


            });

             take_input.setNegativeButton(android.R.string.cancel , ((dialog, which) -> {
                 dialog.dismiss();

             }));
             take_input.show();
            Log.d(TAG , "STEP 4 END");

        });


        return view;
    }

    private void uploadbg(String auth , String refernce  , String datarefer , Uri uri) {

        Log.d(TAG , "upload calls");

        storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference(refernce).child(auth);
        reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
            Log.d(TAG , "storage main store");
            reference.getDownloadUrl().addOnSuccessListener(uri1 -> {

                Log.d(TAG , "towards the updation in real time");
                FirebaseDatabase.getInstance().getReference("Users").child(auth).child(datarefer).setValue(uri1.toString())
                        .addOnSuccessListener(aVoid -> {
                            progress.dismiss();
                            Log.d(TAG , "FINALLY STORED");
                            Toast.makeText(getContext(),"Uploaded successfully",Toast.LENGTH_SHORT).show();
                        }).addOnFailureListener(e -> {
                            progress.dismiss();
                            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        });
            }).addOnFailureListener(e -> Toast.makeText(getActivity(), "AN ERROR OCCURED", Toast.LENGTH_SHORT).show());
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            auth = firebaseUser.getUid();
            //DatabaseReference query = FirebaseDatabase.getInstance().getReference("Users").child(auth);
        }


    }
}