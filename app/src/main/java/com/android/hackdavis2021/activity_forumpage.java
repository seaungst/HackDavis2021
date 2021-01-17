
package com.android.hackdavis2021;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.os.Bundle;
import android.widget.Toast;

import com.android.hackdavis2021.User.AllMethods
import com.android.hackdavis2021.Adapters.MessageAdapter;
import com.android.hackdavis2021.User.Message;
import com.android.hackdavis2021.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class activity_forumpage extends AppCompatActivity implements View.OnClickListener
{

    FirebaseAuth auth;
    FirebaseDatabase db;
    DatabaseReference messageDB;
    MessageAdapter messageAdapter;
    String u;
    List<Message> message;
    List<Message> messages;
    DataSnapshot dataSnapshot;

    RecyclerView messageRV;
    EditText messageET;
    ImageButton imageButton;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forumpage);

        auth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        MyUser u = new MyUser();
        messageRV= (RecyclerView) findViewByID(R.id.messageRV);
        messageET= (EditText) findViewByID(R.id.messageET);
        imageButton= (ImageButton)findViewById(R.id.sendBTN);
        imageButton.setOnClickListener(this);
        ArrayList messages= new ArrayList<>();
    }

    @Override
    public void onClick(View view)
    {
        if(!TextUtils.isEmpty(messageET.getText().toString()))
        {
            Message message= new Message(messageET.getText().toString(),u.getName());
            messageET.setText("");
            messageDB.push().setValue(message);
        }
        else
        {
            Toast.makeText(getApplicationContext, "you can't send a blank message!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        final FirebaseUser currentUser = auth.getCurrentUser();
        u.setUid(currentUser.getUid());
        u.setEmail(currentUser.getUid());

        database.getReference("Users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener())
        {
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                u = dataSnapshot.getvalue(MyUser.class);
                u.setUid(currentUser.getUid());
                AllMethods.name = u.getName()
            }
            @Override
            public void onCancelled(DatabaseError )
                {
                }
        }
    }

    messageDB= database.getReference("messages");
    messageDB.addChildEventListener(new ChildEventListener)()
    {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            Message message = dataSnapshot.getValue(Message.class);
            message.setKey(dataSnapshot.getKey());
            messages.add(messageAdapter);
            displayMessages(messages);
        }
        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s)
        {
            Message message = dataSnapshot.getValue(Message.class);
            message.setKey(dataSnapshot.getKey());

            List<Message> newMessages = new ArrayList<Message>();

            for(Message m: messages)
            {
                if(m.getKey().equals(message.getKey()))
                {
                    newMessages.add(message);
                }
                else
                {
                    newMessages.add(m);
                }
            }
        }
        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot)
        {
            Message message = dataSnapshot.getValue(Message.class);
            message.setKey(dataSnapshot.getKey());

            List<Message> newMessages = new ArrayList<Message>();

            for(Message m: messages)
            {
                if (m.getKey().equals(message.getKey()))
                {
                    newMessages.add(message);
                }
            }

            messages = newMessages;
            displayMessages(newMessages);
        }
        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s){}
        @Override
        public void onCancelled(@NonNull DatabaseError){}

    }

    @Override
    protected void onResume()
    {
        super.onResume();
        ArrayList messages = new ArrayList<>();
    }

    private void displayMessages(List<Message> messages))
    {
        messageRV.setLayoutManager(new LinearLayoutManager(activity_forumpage.this));
        messageAdapter= new MessageAdapter(activity_forumpage.this, messages, messageDB);
        messageRV.setAdapter(messageAdapter);


    }

}
