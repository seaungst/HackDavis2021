package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfilePhotoActivity extends AppCompatActivity implements View.OnClickListener {

    private Button backButton, choosePhotoButton, saveButton;
    private CircleImageView profilePageIcon;

    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    private Uri imageUri;
    private String myUri = "";
    private StorageTask uploadTask;
    private StorageReference storageProfilePicsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_photo);

        backButton = (Button) findViewById(R.id.edit_photo_back_button);
        backButton.setOnClickListener(this);

        saveButton = (Button) findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);

        choosePhotoButton = (Button) findViewById(R.id.choose_photo_button);
        choosePhotoButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User");
        storageProfilePicsRef = FirebaseStorage.getInstance().getReference().child("Profile Pic");

        profilePageIcon = findViewById(R.id.edit_profile_page_icon);

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
                Toast.makeText(EditProfilePhotoActivity.this,
                        "An error has occurred.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_photo_back_button:
                startActivity(new Intent(EditProfilePhotoActivity.this,
                        ProfilePageActivity.class));
                break;
            case R.id.save_button:
                uploadProfileImage();
                break;
            case R.id.choose_photo_button:
                CropImage.activity().setAspectRatio(1,1)
                        .start(EditProfilePhotoActivity.this);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE
                && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();

            profilePageIcon.setImageURI(imageUri);
        }
        else {
            Toast.makeText(this, "Profile photo change error.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void uploadProfileImage() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Setting your profile image...");
        progressDialog.setMessage("Please wait, setting profile image.");
        progressDialog.show();

        if (imageUri != null) {
            final StorageReference fileRef = storageProfilePicsRef
                    .child(mAuth.getCurrentUser().getUid() + ".jpg");
            uploadTask = fileRef.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {

                    if (task.isSuccessful()) {
                        throw task.getException();
                    }
                    else {
                        Toast.makeText(EditProfilePhotoActivity.this,
                                "Initial profile photo selection error.",
                                Toast.LENGTH_SHORT).show();
                    }
                    return fileRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        myUri = downloadUri.toString();

                        HashMap<String, Object> userMap = new HashMap<>();
                        userMap.put("image,", myUri);

                        databaseReference.child(mAuth.getCurrentUser().getUid())
                                .updateChildren(userMap);

                        progressDialog.dismiss();
                    }
                    else {
                        Toast.makeText(EditProfilePhotoActivity.this,
                                "Profile photo selection error.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else {
            progressDialog.dismiss();
            Toast.makeText(this, "Image not selected.", Toast.LENGTH_SHORT).show();
        }
    }
}