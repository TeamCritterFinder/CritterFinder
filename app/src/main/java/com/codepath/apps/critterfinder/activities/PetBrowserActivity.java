package com.codepath.apps.critterfinder.activities;

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

import butterknife.ButterKnife;

public class PetBrowserActivity extends AppCompatActivity implements SearchFilterDialog.OnSearchFilterFragmentInteractionListener {

    SwipeablePetsFragment mSwipeablePetsFragment;
    SearchFilter mSearchFilter;

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

    private void showSearchFilterDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        SearchFilterDialog searchFilterDialog = SearchFilterDialog.newInstance(mSearchFilter);
        searchFilterDialog.show(fragmentManager, "fragment_search_filter");
    }
}
