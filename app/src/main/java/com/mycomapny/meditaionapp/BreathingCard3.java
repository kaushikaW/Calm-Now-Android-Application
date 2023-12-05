package com.mycomapny.meditaionapp;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.RoundingMode;
import java.text.DecimalFormat;


public class BreathingCard3 extends Fragment {

    VideoView videoView;
    Button play_pause_button;
    TextView playBreathing;
    String time;

    RelativeLayout relativeLayout;

    TextView timeStamp, zenPoints;
    TextView totalRounds;

    ImageView imageView;

    int rounds = 0;

    double score = 0;
    double dbScore = 0;


    private Handler handler;
    private boolean isTimerRunning;
    private int seconds, minutes, hours, totalSeconds = 0, tempSeconds = 0;


    //create object of DatabaseReference class to access real time database
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://neural-sunup-378911-default-rtdb.firebaseio.com/");

    FirebaseAuth auth;
    FirebaseUser user;


    DecimalFormat df = new DecimalFormat("#.#");


    // initialize the state to playing


    boolean isPlaying = false;

    public BreathingCard3() {
    }

    // Initialize the loop counter
    int loopCount = 0;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.breathing_card3_layout, container, false);

        imageView = view.findViewById(R.id.p3);

        relativeLayout = view.findViewById(R.id.c1);

        //get the current user
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        videoView = view.findViewById(R.id.breathing_video_view3);
        int vidId = R.raw.five__;
        String videoPath = "android.resource://" + requireContext().getPackageName() + "/" + vidId;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        handler = new Handler();

        //method for loop (apa_ani) video
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
            }


        });


        //get button id
        play_pause_button = view.findViewById(R.id.play_pause_button3);
        timeStamp = view.findViewById(R.id.timestamp2);
        totalRounds = view.findViewById(R.id.roundsDigits);
        zenPoints = view.findViewById(R.id.pointsDigit);

        df.setRoundingMode(RoundingMode.HALF_UP);


        //controlling the playing and pausing
        play_pause_button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                MediaPlayer mp = null;
                imageView.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);

                if (isPlaying == false) {
                    //Not playing
                    videoView.start();
                    rounds = 0;
                    isPlaying = true;
                    play_pause_button.setText("Stop");
                    startTimer();
                    zenPoints.setText("0");
                    timeStamp.setText("00:00:00");


                } else {
                    //if playing
                    videoView.pause();
                    videoView.seekTo(0);
                    isPlaying = false;
                    play_pause_button.setText("Start");
                    stopTimer();
                    timeStamp.setText(time);


                    // Retrieve the current score from the database
                    DatabaseReference scoreRef = databaseReference.child("users").child(user.getUid()).child("score");
                    scoreRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                double dbScore = dataSnapshot.getValue(double.class);
                                double currentScore = score + dbScore;

                                // Update the current score in the database
                                databaseReference.child("users").child(user.getUid()).child("score").setValue(currentScore);

                                zenPoints.setText(String.valueOf(df.format(score)));
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                            // Handle database read error
                        }
                    });

                }

            }
        });


        return view;


    }

    private void startTimer() {
        isTimerRunning = true;

        handler.post(new Runnable() {
            @Override
            public void run() {
                seconds++;
                tempSeconds++;
                if (seconds == 60) {
                    seconds = 0;
                    minutes++;
                }
                if (minutes == 60) {
                    minutes = 0;
                    hours++;
                }
                time = String.format("%02d:%02d:%02d", hours, minutes, seconds);

                if (isTimerRunning) {
                    handler.postDelayed(this, 1000);
                    timeStamp.setText(time);

                    if(tempSeconds == 32){
                        rounds++;
                        tempSeconds = 0;
                    }
                    score = 0.1 * rounds;
                    score = Double.parseDouble(df.format(score));
                    totalRounds.setText(String.valueOf(rounds));
                    System.out.println(rounds);

                }


            }
        });
    }

    private void stopTimer() {
        isTimerRunning = false;

        seconds = 0;
        minutes = 0;
        hours = 0;
        String time = String.format("%02d:%02d:%02d", hours, minutes, seconds);
        timeStamp.setText(time);


        System.out.println(rounds);
    }
}