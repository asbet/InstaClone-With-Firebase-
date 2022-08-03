package com.betulas.instagramclonejava.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.betulas.instagramclonejava.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private FirebaseAuth auth;
    String email;
    String pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        auth =FirebaseAuth.getInstance();
        onStart();


    }
    //Login
    public void signIn_clc(View view){
        String email=binding.mailText.getText().toString();
        String pass=binding.passwordText.getText().toString();
        if(email.equals("")||pass.equals("")){
            Toast.makeText(this,"Enter email and password ",Toast.LENGTH_LONG).show();
        }else {
            auth.signInWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent=new Intent(MainActivity.this, FeedActivity.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }

    }
    //Register
    public void signUp_clc(View view){
        String email=binding.mailText.getText().toString();
        String pass=binding.passwordText.getText().toString();
        if (email.equals("")||pass.equals("")){
            Toast.makeText(this,"Enter email and password",Toast.LENGTH_LONG).show();
        }else{
            auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    Intent intent=new Intent(MainActivity.this,FeedActivity.class);
                    startActivity(intent);
                    // Kullanıcı bir kere giriş yaptıktan sonra tekrar buraya girmemisi için finish() methodunu kullandık
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this,e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = auth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(MainActivity.this,FeedActivity.class);
            startActivity(intent);
            finish();
        }
    }
}