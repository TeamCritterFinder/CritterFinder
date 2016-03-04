package com.codepath.apps.critterfinder.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.SwipeablePetsFragment;

import butterknife.ButterKnife;

public class PetBrowserActivity extends AppCompatActivity {

    SwipeablePetsFragment mSwipeablePetsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_browser);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSwipeablePetsFragment = (SwipeablePetsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_swipeable_pets_browser);
    }
}
