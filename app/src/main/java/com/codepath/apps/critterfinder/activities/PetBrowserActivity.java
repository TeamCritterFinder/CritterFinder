package com.codepath.apps.critterfinder.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.SwipeablePetsFragment;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.services.LocationService;

import org.parceler.Parcels;

import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PetBrowserActivity extends AppCompatActivity implements
        LocationService.OnLocationListener {

    private final int SEARCH_FILTER_REQUEST_CODE = 20;

    SwipeablePetsFragment mSwipeablePetsFragment;
    SearchFilter mSearchFilter;
    LocationService mLocationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_browser);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mSearchFilter = new SearchFilter();
        mSwipeablePetsFragment = (SwipeablePetsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_swipeable_pets_browser);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_pet_browser, menu);
        return true;
    }

    @Override
    @NeedsPermission({Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION})
    protected void onStart() {
        super.onStart();
        if (mLocationService == null) {
            mLocationService = new LocationService(this, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search:
                showSearchFilterDialog();
                return true;
            // TODO - add support for loading the favorites fragment here
            default:
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PetBrowserActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }

    @Override
    public void onLocationAvailable(String postalCode) {
        // TODO - now that we have a postal code we can kick off the initial search for pets
        // with the users current location
        mSearchFilter.setPostalCode(postalCode);
        Snackbar.make(findViewById(android.R.id.content),
                "Location found: " + postalCode,
                Snackbar.LENGTH_LONG).
                show();
    }

    @Override
    public void onLocationFailed() {
        Snackbar.make(findViewById(android.R.id.content),
                "Make sure you have google play services installed",
                Snackbar.LENGTH_LONG).
                show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SEARCH_FILTER_REQUEST_CODE) {
            mSearchFilter = Parcels.unwrap(data.getParcelableExtra(PetSearchFilterActivity.EXTRA_SEARCH_FILTER));
            Snackbar.make(findViewById(android.R.id.content), "Search filters have been updated. Gender: " + mSearchFilter.getGender().toString(), Snackbar.LENGTH_LONG).show();
            // TODO - refresh our search?
        }
    }

    private void showSearchFilterDialog() {
        startActivityForResult(PetSearchFilterActivity.getStartIntent(this, mSearchFilter), SEARCH_FILTER_REQUEST_CODE);
    }
}
