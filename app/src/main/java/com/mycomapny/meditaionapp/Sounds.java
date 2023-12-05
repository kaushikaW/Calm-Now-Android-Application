package com.mycomapny.meditaionapp;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Slide;
import android.transition.TransitionManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;


public class Sounds extends Fragment {
    int current = 0;
    int state = 0;
    ImageView PlayPauseButton, imageCover, currentImageView;
    TextView nowPlayingLabel;
    RelativeLayout RelativeLayout;

    MediaPlayer currentMediaPlayer;


    public Sounds() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sounds_layout, container, false);

        // Get card ids
        CardView cardView1 = view.findViewById(R.id.cardView1);
        CardView cardView2 = view.findViewById(R.id.cardView2);
        CardView cardView3 = view.findViewById(R.id.cardView3);
        CardView cardView4 = view.findViewById(R.id.cardView4);

        CardView cardView5 = view.findViewById(R.id.cardView1m);
        CardView cardView6 = view.findViewById(R.id.cardView2m);
        CardView cardView7 = view.findViewById(R.id.cardView3m);
        CardView cardView8 = view.findViewById(R.id.cardView4m);

        CardView cardView9 = view.findViewById(R.id.cardView1b);
        CardView cardView10 = view.findViewById(R.id.cardView2b);
        CardView cardView11 = view.findViewById(R.id.cardView3b);
        CardView cardView12 = view.findViewById(R.id.cardView4b);

        // Create MediaPlayer objects
        MediaPlayer mediaPlayer1 = MediaPlayer.create(getContext(), R.raw.forest);
        MediaPlayer mediaPlayer2 = MediaPlayer.create(getContext(), R.raw.wind);
        MediaPlayer mediaPlayer3 = MediaPlayer.create(getContext(), R.raw.rain);
        MediaPlayer mediaPlayer4 = MediaPlayer.create(getContext(), R.raw.campfire);

        MediaPlayer mediaPlayer5 = MediaPlayer.create(getContext(), R.raw.med1);
        MediaPlayer mediaPlayer6 = MediaPlayer.create(getContext(), R.raw.med2);
        MediaPlayer mediaPlayer7 = MediaPlayer.create(getContext(), R.raw.med3);
        MediaPlayer mediaPlayer8 = MediaPlayer.create(getContext(), R.raw.med4);

        MediaPlayer mediaPlayer9 = MediaPlayer.create(getContext(), R.raw.b1);
        MediaPlayer mediaPlayer10 = MediaPlayer.create(getContext(), R.raw.b2);
        MediaPlayer mediaPlayer11 = MediaPlayer.create(getContext(), R.raw.b3);
        MediaPlayer mediaPlayer12 = MediaPlayer.create(getContext(), R.raw.b4);


        ImageView g1 = view.findViewById(R.id.g1);
        ImageView g2 = view.findViewById(R.id.g2);
        ImageView g3 = view.findViewById(R.id.g3);
        ImageView g4 = view.findViewById(R.id.g4);
        ImageView g5 = view.findViewById(R.id.g5);
        ImageView g6 = view.findViewById(R.id.g6);
        ImageView g7 = view.findViewById(R.id.g7);
        ImageView g8 = view.findViewById(R.id.g8);
        ImageView g9 = view.findViewById(R.id.g9);
        ImageView g10 = view.findViewById(R.id.g10);
        ImageView g11 = view.findViewById(R.id.g11);
        ImageView g12 = view.findViewById(R.id.g12);


        //get UI components references
        PlayPauseButton = view.findViewById(R.id.playPause);
        nowPlayingLabel = view.findViewById(R.id.nowPlaying);
        imageCover = view.findViewById(R.id.cover);
        RelativeLayout = view.findViewById(R.id.miniPlayer);


        cardView1.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 1) {
                    pauseCurrentPlayer();
                }

                playPauseCard(mediaPlayer1, 1, g1);
                playPauseMini();
                ChangeLabel("Forest");
                imageCover.setImageResource(R.drawable.forestcard);
            }
        });

        cardView2.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 2) {
                    pauseCurrentPlayer();
                }

                playPauseCard(mediaPlayer2, 2, g2);
                playPauseMini();
                ChangeLabel("Wind");
                imageCover.setImageResource(R.drawable.wind);


            }
        });


        cardView3.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 3) {
                    pauseCurrentPlayer();
                }

                playPauseCard(mediaPlayer3, 3, g3);
                playPauseMini();
                ChangeLabel("Rain");
                imageCover.setImageResource(R.drawable.forestcard);
                imageCover.setImageResource(R.drawable.rain);



            }

        });

        cardView4.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);


                if (current != 4) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer4, 4, g4);
                playPauseMini();
                ChangeLabel("Campfire");
                imageCover.setImageResource(R.drawable.fire);


            }

        });

        cardView5.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 5) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer5, 5, g5);
                playPauseMini();
                ChangeLabel("Deep Relax");
                imageCover.setImageResource(R.drawable.med1);



            }

        });

        cardView6.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 6) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer6, 6, g6);
                playPauseMini();
                ChangeLabel("De stress");
                imageCover.setImageResource(R.drawable.med2);


            }

        });

        cardView7.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 7) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer7, 7, g7);
                playPauseMini();
                ChangeLabel("Peace");
                imageCover.setImageResource(R.drawable.med3);


            }

        });

        cardView8.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 8) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer8, 8, g8);
                playPauseMini();
                ChangeLabel("Calm");
                imageCover.setImageResource(R.drawable.med4);


            }

        });

        cardView9.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 9) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer9, 9, g9);
                playPauseMini();
                ChangeLabel("Dream");
                imageCover.setImageResource(R.drawable.bin1);


            }

        });

        cardView10.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 10) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer10, 10, g10);
                playPauseMini();
                ChangeLabel("Relax");
                imageCover.setImageResource(R.drawable.bin2);


            }
        });

        cardView11.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 11) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer11, 11, g11);
                playPauseMini();
                ChangeLabel("Study");
                imageCover.setImageResource(R.drawable.bin3);


            }
        });

        cardView12.setOnClickListener(new View.OnClickListener() {
            private MediaPlayer currentMediaPlayer = null;

            @Override
            public void onClick(View v) {

                // Apply slide-up transition
                TransitionManager.beginDelayedTransition((ViewGroup) RelativeLayout, new Slide(Gravity.BOTTOM));
                RelativeLayout.setVisibility(View.VISIBLE);

                if (current != 12) {
                    pauseCurrentPlayer();
                }
                playPauseCard(mediaPlayer12, 12, g12);
                playPauseMini();
                ChangeLabel("Pre Sleep");
                imageCover.setImageResource(R.drawable.bin4);


            }
        });


        PlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current == 1) {
                    playPauseCard(mediaPlayer1, 1, g1);
                    playPauseMini();
                } else if (current == 2) {
                    playPauseCard(mediaPlayer2, 2, g2);
                    playPauseMini();
                } else if (current == 3) {
                    playPauseCard(mediaPlayer3, 3, g3);
                    playPauseMini();
                } else if (current == 4) {
                    playPauseCard(mediaPlayer4, 4, g4);
                    playPauseMini();
                }else if (current == 5) {
                    playPauseCard(mediaPlayer5, 5, g5);
                    playPauseMini();
                }else if (current == 6) {
                    playPauseCard(mediaPlayer6, 6, g6);
                    playPauseMini();
                }else if (current == 7) {
                    playPauseCard(mediaPlayer7, 7, g7);
                    playPauseMini();
                }else if (current == 8) {
                    playPauseCard(mediaPlayer8, 8, g8);
                    playPauseMini();
                }else if (current == 9) {
                    playPauseCard(mediaPlayer9, 9, g9);
                    playPauseMini();
                }else if (current == 10) {
                    playPauseCard(mediaPlayer10, 10, g10);
                    playPauseMini();
                }else if (current == 11) {
                    playPauseCard(mediaPlayer11, 11, g11);
                    playPauseMini();
                }else if (current == 12) {
                    playPauseCard(mediaPlayer12, 12, g12);
                    playPauseMini();
                }
            }
        });


        return view;

    }

    public void playPauseCard(MediaPlayer mediaPlayer, int index, ImageView img) {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            state = 0;
            transitionToInvisible(img);

        } else {

            mediaPlayer.start();
            current = index;
            currentMediaPlayer = mediaPlayer;
            currentImageView = img;
            state = 1;
            transitionToVisible(img);


        }
    }

    // mini player button change
    public void playPauseMini() {


        if (state == 0) {
            // Switch to image2
            PlayPauseButton.setImageResource(R.drawable.baseline_play_circle_outline_24);
            state = 1;

        } else {
            // Switch to image1
            PlayPauseButton.setImageResource(R.drawable.baseline_pause_circle_outline_24);
            state = 0;
        }

    }

    public void ChangeLabel(String trackName) {
        // Fade out animation
        nowPlayingLabel.animate()
                .alpha(0)
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        // Set the new track name
                        nowPlayingLabel.setText(trackName);

                        // Fade in animation
                        nowPlayingLabel.animate()
                                .alpha(1)
                                .setDuration(200)
                                .setListener(null)
                                .start();
                    }
                })
                .start();
    }


    private void transitionToVisible(ImageView img) {
        TransitionManager.beginDelayedTransition((ViewGroup) img.getParent());
        img.setVisibility(View.VISIBLE);
    }

    private void transitionToInvisible(ImageView img) {
        TransitionManager.beginDelayedTransition((ViewGroup) img.getParent());
        img.setVisibility(View.INVISIBLE);
    }

    public void pauseCurrentPlayer() {
        if (currentMediaPlayer != null) {
            currentMediaPlayer.pause();
        }

        if (currentImageView != null) {
            transitionToInvisible(currentImageView);

        }

    }


}