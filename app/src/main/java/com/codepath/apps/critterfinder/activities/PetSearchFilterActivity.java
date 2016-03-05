package com.codepath.apps.critterfinder.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.SearchCriteriaAdapter;
import com.codepath.apps.critterfinder.models.SearchCriteria;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.utils.DividerItemDecoration;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PetSearchFilterActivity extends AppCompatActivity implements SearchCriteriaAdapter.OnItemClickListener {

    private static String EXTRA_SEARCH_FILTER = "com.codepath.apps.critterfinder.activities.searchfilteractivity.searchfilter";

    @Bind(R.id.recycler_search_criteria) RecyclerView mSearchCriteriaRecyclerView;

    private List<SearchCriteria> mSearchCriteria;
    private SearchCriteriaAdapter mSearchCriteriaAdapter;
    private SearchFilter mSearchFilter;

    // can the model manage the view strings to keep this out of the activity?
    String[] mGenderViewStrings;
    String[] mSpeciesViewStrings;
    String[] mAgeViewStrings;
    String[] mSizeViewStrings;

    /**
     * Create an intent which will start the pet details activity
     * @param context
     * @param searchFilter
     * @return
     */
    public static Intent getStartIntent(Context context, SearchFilter searchFilter) {
        Intent intent= new Intent(context, PetSearchFilterActivity.class);
        intent.putExtra(PetSearchFilterActivity.EXTRA_SEARCH_FILTER, Parcels.wrap(searchFilter));
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_search_filter);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add a back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mSearchFilter = Parcels.unwrap(getIntent().getParcelableExtra(PetSearchFilterActivity.EXTRA_SEARCH_FILTER));

        setupCriteriaDisplayStrings();
        createSearchCriteriaFromFilter(mSearchFilter);
        setupSearchCriteriaView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        SearchCriteria searchCriteria = mSearchCriteria.get(position);
        switch (searchCriteria.criteriaType) {
            case GENDER:
                changeGender(searchCriteria, position);
                break;
            case SPECIES:
                changeSpecies(searchCriteria, position);
                break;
            case AGE:
                changeAge(searchCriteria, position);
                break;
            case SIZE:
                changeSize(searchCriteria, position);
                break;
            default:
                break;
        }
    }

    private void setupSearchCriteriaView() {
        mSearchCriteriaAdapter = new SearchCriteriaAdapter(mSearchCriteria);
        mSearchCriteriaAdapter.setOnItemClickListener(this);
        mSearchCriteriaRecyclerView.setAdapter(mSearchCriteriaAdapter);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST);
        mSearchCriteriaRecyclerView.addItemDecoration(itemDecoration);
        mSearchCriteriaRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setupCriteriaDisplayStrings() {
        Resources res = getResources();
        mGenderViewStrings = res.getStringArray(R.array.gender);
        mSpeciesViewStrings = res.getStringArray(R.array.species);
        mAgeViewStrings = res.getStringArray(R.array.age);
        mSizeViewStrings = res.getStringArray(R.array.size);
    }

    private void createSearchCriteriaFromFilter(SearchFilter searchFilter) {
        mSearchCriteria = new ArrayList<>();

        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.LOCATION,
                getResources().getString(R.string.title_search_criteria_location),
                mSearchFilter.getPostalCode()));

        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.GENDER,
                getResources().getString(R.string.title_search_criteria_gender),
                mGenderViewStrings[searchFilter.getGender().ordinal()]));

        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.SPECIES,
                getResources().getString(R.string.title_search_criteria_species),
                mSpeciesViewStrings[searchFilter.getSpecies().ordinal()]));

        SearchFilter.Age age = searchFilter.getAge();
        String ageValue = (age != null) ? mAgeViewStrings[searchFilter.getSize().ordinal()] : null;
        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.AGE,
                getResources().getString(R.string.title_search_criteria_age),
                ageValue));

        SearchFilter.Size size = searchFilter.getSize();
        String sizeValue = (size != null) ? mSizeViewStrings[searchFilter.getSize().ordinal()] : null;
        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.SIZE,
                getResources().getString(R.string.title_search_criteria_size),
                sizeValue));

        mSearchCriteria.add(new SearchCriteria(SearchCriteria.CriteriaType.BREEDS,
                getResources().getString(R.string.title_search_criteria_breeds),
                "Coton Du Tulear"));
    }

    private void changeGender(final SearchCriteria searchCriteria, final int position) {
        new MaterialDialog.Builder(this)
                .title(searchCriteria.title)
                .items(mGenderViewStrings)
                .itemsCallbackSingleChoice(mSearchFilter.getGender().ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mSearchFilter.setGender(SearchFilter.Gender.values()[which]);
                        searchCriteria.value = text.toString();
                        mSearchCriteriaAdapter.notifyItemChanged(position);
                        return true;
                    }
                })
                .positiveText(getResources().getString(R.string.filter_choose))
                .show();
    }

    private void changeSpecies(final SearchCriteria searchCriteria, final int position) {
        new MaterialDialog.Builder(this)
                .title(searchCriteria.title)
                .items(mSpeciesViewStrings)
                .itemsCallbackSingleChoice(mSearchFilter.getSpecies().ordinal(), new MaterialDialog.ListCallbackSingleChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        mSearchFilter.setSpecies(SearchFilter.Species.values()[which]);
                        searchCriteria.value = text.toString();
                        mSearchCriteriaAdapter.notifyItemChanged(position);
                        return true;
                    }
                })
                .positiveText(getResources().getString(R.string.filter_choose))
                .show();
    }

    private void changeAge(final SearchCriteria searchCriteria, final int position) {
        // TODO - SearchFilter needs to support an array of ages
        Integer[] selectedAge = null;
        if (mSearchFilter.getAge() != null) {
            selectedAge = new Integer[] {mSearchFilter.getAge().ordinal()};
        }
        new MaterialDialog.Builder(this)
                .title(searchCriteria.title)
                .items(mAgeViewStrings)
                .itemsCallbackMultiChoice(selectedAge, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        if (which == null || which.length == 0) {
                            mSearchFilter.setAge(null);
                            searchCriteria.value = "";
                        } else {
                            mSearchFilter.setAge(SearchFilter.Age.values()[which[0]]);
                            searchCriteria.value = text[0].toString();
                        }
                        mSearchCriteriaAdapter.notifyItemChanged(position);
                        return false;
                    }
                })
                .positiveText(getResources().getString(R.string.filter_choose))
                .show();
    }

    private void changeSize(final SearchCriteria searchCriteria, final int position) {
        new MaterialDialog.Builder(this)
                .title(searchCriteria.title)
                .items(mSizeViewStrings)
                .itemsCallbackMultiChoice(null, new MaterialDialog.ListCallbackMultiChoice() {
                    @Override
                    public boolean onSelection(MaterialDialog dialog, Integer[] which, CharSequence[] text) {
                        // TODO - SearchFilter needs to support an array of sizes
                        if (which == null || which.length == 0) {
                            mSearchFilter.setSize(null);
                            searchCriteria.value = "";
                        } else {
                            mSearchFilter.setSize(SearchFilter.Size.values()[which[0]]);
                            searchCriteria.value = text[0].toString();
                        }
                        mSearchCriteriaAdapter.notifyItemChanged(position);
                        return false;
                    }
                })
                .positiveText(getResources().getString(R.string.filter_choose))
                .show();
    }
}
