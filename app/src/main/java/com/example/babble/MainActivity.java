package com.example.babble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.babble.Fragments.callFragment;
import com.example.babble.Fragments.gamesFragment;
import com.example.babble.Fragments.videoCallFragment;
import com.example.babble.adapters.availableUsersAdapter;
import com.example.babble.adapters.availableUsersFragment;
import com.example.babble.databinding.ActivityMainBinding;
import com.example.babble.databinding.FragmentAvailableUsersBinding;
import com.example.babble.modals.UsersModal;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    //ArrayList<UsersModal> usersList;
    //availableUsersAdapter usersAdapter;
    ProgressDialog dialog;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();
        //usersList =new ArrayList<>();
        dialog=new ProgressDialog(this);
        dialog.setTitle("Logging out");
        dialog.setMessage("Please wait!");

        bottomNavigationView=binding.bottomNavigationView;
        getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,new availableUsersFragment()).commit();
       bottomNavigationView.setSelectedItemId(R.id.availableUsers);
       bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
           @Override
           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
               Fragment fragment=null;

               switch (item.getItemId())
               {
                   case R.id.availableUsers:
                       fragment=new availableUsersFragment();
                       break;
                   case R.id.calls:
                       fragment=new callFragment();
                       break;
                   case R.id.videoCall:
                       fragment=new videoCallFragment();
                       break;
                   case R.id.games:
                       fragment=new gamesFragment();
                       break;
               }
               getSupportFragmentManager().beginTransaction().replace(R.id.mainContainer,fragment).commit();
               return true;
           }
       });

        /*usersAdapter=new availableUsersAdapter(usersList,this);
        binding.recyclerView1.setAdapter(usersAdapter);
        binding.recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                usersList.clear();
                for (DataSnapshot datasnapshot : snapshot.getChildren()) {
                    UsersModal user = datasnapshot.getValue(UsersModal.class);
                    if(auth.getCurrentUser().getUid()!=user.getUserid()){
                        usersList.add(user);
                    }
                    }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Error loading...", Toast.LENGTH_SHORT).show();
            }
        });*/


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ((item.getItemId())){
            case R.id.search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                break;
            case R.id.invite:
                Toast.makeText(this, "Invite", Toast.LENGTH_SHORT).show();
                break;
            case R.id.logout:
                dialog.show();
                dialog.setCancelable(false);
                Intent intent=new Intent(MainActivity.this,loginActivity.class);
                auth.signOut();

                dialog.dismiss();
                startActivity(intent);
                break;
            case R.id.settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}