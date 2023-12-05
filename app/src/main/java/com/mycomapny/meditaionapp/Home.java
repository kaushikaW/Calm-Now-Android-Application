package com.mycomapny.meditaionapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.Calendar;


public class Home extends Fragment implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor sensor;
    public float changeValues;
    TextView bri_value_label, textView9, state_label, welcomeNote, zenPoints;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");
    CardView breathing_button;
    RelativeLayout sensor_dash_board;
    VideoView videoView;
    boolean isRunning = false;
    FirebaseAuth auth;
    FirebaseUser user;


    public Home() {
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.home_layout, container, false);


        //get UI components references
        breathing_button = view.findViewById(R.id.breathing_button);
        sensor_dash_board = view.findViewById(R.id.sensor_dash_board);
        state_label = view.findViewById(R.id.state_label2);
        bri_value_label = view.findViewById(R.id.bri_val);
        textView9 = view.findViewById(R.id.textView93);
        welcomeNote = view.findViewById(R.id.welcomeNote);
        zenPoints = view.findViewById(R.id.zenPoints);

        //get the current user from database
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();


        DecimalFormat df = new DecimalFormat("#.#");

        //create calendar instance
        Calendar calendar = Calendar.getInstance();
        //get current system hour(Time)
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        //change wallpaper according to the mood of day
        int vidId;

        if (currentHour >= 0 && currentHour <= 11) {
            vidId = R.raw.mm;
        } else {
            vidId = R.raw.mm2;
            welcomeNote.setTextColor(Color.WHITE);
        }
        videoView = view.findViewById(R.id.welcomeAnimation);

        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + vidId;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);
        videoView.start();

        //method for loop (apa_ani) video
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }
        });


        //change welcome banner according to the time of the day

        // Retrieve the username from the database
        DatabaseReference username = databaseReference.child("users").child(user.getUid()).child("firstName");
        username.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String firstName = dataSnapshot.getValue(String.class);
                    if (currentHour >= 0 && currentHour <= 11) {
                        welcomeNote.setText("Good Morning\n" + firstName);
                    } else {
                        welcomeNote.setText("Good Evening\n" + firstName);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database read error
            }
        });


        //method for navigate to learn more page
        textView9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                replaceFragment(new LearnMore());
            }
        });


        //  navigate to breathing exercises window
        breathing_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBreathingActivity();
            }
        });


        // Obtain an instance of the SensorManager
        sensorManager = (SensorManager) requireActivity().getSystemService(Context.SENSOR_SERVICE);


        // Retrieve the current score(zen points) from the database
        DatabaseReference score = databaseReference.child("users").child(user.getUid()).child("score");
        score.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    double dbScore = dataSnapshot.getValue(double.class);
                    zenPoints.setText(String.valueOf(df.format(dbScore)));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle database read error
            }
        });

        return view;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {

        //create calendar instance
        Calendar calendar = Calendar.getInstance();
        //get current system hour(Time)
        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);

        changeValues = event.values[0];

        bri_value_label.setText(String.valueOf(changeValues)+" lux");

        //change the sensor dash boards colors  according to the sensor data and time of the day
        if (changeValues > 50 && (currentHour >= 21 || currentHour < 4)) {
            sensor_dash_board.setBackgroundResource(R.drawable.bad_lighting);
            state_label.setText("Bad");
        } else if (changeValues < 50 && (currentHour >= 21 || currentHour < 4)) {
            sensor_dash_board.setBackgroundResource(R.drawable.home_card);
            state_label.setText("Good");
        } else {
            state_label.setText("Normal");
        }


        System.out.println("changeValues" + changeValues);
        System.out.println("current" + currentHour);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }


    @Override
    public void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT), SensorManager.SENSOR_DELAY_NORMAL);


    }


    @Override
    public void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);


    }

    public void openBreathingActivity() {
        Intent intent = new Intent(getActivity(), Breathing.class);
        startActivity(intent);


    }


    public void change_sensor_warning_message_bad() {
        sensor_dash_board.setBackgroundResource(R.drawable.bad_lighting);
    }

    // A helper method to replace the current fragment with a new one
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.replace(R.id.home_layout, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}