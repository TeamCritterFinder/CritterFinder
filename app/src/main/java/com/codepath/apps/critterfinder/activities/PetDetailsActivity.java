package com.codepath.apps.critterfinder.activities;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.ImageGalleryAdapter;
import com.codepath.apps.critterfinder.fragments.PetDetailsFragment;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.utils.CircularRevealHelper;
import com.viewpagerindicator.CirclePageIndicator;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetDetailsActivity extends AppCompatActivity implements FloatingActionButton.OnClickListener,
        PetDetailsFragment.OnDetailsFragmentListener, AppBarLayout.OnOffsetChangedListener
{

    private static String EXTRA_PET = "com.codepath.apps.critterfinder.activities.details.pet";
    private PetModel mPet;

    @Bind(R.id.pet_details_fab) FloatingActionButton mFloatingActionButton;
    @Bind(R.id.pet_image_gallery) ViewPager mImageGallery;
    @Bind(R.id.image_gallery_page_indicator) CirclePageIndicator mPageIndicator;
    @Bind(R.id.appbar_layout) AppBarLayout mAppBarLayout;
    @Bind(R.id.details_collapsing_toolbar) CollapsingToolbarLayout mCollapsingToolbarLayout;
    @Bind(R.id.toolbar) Toolbar mToolbar;

    private Transition.TransitionListener mEnterTransitionListener;
    private ImageGalleryAdapter mImageGalleryAdapter;

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

        setSupportActionBar(mToolbar);

        mFloatingActionButton.setOnClickListener(this);
        mAppBarLayout.addOnOffsetChangedListener(this);

        // add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mPet = Parcels.unwrap(getIntent().getParcelableExtra(EXTRA_PET));

        if (savedInstanceState == null) {
            setupPetDetails(mPet);
        }

        mCollapsingToolbarLayout.setTitle(mPet.getName());
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        setupPetImageGallery();
        setupTransitionListener();
    }

    @Override
    public void onClick(View v) {
        contactShelter(mPet);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            CircularRevealHelper.exitReveal(mFloatingActionButton, this);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        CircularRevealHelper.exitReveal(mFloatingActionButton, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //stub to ensure the implicit intent for contacting a shelter returns control to our app
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        // TODO - calculate the correct height of the toolbar in the collapsed state
        // instead of hard coding this number
        if (Math.abs(verticalOffset) >= 683) {
            // collapsed
            getSupportActionBar().setHomeAsUpIndicator(null);
        } else {
            // expanded
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_button_white);
        }
    }

    private void setupPetDetails(PetModel pet) {
        PetDetailsFragment petDetailsFragment = PetDetailsFragment.newInstance(pet);
        getSupportFragmentManager().beginTransaction().
                replace(R.id.layout_details_fragment_placeholder, petDetailsFragment).
                commit();
    }

    private void contactShelter(PetModel mPet) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");

        final String subject = getString(R.string.contact_pet_subject, mPet.getName());
        final String body = getString(R.string.contact_pet_body, mPet.getName());

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPet.getContactEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.contact_no_email_client), Snackbar.LENGTH_LONG).show();

        }
    }

    private void setupPetImageGallery() {
        mImageGalleryAdapter = new ImageGalleryAdapter(this, mPet.getDetailImageUrls());
        mImageGallery.setAdapter(mImageGalleryAdapter);
        mPageIndicator.setSnap(true);
        mPageIndicator.setViewPager(mImageGallery);
    }

    private void setupTransitionListener() {
        mEnterTransitionListener = new Transition.TransitionListener() {
            @Override
            public void onTransitionStart(Transition transition) {}

            @Override
            public void onTransitionEnd(Transition transition) {
                CircularRevealHelper.enterReveal(mFloatingActionButton, PetDetailsActivity.this, mEnterTransitionListener);
            }

            @Override
            public void onTransitionCancel(Transition transition) {}

            @Override
            public void onTransitionPause(Transition transition) {}

            @Override
            public void onTransitionResume(Transition transition) {}
        };

        getWindow().getEnterTransition().addListener(mEnterTransitionListener);
    }

    // Open the map in Google Maps if available
    public void onMap(String uri) {
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Log.d("PetDetailsActivity", "MAP Intent could not be fired");
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.no_map_client), Snackbar.LENGTH_LONG).show();
        }
    }
}

