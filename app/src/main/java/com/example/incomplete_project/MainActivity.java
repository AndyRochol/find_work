package com.example.incomplete_project;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;



public class MainActivity extends AppCompatActivity {

    private TextInputLayout email, passwor;
    private Button  login;

   private TextView register , forgotpass;
   private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth = FirebaseAuth.getInstance();
        final Intent i = new Intent(MainActivity.this , navigation_Activity.class);

        email = findViewById(R.id.editText);
        login = findViewById(R.id.login);
        passwor = findViewById(R.id.editText2);
        register = findViewById(R.id.sign_text);
        forgotpass = findViewById(R.id.forgot_password);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(i);
            }
        });

        login.setOnClickListener(v -> LogIn());

        (passwor.getEditText()).setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE)
                LogIn();
            return false;
        });
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
        try {
            InputMethodManager inputManager = (InputMethodManager)
                    getSystemService(Context.INPUT_METHOD_SERVICE);

            assert inputManager != null;
            inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception ignored) {

        }

        String Stremail = email.getEditText().toString().trim();
        String Strpass = passwor.getEditText().toString().trim();
       // Matcher m = Email_look.matcher(Stremail);


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


        } else {
            email.setError(null);
            passwor.setError(null);
            firebaseAuth.signInWithEmailAndPassword(Stremail, Strpass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (!task.isSuccessful()) {

                        firebaseAuth.fetchSignInMethodsForEmail(Stremail).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                            @Override
                            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                boolean b = !task.getResult().getSignInMethods().isEmpty();

                                if (b) {
                                    Toast.makeText(getApplicationContext(), "Incorrect Password!", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "User not Registered!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }
            }
        });

    }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if (firebaseUser != null && firebaseUser.isEmailVerified()) {
            startActivity(new Intent(this, sign_up.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            finish();
        }

    }

    public void forgotpassword(View view) {
        String Stremail = email.getEditText().getText().toString();
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