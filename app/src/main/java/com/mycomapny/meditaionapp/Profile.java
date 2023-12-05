package com.mycomapny.meditaionapp;

import static android.app.Activity.RESULT_OK;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


@SuppressWarnings("deprecation")
public class Profile extends Fragment {
    //create object of DatabaseReference class to access real time database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().
            getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    private CircleImageView mImageView;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    TextView displayName;


    public Profile() {
        // Required empty public constructor
    }

    FirebaseAuth auth;
    FirebaseUser user;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_layout, container, false);


        displayName = view.findViewById(R.id.user_name);
        TextView uploadImage = view.findViewById(R.id.uploadImage);
        mImageView = view.findViewById(R.id.profilePicture);
        ListView listView = view.findViewById(R.id.list1);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        mStorageRef = FirebaseStorage.getInstance().getReference().child("users").child(user.getUid());
        ;
        mDatabaseRef = FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid());
        ;

        //define the list
        List<String> list = new ArrayList<>();
        list.add("Set bed time reminder");
        list.add("Edit your profile");
        list.add("About");
        list.add("Sign out");

        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext().getApplicationContext(),
                R.layout.list_item_my, list);
        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    OpenSetReminder();
                } else if (position == 1) {
                    //go to the edit profile window
                    OpenEditProfile();
                } else if (position == 2) {
                    //go to the about window
                    OpenAbout();
                } else if (position == 3) {
                    //sign out the user
                    FirebaseAuth.getInstance().signOut();
                    Intent intent = new Intent(getContext().getApplicationContext(), Login.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });


        if (user == null) {
            Intent intent = new Intent(getContext().getApplicationContext(), Login.class);
            startActivity(intent);
            getActivity().finish();
        } else {
            databaseReference
                    .child("users") // Navigate to the "users" node
                    .child(user.getUid()) // Navigate to a specific user node using the UID
                    .addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            // Retrieve the value of "firstName" from the dataSnapshot
                            String firstName = dataSnapshot.child("firstName").getValue(String.class);
                            String lastName = dataSnapshot.child("lastname").getValue(String.class);

                            // Set the retrieved value to the TextField
                            displayName.setText(firstName + " " + lastName);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle any errors that occur during the data retrieval
                        }
                    });


        }


        //display the user profile
        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        StorageReference imageRef = storageRef.child("users/" + user.getUid() + "/" + user.getUid() + ".jpg");
        imageRef.getDownloadUrl().

                addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageUrl = uri.toString();
                        // Use Picasso to load the image into the ImageView
                        Picasso.get().load(imageUrl).into(mImageView);
                    }
                }).

                addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle any errors that occur while retrieving the download URL
                    }
                });


        //choosing a file
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });


        return view;

    }

    //open file chooser
    public void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            mImageUri = data.getData();

            uploadFile();
            Picasso.get().load(mImageUri).into(mImageView);
        }
    }

    //get extension of the file
    private String getFileExtension(Uri uri) {
        ContentResolver cR = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    //uploading the file to the data base
    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(user.getUid()
                    + "." + getFileExtension(mImageUri));

            System.out.println("MY path " + System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));

            fileReference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //upload success

                    Upload upload = new Upload(taskSnapshot.getMetadata().getReference().getDownloadUrl().toString());
                    String uploadId = mDatabaseRef.push().getKey();
                    //mDatabaseRef.child(uploadId).setValue(upload);
                    mDatabaseRef.child("profile_photo").setValue(upload);

                    System.out.println("upload" + upload);
                    System.out.println("uploadId" + uploadId);
                    System.out.println("OK");

                    mImageView = mImageView.findViewById(R.id.profilePicture);
                    displayName.setText("succsess");


                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    //upload fail
                    System.out.println("Fail");


                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    //upload progressing
                    System.out.println("progressing");

                }
            });
        }
    }

    //navigate to edit profile window
    public void OpenEditProfile() {
        Intent intent = new Intent(getActivity(), EditProfile.class);
        startActivity(intent);
    }

    //navigate to edit about window
    public void OpenAbout() {
        Intent intent = new Intent(getActivity(), About.class);
        startActivity(intent);
    }

    public void OpenSetReminder() {
        Intent intent = new Intent(getActivity(), BedtimeReminder.class);
        startActivity(intent);
    }
}