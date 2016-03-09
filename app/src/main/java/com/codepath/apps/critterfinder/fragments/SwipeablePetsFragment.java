package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.SwipeableCardAdapter;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.services.PetSearch;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Our tinder-like pet browser
 */
public class SwipeablePetsFragment extends Fragment implements PetSearch.PetSearchCallbackInterface,
        SwipeFlingAdapterView.onFlingListener {

    private static final String ARGUMENT_SEARCH_FILTER = "ARGUMENT_SEARCH_FILTER";

    private PetSearch petSearch;
    private LinearLayout loadingProgress;
    private SearchFilter mSearchFilter;

    @Bind(R.id.card_view) SwipeFlingAdapterView mCardContainer;

    private List<PetModel> mPets;
    private SwipeableCardAdapter mCardAdapter;

    /**
     * Factory method to generate a swipe-able fragment
     * @param searchFilter
     * @return
     */
    public static SwipeablePetsFragment newInstance(SearchFilter searchFilter) {
        SwipeablePetsFragment swipeablePetsFragment = new SwipeablePetsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_SEARCH_FILTER, Parcels.wrap(searchFilter));
        swipeablePetsFragment.setArguments(args);
        return swipeablePetsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSearchFilter = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_SEARCH_FILTER));
        mPets = new ArrayList<>();
        mCardAdapter = new SwipeableCardAdapter(getContext(), mPets);
        petSearch = PetSearch.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_swipeable_pets, container, false);
        ButterKnife.bind(this, currentView);
        loadingProgress = (LinearLayout)getActivity().findViewById(R.id.loadingProgress);

        mCardContainer.setAdapter(mCardAdapter);
        mCardContainer.setFlingListener(this);
        doPetSearch(mSearchFilter);
        return currentView;
    }

    @Override
    public void removeFirstObjectInAdapter() {

    }

    @Override
    public void onLeftCardExit(Object o) {
        mCardAdapter.remove((PetModel) o);
    }

    @Override
    public void onRightCardExit(Object o) {
        mCardAdapter.remove((PetModel) o);
    }

    @Override
    public void onAdapterAboutToEmpty(int i) {
        doPetSearch(mSearchFilter);
    }

    @Override
    public void onScroll(float v) {
    }

    @OnClick(R.id.button_like)
    public void onLikeButtonClicked(Button button) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Yeah you like this pet!", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.button_pass)
    public void onPassButtonClicked(Button button) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Keep trying the right pet is out there!", Snackbar.LENGTH_LONG).show();
    }

    // start a pet search call
    public void doPetSearch(SearchFilter searchFilter) {
 //       loadingProgress.setVisibility(View.VISIBLE);
        petSearch.doPetSearch(searchFilter, this);
    }
    public void onPetSearchSuccess(String result) {
        mCardAdapter.addAll(petSearch.petsList);
 //       loadingProgress.setVisibility(View.GONE);
        Log.d("FindActivity", "SUCCESS");
    }
    public void onPetSearchError(String result) {
 //       loadingProgress.setVisibility(View.GONE);
        Log.d("FindActivity", "ERROR");
    }
}
