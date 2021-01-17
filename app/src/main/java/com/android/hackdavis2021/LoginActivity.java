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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signupHereButton, loginButton, forgotPasswordButton;
    private EditText editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupHereButton = (Button) findViewById(R.id.signup_here_button);
        signupHereButton.setOnClickListener(this);

        loginButton = (Button) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

        forgotPasswordButton = (Button) findViewById(R.id.forgot_password_button);
        forgotPasswordButton.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.login_email);
        editTextPassword = (EditText) findViewById(R.id.login_password);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.signup_here_button:
                startActivity(new Intent(this, SignupActivity.class));
                break;

            case R.id.login_button:
                userLogin();
                break;

            case R.id.forgot_password_button:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }
    }

    private void userLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Please enter an email.");
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address.");
            editTextEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Please enter a password.");
            editTextPassword.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Login successful!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,
                            HomepageActivity.class));
                }
                else {
                    Toast.makeText(LoginActivity.this, "Incorrect email or password.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}