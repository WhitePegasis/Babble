package com.example.babble.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.babble.chatDetailActivity;

import com.example.babble.modals.UsersModal;
import com.example.babble.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class availableUsersAdapter extends RecyclerView.Adapter<availableUsersAdapter.viewholder>{

    ArrayList<UsersModal> arraylist;
    Context context;
    FirebaseDatabase database;
    FirebaseAuth auth;


    public availableUsersAdapter(ArrayList<UsersModal> array, Context context) {
        this.arraylist = array;
        this.context = context;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.sample_available_users_layout,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        UsersModal user= arraylist.get(position);
        String senderId=auth.getUid();

        String senderRoom= senderId+user.getUserid();
        //Picasso.get().load(user.getDp()).placeholder(R.drawable.bg1);
        holder.username.setText(user.getUsername());


        //Toast.makeText(context, "Profile Image Url: "+user.getDp(), Toast.LENGTH_SHORT).show();
        Glide.with(context).load(user.getDp()).centerCrop()
                .placeholder(R.drawable.avatar)
                .into(holder.img);

        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String lastMsg=snapshot.child("lastMsg").getValue(String.class);
                    long time = snapshot.child("lastMsgTime").getValue(Long.class);
                    Format dateFormat = new SimpleDateFormat("hh:mm a");
                    holder.lastMessage.setText(lastMsg);
                    holder.time.setText(dateFormat.format(new Date(time)));
                }
                else
                {
                    holder.lastMessage.setText("Tap to chat");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, chatDetailActivity.class);
                intent.putExtra("name",user.getUsername());
                intent.putExtra("profilePic",user.getDp());
                intent.putExtra("uid",user.getUserid());
                context.startActivity(intent);
            }
        });




        //chatDetailActivity

    }

    @Override
    public int getItemCount() {
        return arraylist.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView username,lastMessage,time;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.profileImage);
            username=itemView.findViewById(R.id.userNameTextview);
            lastMessage=itemView.findViewById(R.id.lastMessageTextview);
            time=itemView.findViewById(R.id.time);
        }
    }
}