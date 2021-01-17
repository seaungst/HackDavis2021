package com.android.hackdavis2021;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HomepageActivity extends AppCompatActivity implements View.OnClickListener {

    private Button signoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        signoutButton = findViewById(R.id.sign_out_button);
        signoutButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_out_button:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
    }
}