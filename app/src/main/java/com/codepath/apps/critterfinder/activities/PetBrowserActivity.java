package com.codepath.apps.critterfinder.activities;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.fragments.SearchFilterDialog;
import com.codepath.apps.critterfinder.fragments.SwipeablePetsFragment;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.services.LocationService;

import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class PetBrowserActivity extends AppCompatActivity implements
        SearchFilterDialog.OnSearchFilterFragmentInteractionListener,
        LocationService.OnLocationListener {

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
        mLocationService = new LocationService(this, this);
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
    public void onFinishedSavingSearchFilter(SearchFilter searchFilter) {
        mSearchFilter = searchFilter;
        // TODO - fill this in to kick off a search against the criteria in searchFilter
        Snackbar.make(findViewById(android.R.id.content), "Search filters have been updated. Gender: " + searchFilter.getGender().toString(), Snackbar.LENGTH_LONG).show();
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

    private void showSearchFilterDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFilterDialog searchFilterDialog = SearchFilterDialog.newInstance(mSearchFilter);
        searchFilterDialog.show(fragmentManager, "fragment_search_filter");
    }
}
