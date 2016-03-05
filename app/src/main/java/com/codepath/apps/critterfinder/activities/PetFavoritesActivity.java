package com.codepath.apps.critterfinder.activities;


import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.PetFavoritesFragment;
import com.codepath.apps.critterfinder.models.PetModel;

public class PetFavoritesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_favorites);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            PetFavoritesFragment petFavoritesFragment = PetFavoritesFragment.newInstance(null);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.layout_favorites_fragment_placeholder, petFavoritesFragment);
            fragmentTransaction.commit();
        }
    }
}
