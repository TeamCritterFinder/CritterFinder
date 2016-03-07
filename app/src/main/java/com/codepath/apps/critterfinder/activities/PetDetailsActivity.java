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

import org.parceler.Parcels;

import butterknife.ButterKnife;

public class PetDetailsActivity extends AppCompatActivity {

    private static String EXTRA_PET = "com.codepath.apps.critterfinder.activities.details.pet";
    private PetModel mPet;

    /**
     * Create an intent which will start the pet details activity
     * @param context
     * @param pet
     * @return
     */
    public static Intent getStartIntent(Context context, PetModel pet) {
        Intent intent= new Intent(context, PetDetailsActivity.class);
        intent.putExtra(PetDetailsActivity.EXTRA_PET, Parcels.wrap(pet));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_details);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PET));

        if (savedInstanceState == null) {
            setupPetDetails(mPet);
        }
    }

    private void setupPetDetails(PetModel pet) {
        PetDetailsFragment petDetailsFragment = PetDetailsFragment.newInstance(pet);
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
