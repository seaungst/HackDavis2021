package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private Button backButtonSignup, signupButton;
    private EditText editTextName, editTextEmail, editTextUsername, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mAuth = FirebaseAuth.getInstance();

        backButtonSignup = (Button) findViewById(R.id.signup_back_button);
        backButtonSignup.setOnClickListener(this);

        signupButton = (Button) findViewById(R.id.signup_button);
        signupButton.setOnClickListener(this);

        editTextName = (EditText) findViewById(R.id.signup_name);
        editTextEmail = (EditText) findViewById(R.id.signup_email);
        editTextUsername = (EditText) findViewById(R.id.signup_username);
        editTextPassword = (EditText) findViewById(R.id.signup_password);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_back_button:
                startActivity(new Intent(this, LoginActivity.class));
                break;

            case R.id.signup_button:
                userSignup();
                break;
        }
    }

    private void userSignup() {
        final String name = editTextName.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String username = editTextUsername.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        //Display error if name text field is empty
        if(name.isEmpty()){
            editTextName.setError("Please enter a full name.");
            editTextName.requestFocus();
            return;
        }

        //Display error if email text field is empty
        if(email.isEmpty()){
            editTextEmail.setError("Please enter an email address.");
            editTextEmail.requestFocus();
            return;
        }

        //Display error if email is not in email address format (i.e.: something@email.com)
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address.");
            editTextEmail.requestFocus();
            return;
        }

        //Display error if username text field is empty
        if(username.isEmpty()){
            editTextUsername.setError("Please enter a username.");
            editTextUsername.requestFocus();
            return;
        }

        //Display error if password text field is empty
        if(password.isEmpty()){
            editTextPassword.setError("Please enter a password.");
            editTextPassword.requestFocus();
            return;
        }

        //Display error if password is shorter than 6 characters
        if(password.length() < 6){
            editTextPassword.setError("Please enter a password 6+ characters in length.");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User user = new User(name, username, email);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()){
                                        Toast.makeText(SignupActivity.this,
                                                "Sign-up successful!",
                                                Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(SignupActivity.this,
                                                LoginActivity.class));

                                    }

                                    else  {
                                        Toast.makeText(SignupActivity.this,
                                                "Sign-up failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }
                        else  {
                            Toast.makeText(SignupActivity.this,
                                    "Sign-up failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
        });
    }
}