package com.codepath.apps.critterfinder.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.PetDetailsFragment;
import com.codepath.apps.critterfinder.models.PetModel;

import butterknife.ButterKnife;

public class PetDetailsActivity extends AppCompatActivity {

    private static String EXTRA_PET = "com.codepath.apps.critterfinder.activities.details.pet";

    /**
     * Create an intent which will start the pet details activity
     * @param context
     * @param pet
     * @return
     */
    public static Intent getStartIntent(Context context, PetModel pet) {
        Intent intent= new Intent(context, PetDetailsActivity.class);
        // TODO - once our pet model is parcelabe, work our magic
        intent.putExtra(PetDetailsActivity.EXTRA_PET, "");
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // TODO - pass in the pet
            setupPetDetails(null);
        }
    }

    private void setupPetDetails(PetModel pet) {
        PetDetailsFragment petDetailsFragment = PetDetailsFragment.newInstance(null);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.layout_details_fragment_placeholder, petDetailsFragment).
                commit();
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
