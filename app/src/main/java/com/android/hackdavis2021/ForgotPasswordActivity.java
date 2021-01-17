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
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private Button resetPasswordButton, backButton;

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        backButton = (Button) findViewById(R.id.forgot_password_back_button);
        backButton.setOnClickListener(this);

        resetPasswordButton = (Button) findViewById(R.id.forgot_password_reset_button);
        resetPasswordButton.setOnClickListener(this);

        editTextEmail = (EditText) findViewById(R.id.forgot_password_email);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.forgot_password_back_button:
                startActivity(new Intent(ForgotPasswordActivity.this,
                        SignupActivity.class));
                break;

            case R.id.forgot_password_reset_button:
                resetPassword();
                break;
        }
    }

    private void resetPassword() {
        String email = editTextEmail.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Please enter an email.");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please provide a valid email address.");
            editTextEmail.requestFocus();
            return;
        }

        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()) {
                    Toast.makeText(ForgotPasswordActivity.this, "Email sent!",
                            Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ForgotPasswordActivity.this,
                            LoginActivity.class));
                }
                else {
                    Toast.makeText(ForgotPasswordActivity.this,
                            "Email not registered.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}