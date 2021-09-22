package com.example.babble.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.babble.R;
import com.example.babble.databinding.SampleChatReceiverBinding;
import com.example.babble.databinding.SampleChatSenderBinding;
import com.example.babble.modals.chatDetailModal;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chatDetailAdapter extends RecyclerView.Adapter{
    Context context;
    ArrayList<chatDetailModal> messagemodel;

    int SENDER_VIEW_TYPE=1;
    int RECEIVER_VIEW_TYPE=2;

    public chatDetailAdapter(Context context, ArrayList<chatDetailModal> messagemodel) {
        this.context = context;
        this.messagemodel = messagemodel;
    }

    @Override
    public int getItemViewType(int position) {
        if(messagemodel.get(position).getSenderUid().equals(FirebaseAuth.getInstance().getUid()))
        {
            return SENDER_VIEW_TYPE;
        }
        else
        {
            return RECEIVER_VIEW_TYPE;
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType==SENDER_VIEW_TYPE)
        {
            View view=LayoutInflater.from(context).inflate(R.layout.sample_chat_sender,parent,false);
            return new SenderViewholder(view);
        }
        else{
            View view=LayoutInflater.from(context).inflate(R.layout.sample_chat_receiver,parent,false);

            return new ReceiverViewholder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        chatDetailModal messageList=messagemodel.get(position);
        if(holder.getClass()==SenderViewholder.class)
        {
            SenderViewholder viewHolder=(SenderViewholder)holder;
            viewHolder.binding.textView.setText(messageList.getMessage());
        }
        else
        {
            ReceiverViewholder viewholder2=(ReceiverViewholder)holder;
            ((ReceiverViewholder)holder).binding.textView.setText(messageList.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return messagemodel.size();
    }


    public class ReceiverViewholder extends RecyclerView.ViewHolder {
        SampleChatReceiverBinding binding;

        public ReceiverViewholder(@NonNull View itemView) {
            super(itemView);
            binding=SampleChatReceiverBinding.bind(itemView);
        }
    }
    public class SenderViewholder extends RecyclerView.ViewHolder{

        SampleChatSenderBinding binding;

        public SenderViewholder(@NonNull View itemView) {
            super(itemView);
            binding=SampleChatSenderBinding.bind(itemView);
        }
    }
}
