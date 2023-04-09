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

import com.example.incomplete_project.Home.HomeActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {

    private TextInputLayout email, passwor;
    private TextInputEditText ed1 , ed2;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        final Intent i = new Intent(MainActivity.this , sign_up.class);

        email = findViewById(R.id.editText);
        Button login = findViewById(R.id.login);
        passwor =findViewById(R.id.edit_text2);
        ed2 = findViewById(R.id.password);
        TextView register = findViewById(R.id.sign_text);
        TextView forgotpass = findViewById(R.id.forgot_password);
        ed1 = findViewById(R.id.user_id);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        login.setOnClickListener(v -> LogIn());


        email.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                email.setError(null);
            }
        });
       passwor.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                passwor.setError(null);
            }
        });

        forgotpass.setOnClickListener(this::forgotpassword);

    }

  //  public final Pattern Email_look= Pattern.compile(regex);

    public void LogIn() {
        String Stremail = ed1.getText().toString().trim();
        String Strpass = ed2.getText().toString().trim();

        if (Stremail.isEmpty() && Strpass.isEmpty()) {
            email.setError("Enter your Email address");
            passwor.setError("Enter a Valid password");


        } else if (Stremail.isEmpty() ) {
            email.setError("Enter your Email address");


        } else if(! Patterns.EMAIL_ADDRESS.matcher(Stremail).matches()){
                email.setError("Enter your valid email address");
        }
        else if (Strpass.isEmpty() ) {
            passwor.setError("Enter a valid password");
            Toast.makeText(getApplicationContext() , "Give password",Toast.LENGTH_LONG).show();

        } else {
            email.setError(null);
            passwor.setError(null);
            firebaseAuth.signInWithEmailAndPassword(Stremail, Strpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        startActivity(new Intent(getApplicationContext() , HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP ));
                        //Toast.makeText(getApplicationContext(), "Welcome to BE_HIND US", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Wrong Email Id or Password", Toast.LENGTH_SHORT).show();
                    }
            }
        });

    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser != null) {
           // String databaseReference = FirebaseDatabase.getInstance().getReference().push().getKey();
            startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }

    }

    public void forgotpassword(View view) {
        String Stremail = ed1.getText().toString();
        if (Stremail.isEmpty())
            email.setError("Enter your Email address");
        else

            FirebaseAuth.getInstance().sendPasswordResetEmail(Stremail).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful())
                        Toast.makeText(getApplicationContext(), "Password Reset Email sent. Check Inbox", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getApplicationContext(), "Email not registered!", Toast.LENGTH_LONG).show();
                }
            });
    }
}