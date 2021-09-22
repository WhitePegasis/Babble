package com.example.babble.adapters;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.babble.R;
import com.example.babble.databinding.FragmentAvailableUsersBinding;
import com.example.babble.modals.UsersModal;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class availableUsersFragment extends Fragment {
    FragmentAvailableUsersBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    ArrayList<UsersModal> usersList;
    availableUsersAdapter usersAdapter;
    RecyclerView recyclerView;
    //ProgressDialog dialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_available_users, container, false);
        recyclerView=view.findViewById(R.id.recyclerView1);
        binding=FragmentAvailableUsersBinding.inflate(getLayoutInflater());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        usersList =new ArrayList<>();
        usersAdapter=new availableUsersAdapter(usersList,getContext());
        recyclerView.setAdapter(usersAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    UsersModal user = datasnapshot.getValue(UsersModal.class);
                    //if(!auth.getCurrentUser().getUid().equals(user.getUserid())){
                        usersList.add(user);
                   // }
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading...", Toast.LENGTH_SHORT).show();
            }
        });
        //binding.recyclerView1.setAdapter(usersAdapter);
        return view;
    }
}