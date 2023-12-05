package com.mycomapny.meditaionapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {
    ImageView imageView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this,Login.class);
                startActivity(intent);
                finish();
            }
        },3000);

        ImageView splashLogo = findViewById(R.id.splashLogo);
        TextView textView = findViewById(R.id.text);


        Animation fadeInAnimation = new AlphaAnimation(0, 1);
        fadeInAnimation.setDuration(2000);

        fadeInAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                // Animation start callback
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // Animation end callback
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // Animation repeat callback
            }
        });

        splashLogo.startAnimation(fadeInAnimation);
        textView.startAnimation(fadeInAnimation);
    }
}