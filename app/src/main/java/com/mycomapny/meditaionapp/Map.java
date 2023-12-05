package com.mycomapny.meditaionapp;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.VideoView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


public class Map extends Fragment {

    VideoView videoView;
    Button button_map;


    public Map() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.map_layout, container, false);


        videoView = view.findViewById(R.id.videoViwe);
        int vidId = R.raw.map_welcome2;
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


        button_map = view.findViewById(R.id.map_button);

        button_map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMapsActivity();
            }
        });


        return view;

    }

    public void openMapsActivity() {
        Intent intent = new Intent(getActivity(), MapsActivity.class);
        startActivity(intent);


    }

    // A helper method to replace the current fragment with a new one

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.map_layout, fragment);
        fragmentTransaction.commit();
    }



}