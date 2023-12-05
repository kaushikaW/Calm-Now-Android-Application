package com.mycomapny.meditaionapp;

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


public class LearnMore extends Fragment {

    VideoView videoView;
    Button button;

    public LearnMore() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.learn_more_layout, container, false);



        videoView = view.findViewById(R.id.learn_more);
        int vidId = R.raw.sleep;
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

        button = view.findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("abc");
                navigateBack();
            }
        });






        return view;
    }

    // A helper method to replace the current fragment with a new one
    private void navigateBack() {
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        fragmentManager.popBackStackImmediate();

    }

}