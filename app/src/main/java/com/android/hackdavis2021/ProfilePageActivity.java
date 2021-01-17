package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changePasswordButton, backButton;

    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        changePasswordButton = (Button) findViewById(R.id.reset_password_button);
        changePasswordButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.profile_back_button);
        backButton.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        final TextView nameTextView = (TextView) findViewById(R.id.profile_name);
        final TextView usernameTextView = (TextView) findViewById(R.id.profile_username);
        final TextView emailTextView = (TextView) findViewById(R.id.profile_email);

        /*
        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }
        });

         */

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reset_password_button:
                startActivity(new Intent(ProfilePageActivity.this,
                        ForgotPasswordActivity.class));
                break;

            case R.id.profile_back_button:
                startActivity(new Intent(ProfilePageActivity.this,
                        HomepageActivity.class));
                break;
        }
    }
}