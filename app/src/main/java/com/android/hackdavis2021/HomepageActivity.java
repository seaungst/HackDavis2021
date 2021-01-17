package com.android.hackdavis2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signoutButton, profileButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        signoutButton = findViewById(R.id.sign_out_button);
        signoutButton.setOnClickListener(this);

        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(HomepageActivity.this, "Signed out successfully!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomepageActivity.this, LoginActivity.class));
                break;

            case R.id.profile_button:
                startActivity(new Intent(HomepageActivity.this,
                        ProfilePageActivity.class));
                 break;
        }
    }
}