package com.mycomapny.meditaionapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity {

    //password pattern
    private static final Pattern PASSWORD_PATTERN = Pattern.compile("^" +
            "(?=.*[0-9])" +         //at least 1 digit
            "(?=.*[a-zA-Z])" +      //any letter
            "(?=\\S+$)" +           //no white spaces
            ".{4,}" +               //at least 4 characters
            "$");


    //create object of DatabaseReference class to access real time database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");

    AppCompatEditText editTextEmail, editTextPassword, Lastname, FirstName, rePassword;
    TextInputLayout editTextEmailLayout, editTextPasswordLayout, LastnameLayout, FirstNameLayout, rePasswordLayout;
    Button buttonReg;
    FirebaseAuth mAuth;
    TextView clickToLogin;
    FirebaseUser user;

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
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        //reference the components
        editTextEmailLayout = findViewById(R.id.emailNew);
        editTextPasswordLayout = findViewById(R.id.passwordNew);
        LastnameLayout = findViewById(R.id.lname);
        FirstNameLayout = findViewById(R.id.fname);
        rePasswordLayout = findViewById(R.id.rePassword);

        editTextEmail = findViewById(R.id.emailNew_edit);
        editTextPassword = findViewById(R.id.passwordNew_edit);
        Lastname = findViewById(R.id.lname_edit);
        FirstName = findViewById(R.id.fname_edit);
        rePassword = findViewById(R.id.rePassword_edit);

        buttonReg = findViewById(R.id.register_button);
        clickToLogin = findViewById(R.id.click_to_login);


        //go to the user registration page if there is no account
        clickToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
                finish();
            }
        });

        //main user registration implementation
        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get text from input fields as strings
                final String EMAIL = editTextEmail.getText().toString();
                final String FIRST_NAME = FirstName.getText().toString();
                final String LAST_NAME = Lastname.getText().toString();

                String email, password;
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());


                //checking fields validations
                if (!validateEmail() | !validateFirstName() | !validatePassword() |
                        !matchPassword() | !passwordStrongCheck()) {
                    return;
                }else {

                    //Creating a new user
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                user = mAuth.getCurrentUser();

                                // If sign in success, send data to the real time db
                                databaseReference.child("users").child(user.getUid()).child("firstName").setValue(FIRST_NAME);
                                databaseReference.child("users").child(user.getUid()).child("lastname").setValue(LAST_NAME);
                                databaseReference.child("users").child(user.getUid()).child("email").setValue(EMAIL);
                                databaseReference.child("users").child(user.getUid()).child("score").setValue(0);


                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(Register.this, "Account is created Successfully", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();

                            } else {
                                // If user name has been already used warn the user

                                if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                    Toast.makeText(Register.this, "Email already exists", Toast.LENGTH_SHORT).show();
                                }

                                // If sign in fails, display a message to the user.
                                Toast.makeText(Register.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                };





            }
        });
    }

    //check if email field is empty or not
    private boolean validateEmail() {
        final String EMAIL = editTextEmail.getText().toString().trim();

        if (EMAIL.isEmpty()) {
            editTextEmailLayout.setError("Email field can't be empty");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(EMAIL).matches()) {
            editTextEmailLayout.setError("Please Enter a valid Email address");
            return false;
        } else {
            editTextEmailLayout.setError(null);
            return true;
        }
    }

    //check if first name  field is empty or not
    private boolean validateFirstName() {
        final String FIRST_NAME = FirstName.getText().toString().trim();

        if (FIRST_NAME.isEmpty()) {
            FirstNameLayout.setError("Enter your first name");
            return false;
        } else {
            FirstNameLayout.setError(null);
            return true;
        }
    }

    //check if password  field is empty or not
    private boolean validatePassword() {
        final String GetEditTextPassword = editTextPassword.getText().toString().trim();
        final String GetReEditTextPassword = rePassword.getText().toString().trim();

        if (GetEditTextPassword.isEmpty() || GetReEditTextPassword.isEmpty()) {
            editTextPasswordLayout.setError("Password fields can't be empty");
            rePasswordLayout.setError("!");

            return false;
        } else {
            editTextPasswordLayout.setError(null);
            rePasswordLayout.setError(null);

            return true;
        }
    }

    //check if password fields are matching
    private boolean matchPassword() {
        final String GetEditTextPassword = editTextPassword.getText().toString().trim();
        final String GetReEditTextPassword = rePassword.getText().toString().trim();

        if (!(GetEditTextPassword.isEmpty()) && !(GetReEditTextPassword.isEmpty())) {
            if (!(GetEditTextPassword.equals(GetReEditTextPassword))) {
                editTextPasswordLayout.setError("Password doesn't match");
                rePasswordLayout.setError("!");
                return false;
            } else {
                rePasswordLayout.setError(null);
                editTextPasswordLayout.setError(null);
                return true;
            }
        }

        return true;


    }

    //check if the password  or weak
    private boolean passwordStrongCheck() {

        if (matchPassword() && validatePassword()) {
            final String GetEditTextPassword = editTextPassword.getText().toString().trim();
            if (!PASSWORD_PATTERN.matcher(GetEditTextPassword).matches()) {
                editTextPasswordLayout.setError("Password must contain at least one letter and digit");
                rePasswordLayout.setError("!");
            } else if (GetEditTextPassword.length() < 8) {
                editTextPasswordLayout.setError("Password must contain at least " + "\n8 characters");
                rePasswordLayout.setError("!");
            } else {
                rePasswordLayout.setError(null);
                editTextPasswordLayout.setError(null);
                return true;
            }
        }

        return false;
    }

    //check password length if has enough length return true

    private boolean passwordLengthCheck() {
        final String GetEditTextPassword = editTextPassword.getText().toString().trim();
        final String GetReEditTextPassword = rePassword.getText().toString().trim();

        if (passwordStrongCheck()) {
            if (GetEditTextPassword.length() < 8) {
                editTextPasswordLayout.setError("Password must contain at least " + "\n8 characters");
                rePasswordLayout.setError("!");
            } else {
                editTextPasswordLayout.setError(null);
                rePasswordLayout.setError(null);
                return true;
            }
        }

        return false;
    }
}