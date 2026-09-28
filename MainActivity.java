package com.example.studyhub.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.studyhub.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView nav = findViewById(R.id.bottom_nav);
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            Fragment fragment;
            if (id == R.id.nav_subjects) fragment = new SubjectsFragment();
            else if (id == R.id.nav_tasks) fragment = new TasksFragment();
            else fragment = new NotesFragment();
            setTitle(item.getTitle());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, fragment).commit();
            return true;
        });
        if (savedInstanceState == null) nav.setSelectedItemId(R.id.nav_subjects);
    }
}
