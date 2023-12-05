package com.mycomapny.meditaionapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class EditProfile extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");
    Button clearProfilePicBtn, editLastName, editFirstName;
    EditText enterFname, enterLname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);


        FirebaseAuth auth;
        FirebaseUser user;

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        clearProfilePicBtn = findViewById(R.id.clearProfile);
        editFirstName = findViewById(R.id.changeFname);
        editLastName = findViewById(R.id.changeLname);
        enterFname = findViewById(R.id.editFname);
        enterLname = findViewById(R.id.editLname);


        //delete user profile picture
        clearProfilePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StorageReference storageRef = FirebaseStorage.getInstance().getReference();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                StorageReference imageRef = storageRef.child("users/" + user.getUid() + "/" + user.getUid() + ".jpg");
                DatabaseReference pathRef = databaseReference.child("users/" + user.getUid() + "/" + "profile_photo");


                imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Image deleted successfully
                        Toast.makeText(EditProfile.this, "Profile Image deleted successfully", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // An error occurred while deleting the image
                        Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                pathRef.removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        // Image deleted successfully

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // An error occurred while deleting the image
                        Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });


            }
        });

        //edit the user first name
        editFirstName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get last name from user input
                String getFisrtName = enterFname.getText().toString();

                //navigate to the database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference textRef = databaseReference.child("users/" + user.getUid() + "/" + "firstName");

                if (!getFisrtName.isEmpty()) {
                    textRef.setValue(getFisrtName).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditProfile.this, "First name updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(EditProfile.this, "Enter your first name", Toast.LENGTH_SHORT).show();
                }


            }
        });

        //edit the user last name
        editLastName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get last name from user input
                String getLastName = enterLname.getText().toString();

                //navigate to the database
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference textRef = databaseReference.child("users/" + user.getUid() + "/" + "lastname");


                if (!getLastName.isEmpty()) {
                    textRef.setValue(getLastName).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EditProfile.this, "Last name updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EditProfile.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });

                } else {
                    Toast.makeText(EditProfile.this, "Enter your last name", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


}