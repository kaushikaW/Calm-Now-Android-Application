package com.mycomapny.meditaionapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.mycomapny.meditaionapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);


        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Home());


        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.b_home:
                    replaceFragment(new Home());
                    break;
                case R.id.b_sounds:
                    replaceFragment(new Sounds());
                    break;
                case R.id.b_map:
                    replaceFragment(new Map());
                    break;
                case R.id.b_profile:
                    replaceFragment(new Profile());
                    break;
            }
            return true;
        });


    }


    // A helper method to replace the current fragment with a new one
    private void replaceFragment(Fragment fragment) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        fragmentTransaction.replace(R.id.main_frameLayout, fragment);
        fragmentTransaction.commit();
    }
}