package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Random;

import com.android.hackdavis2021.UserData.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button postButton, signoutButton, profileButton, chatroomButton;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        postButton = findViewById(R.id.post_button);
        postButton.setOnClickListener(this);

        signoutButton = findViewById(R.id.sign_out_button);
        signoutButton.setOnClickListener(this);

        profileButton = findViewById(R.id.profile_button);
        profileButton.setOnClickListener(this);

        chatroomButton = findViewById(R.id.chatroom_button);
        chatroomButton.setOnClickListener(this);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userId = user.getUid();

        final TextView nameTextView = (TextView) findViewById(R.id.homepage_name);
        final TextView inspo = (TextView) findViewById(R.id.inspiration);

        reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue(User.class);

                if(userProfile != null) {
                    String name = userProfile.name;

                    nameTextView.setText("Welcome, " + name + "!");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HomepageActivity.this, "Data fetch error.",
                        Toast.LENGTH_SHORT).show();
            }
        });

        Random rand = new Random();
        int num = rand.nextInt(19);

        switch(num) {
            case 0:
                inspo.setText("Be so good they can’t ignore you. – Steve Martin");
                break;
            case 1:
                inspo.setText("Love For All, Hatred For None. – Khalifatul Masih III");
                break;
            case 2:
                inspo.setText("Change the world by being yourself. – Amy Poehler");
                break;
            case 3:
                inspo.setText("Every moment is a fresh beginning. – T.S Eliot");
                break;
            case 4:
                inspo.setText("Never regret anything that made you smile. – Mark Twain");
                break;
            case 5:
                inspo.setText("Die with memories, not dreams. – Unknown");
                break;
            case 6:
                inspo.setText("Aspire to inspire before we expire. – Unknown");
                break;
            case 7:
                inspo.setText("Let the beauty of what you love be what you do. – Rumi");
                break;
            case 8:
                inspo.setText("Simplicity is the ultimate sophistication. – Leonardo da Vinci");
                break;
            case 9:
                inspo.setText("Whatever you do, do it well. – Walt Disney");
                break;
            case 10:
                inspo.setText("What we think, we become. – Buddha");
                break;
            case 11:
                inspo.setText("All limitations are self-imposed. – Oliver Wendell Holmes");
                break;
            case 12:
                inspo.setText("Tough times never last but tough people do. – Robert H. Schiuller");
                break;
            case 13:
                inspo.setText("Never let your emotions overpower your intelligence. – Drake");
                break;
            case 14:
                inspo.setText("Strive for greatness. – Lebron James");
                break;
            case 15:
                inspo.setText("Turn your wounds into wisdom. – Oprah Winfrey");
                break;
            case 16:
                inspo.setText("Happiness depends upon ourselves. – Aristotle");
                break;
            case 17:
                inspo.setText("Hate comes from intimidation, love comes from appreciation. – Tyga");
                break;
            case 18:
                inspo.setText("Oh, the things you can find, if you don’t stay behind. – Dr. Seuss");
                break;
            case 19:
                inspo.setText("Determine your priorities and focus on them. – Eileen McDargh");
                break;
        }

    }

    @Override
    public void onClick(@NonNull View v) {
        switch (v.getId()) {
            case R.id.post_button:
                Toast.makeText(HomepageActivity.this, "Time to Talk!",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomepageActivity.this,
                        ForumPage.class));
                break;
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

            case R.id.chatroom_button:
                startActivity(new Intent(HomepageActivity.this, ChatRoomActivity.class));
                break;
        }
    }
}