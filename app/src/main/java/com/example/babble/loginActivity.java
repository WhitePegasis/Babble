package com.example.babble;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.babble.databinding.ActivityLoginBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class loginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    ProgressDialog progressDialogue;
    FirebaseDatabase database;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        database=FirebaseDatabase.getInstance();
        setContentView(binding.getRoot());
        progressDialogue = new ProgressDialog(loginActivity.this);
        progressDialogue.setTitle("Login");
        progressDialogue.setMessage("Logging in to your account, thanks for waiting!");
        auth=FirebaseAuth.getInstance();

        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(binding.email.getText().toString().isEmpty() || binding.password.getText().toString().isEmpty())
                {
                    Toast.makeText(loginActivity.this, "Please enter correct details!", Toast.LENGTH_SHORT).show();
                }
                else{
                    progressDialogue.show();
                    progressDialogue.setCancelable(false);
                    auth.signInWithEmailAndPassword(binding.email.getText().toString()
                            , binding.password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialogue.dismiss();
                            if (task.isSuccessful()) {
                                Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                Intent intent= new Intent(loginActivity.this,MainActivity.class);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(loginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });

        /*if(auth.getCurrentUser()!=null)
        {
            Toast.makeText(this, ""+auth.getCurrentUser(), Toast.LENGTH_SHORT).show();

                        Intent intent;
                        intent = new Intent(loginActivity.this, MainActivity.class);
                        startActivity(intent);

        }*/

        //if user wants to register send him/her to signup page
        binding.newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(loginActivity.this,signUpActivity.class);
                startActivity(intent);
            }
        });

    }
}