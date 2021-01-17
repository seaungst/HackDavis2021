package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilePageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button changePasswordButton, backButton, editProfilePhotoButton;

    private CircleImageView profilePageIcon;
    private FirebaseUser user;
    private DatabaseReference reference, databaseReference;
    private String userId;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        changePasswordButton = (Button) findViewById(R.id.reset_password_button);
        changePasswordButton.setOnClickListener(this);

        backButton = (Button) findViewById(R.id.profile_back_button);
        backButton.setOnClickListener(this);

        editProfilePhotoButton = (Button) findViewById(R.id.edit_profile_photo);
        editProfilePhotoButton.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");

        userId = user.getUid();
        mAuth = FirebaseAuth.getInstance();

        profilePageIcon = findViewById(R.id.profile_page_icon);

        final TextView nameTextView = (TextView) findViewById(R.id.profile_name);
        final TextView usernameTextView = (TextView) findViewById(R.id.profile_username);
        final TextView emailTextView = (TextView) findViewById(R.id.profile_email);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String name = userProfile.name;
                    String username = userProfile.username;
                    String email = userProfile.email;

                    nameTextView.setText(name);
                    usernameTextView.setText(username);
                    emailTextView.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ProfilePageActivity.this, "Data fetch error.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        getUserInfo();
    }

    private void getUserInfo() {
        databaseReference.child(mAuth.getCurrentUser().getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists() && snapshot.getChildrenCount() > 0) {
                            if (snapshot.hasChild("image")) {
                                String image = snapshot.child("image").getValue().toString();
                                Picasso.get().load(image).into(profilePageIcon);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(ProfilePageActivity.this,
                                "An error has occurred.", Toast.LENGTH_SHORT).show();
                    }
                });
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
            case R.id.edit_profile_photo:
                startActivity(new Intent(ProfilePageActivity.this,
                        EditProfilePhotoActivity.class));
                break;
        }
    }
}