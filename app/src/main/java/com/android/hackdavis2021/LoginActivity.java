package com.android.hackdavis2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends AppCompatActivity {

    private Button signupHereButton;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupHereButton = findViewById(R.id.signup_here_button);
        signupHereButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent signupIntent = new Intent(view.getContext(), SignupActivity.class);
                startActivity(signupIntent);
            }
        });

        loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent homepageIntent = new Intent(view.getContext(), HomepageActivity.class);
                startActivity(homepageIntent);
            }
        });

    }
}