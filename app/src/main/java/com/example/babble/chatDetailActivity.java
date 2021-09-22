package com.example.babble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.babble.adapters.chatDetailAdapter;
import com.example.babble.databinding.ActivityChatDetailBinding;
import com.example.babble.modals.chatDetailModal;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class chatDetailActivity extends AppCompatActivity {
     ActivityChatDetailBinding binding;
     FirebaseAuth auth;
     FirebaseDatabase database;
     chatDetailAdapter adapter;
    ArrayList<chatDetailModal> messageList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        binding=ActivityChatDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        adapter=new chatDetailAdapter(this,messageList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(chatDetailActivity.this);
        layoutManager.setStackFromEnd(true);
        binding.privateChatRecyclerview.setLayoutManager(layoutManager);
        binding.privateChatRecyclerview.setAdapter(adapter);





        String name = getIntent().getStringExtra("name");
        String profilePic = getIntent().getStringExtra("profilePic");
        String receiverId = getIntent().getStringExtra("uid");

        Glide.with(this).load(profilePic).centerCrop()
                .placeholder(R.drawable.avatar).into(binding.profileImageDp);
        String senderId=auth.getUid();

        binding.userName.setText(name);

        binding.arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(chatDetailActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        final String senderRoom = senderId + receiverId;
        final String receiverRoom = receiverId + senderId;

        database.getReference().child("chats").child(senderRoom).child("personalMsg").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if(snapshot.getValue()!="lastMsg" && snapshot.getValue()!="lastMsgTime"){
                        chatDetailModal model = snapshot1.getValue(chatDetailModal.class);
                        messageList.add(model);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //sending text

        binding.sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = binding.messagesendtext.getText().toString();

                Date date=new Date();
                final chatDetailModal messageModal = new chatDetailModal(message,senderId,new Date().getTime());
                binding.messagesendtext.setText("");

                String lastMsg=messageModal.getMessage();
                Long lastMsgTime=date.getTime();
                HashMap<String ,Object> lastMsgObj=new HashMap<>();

                lastMsgObj.put("lastMsg",lastMsg);
                lastMsgObj.put("lastMsgTime",lastMsgTime);
                database.getReference().child("chats").child(senderRoom).updateChildren(lastMsgObj);
                database.getReference().child("chats").child(receiverRoom).updateChildren(lastMsgObj);


                database.getReference().child("chats").child(senderRoom).child("personalMsg").push().setValue(messageModal)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                database.getReference().child("chats").child(receiverRoom).push().setValue(messageModal)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {

                                            }
                                        });

                            }
                        });
            }
        });
    }
}