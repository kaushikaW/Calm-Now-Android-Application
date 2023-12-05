package com.mycomapny.meditaionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Recover extends AppCompatActivity {

    ImageView backButton;
    TextInputLayout RecoverEmailLayout;
    AppCompatEditText RecoverEmail;
    Button Reset;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover);


        backButton = findViewById(R.id.back);
        RecoverEmailLayout = findViewById(R.id.recoverEmailLayout);
        RecoverEmail = findViewById(R.id.recoverGetEmail);
        Reset = findViewById(R.id.Reset);

        String GetEmail = RecoverEmail.getText().toString();


        //sending password reset email
        Reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (GetEmail.isEmpty()) {
                    RecoverEmailLayout.setError("Email field can't be empty");

                } else {

                    RecoverEmailLayout.setError(null);
                    auth.sendPasswordResetEmail(GetEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Recover.this, "Check your email", Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(Recover.this, "Type correct email", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}