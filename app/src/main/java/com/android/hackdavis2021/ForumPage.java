
package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
/*
import com.android.hackdavis2021.User.AllMethods;
import com.android.hackdavis2021.Adapters.MessageAdapter;
import com.android.hackdavis2021.User.Message;
 */
import com.android.hackdavis2021.User;
import com.android.hackdavis2021.UserData.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


public class ForumPage extends AppCompatActivity implements View.OnClickListener
{

    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference ref = database.getReference("server/saving-data/forumpage");
    private Button send;
    EditText mEdit;
    TextView mText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumpage);
        ArrayList input = new ArrayList<>();

        RecyclerView recyclerView = findViewById(R.id.messageRV);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
/*
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
        RecyclerViewadapter = new RecyclerView.Adapter(this, data);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);
    }

*/
        send = (Button) findViewById(R.id.sendBTN);
        send.setOnClickListener(this);
    }
        @Override
        public void onClick(View view)
        {
            mEdit = (EditText) findViewById(R.id.userInput);
            mText = (TextView)findViewById(R.id.messageRV);
            mEdit.getText().toString();

        }
    }



