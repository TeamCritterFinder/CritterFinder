package com.codepath.apps.critterfinder.activities;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.PetFavoritesFragment;

public class PetFavoritesActivity extends AppCompatActivity {

    /**
     * Create an intent which will start the pet details activity
     * @param context
     * @return
     */
    public static Intent getStartIntent(Context context) {
        Intent intent= new Intent(context, PetFavoritesActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            PetFavoritesFragment petFavoritesFragment = PetFavoritesFragment.newInstance(null);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_favorites_fragment_placeholder, petFavoritesFragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            // for now the back button will save the results
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
