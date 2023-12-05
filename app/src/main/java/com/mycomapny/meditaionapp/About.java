package com.mycomapny.meditaionapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class About extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //navigate to the database
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");
        FirebaseAuth auth;
        FirebaseUser user;


        TextView userId = findViewById(R.id.userID);
        TextView EmailReg = findViewById(R.id.emailReg);

        //get current user accuses
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        //display user id
        userId.setText(user.getUid());

        //display register user email
        EmailReg.setText(user.getEmail());







    }
}