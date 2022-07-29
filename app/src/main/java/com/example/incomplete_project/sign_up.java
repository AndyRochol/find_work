package com.example.incomplete_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.database.FirebaseDatabase;

public class sign_up extends AppCompatActivity {

    TextInputLayout email_id , Full_name , password , nation;
    Button b1;
    TextView t1 , t2;
    FirebaseAuth auth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        //FirebaseAuth auth = FirebaseAuth.getInstance();
        email_id = findViewById(R.id.text_mail);
        Full_name = findViewById(R.id.text_name);
        password = findViewById(R.id.text_pass);
        nation = findViewById(R.id.text_nation);

        b1 = findViewById(R.id.sign_up_btn);
        t1 = findViewById(R.id.editText5);

        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(sign_up.this , MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sign_up();
            }
        });

        email_id.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email_id.setError(null);
            }
        });
        password.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                password.setError(null);
            }
        });

        Full_name.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Full_name.setError(null);
            }
        });
    }

    private void Sign_up(){
        String Stremail =  email_id.getEditText().toString().trim();
        String Strfull_name = Full_name.getEditText().toString().trim();
        String Strpass = password.getEditText().toString().trim();
        String Strnation = nation.getEditText().toString().trim();

        if (Stremail.isEmpty() && Strpass.isEmpty() && Strfull_name.isEmpty()) {

            password.setError("Enter a valid password");
            Full_name.setError("Enter your Full name");
            email_id.setError("Enter your email address");
        } else if (Stremail.isEmpty()) {
            email_id.setError("Enter your Email address");

        }else if(! Patterns.EMAIL_ADDRESS.matcher(Stremail).matches()){
            email_id.setError("Enter your valid email address");
        }
        else if (Strpass.isEmpty()) {
            password.setError("Enter a valid password");
        } else if (Strfull_name.isEmpty()) {
            Full_name.setError("Enter your name");
        }
        else {
            auth.fetchSignInMethodsForEmail(Stremail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    if(! (task.getResult().getSignInMethods().isEmpty())){
                        Toast.makeText(getApplicationContext() , "Email is already regitered ",Toast.LENGTH_LONG).show();
                    }
                    else{
                        auth.createUserWithEmailAndPassword(Stremail ,Strpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    String uid = task.getResult().getUser().getUid();
                                    if(uid != null){
                                        FirebaseDatabase.getInstance().getReference().child(uid).setValue(Full_name);
                                    }

                                    auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(getApplicationContext() , "Registration is done , Email is sent for verification",Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });

                                    Toast.makeText(getApplicationContext() , "Login with your credentials",Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(sign_up.this, MainActivity.class);
                                    startActivity(intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
                                    finish();
                                }
                            }
                        });
                    }
                }
            });
        }

    }
}