package com.example.stickynotes.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.example.stickynotes.R;
import com.example.stickynotes.fragments.HomeFragment;
import com.example.stickynotes.fragments.ReminderFragment;
import com.example.stickynotes.fragments.ShoppingListFragment;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity {

    ChipNavigationBar chipNavigationBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chipNavigationBar = findViewById(R.id.bottom_navigation_bar);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

        bottomMenu();
    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment = new Fragment();

            if (i == R.id.home) {
                fragment = new HomeFragment();
            } else if (i == R.id.reminder) {
                fragment = new ReminderFragment();
            } else if (i == R.id.shopping_list) {
                fragment = new ShoppingListFragment();
            }

//                assert fragment != null;
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
        });
    }
}