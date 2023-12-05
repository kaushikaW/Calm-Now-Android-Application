package com.mycomapny.meditaionapp;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.mycomapny.meditaionapp.databinding.BreathingLayoutBinding;

public class Breathing extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private BreathingLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = BreathingLayoutBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // get reference of main layouts  by ID
        ConstraintLayout constraintLayout1 = findViewById(R.id.b_card_1);
        ConstraintLayout constraintLayout2 = findViewById(R.id.b_card_2);
        ConstraintLayout constraintLayout3 = findViewById(R.id.b_card_3);
        ConstraintLayout constraintLayout4 = findViewById(R.id.b_card_4);


        constraintLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new BreathingCard1());
            }
        });

        constraintLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new BreathingCard2());
            }
        });

        constraintLayout3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new BreathingCard3());
            }
        });

        constraintLayout4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new BreathingCard4());
            }
        });


    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_breathing);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    // A helper method to replace the current fragment with a new one
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.Breathing_exercises, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}