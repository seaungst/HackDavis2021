package com.android.hackdavis2021.Adapters;

import android.os.Message;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.core.Context;
import com.android.hackdavis2021.User;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder>
{
    Context context;
    List<Message> messages;
    DatabaseReference messageDB;

    public MessageAdapter(Context context, List<Message> messages, DatabaseReference messageDB)
    {
        this.context= context;
        this.messageDB= messageDB;
        this.messages= messages;
    }




    @Override
    public MessageAdapter.MessageAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MessageAdapterViewHolder holder, int position)
    {

    }

}
