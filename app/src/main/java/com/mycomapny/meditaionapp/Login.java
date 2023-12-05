package com.mycomapny.meditaionapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    TextInputLayout emailLayout, passwordLayout;
    AppCompatEditText GetEmail, GetPassword;
    Button buttonLogin;
    FirebaseAuth mAuth;
    TextView clickToLogin, forgetPassword;

    @Override
    public void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //define components
        mAuth = FirebaseAuth.getInstance();

        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        GetEmail = findViewById(R.id.getEmail);
        GetPassword = findViewById(R.id.getPassword);
        forgetPassword = findViewById(R.id.forgetPass);

        buttonLogin = findViewById(R.id.login_button);
        clickToLogin = findViewById(R.id.click_to_register);


        //navigate to the user registration page
        clickToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Register.class);
                startActivity(intent);
                finish();
            }
        });

        //navigate to the forget password page
        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Recover.class);
                startActivity(intent);
                finish();
            }
        });

        /*user login
        if user gives correct credentials redirected to main Welcome page
        */
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email, password;
                email = String.valueOf(GetEmail.getText());
                password = String.valueOf(GetPassword.getText());

                //validate input fields
                if (!checkEmail() | !checkPass()) {
                    return;
                }
                ;

                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Login.this, "Login Successful",
                                            Toast.LENGTH_SHORT).show();

                                    //if login successful go to main activity

                                    emailLayout.setError(null);
                                    passwordLayout.setError(null);

                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {

                                    //if login unsuccessful showing messages
                                    emailLayout.setError("Email doesn't exist");
                                    passwordLayout.setError("Wrong password");

                                    Toast.makeText(Login.this, "You have entered wrong username or password",
                                            Toast.LENGTH_LONG).show();

                                }
                            }
                        });

            }
        });


    }

    //check if email filed is not empty
    private boolean checkEmail() {

        String getEmail = GetEmail.getText().toString().trim();
        if (getEmail.isEmpty()) {
            emailLayout.setError("Email field can't be empty");
            return false;
        } else {
            emailLayout.setError(null);
            return true;
        }

    }

    //check if password filed is not empty
    private boolean checkPass() {

        String getPass = GetPassword.getText().toString().trim();
        if (getPass.isEmpty()) {
            passwordLayout.setError("Password field can't be empty");
            return false;
        } else {
            passwordLayout.setError(null);
            return true;
        }

    }

}