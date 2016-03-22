package com.codepath.apps.critterfinder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.SwipeableCardAdapter;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.services.FavoritesService;
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
        SwipeFlingAdapterView.onFlingListener,
        SwipeFlingAdapterView.OnItemClickListener {

    private static final String ARGUMENT_SEARCH_FILTER = "ARGUMENT_SEARCH_FILTER";

    private PetSearch petSearch;
    private LinearLayout loadingProgressLayout;
    private TextView loadingMessageView;
    private SearchFilter mSearchFilter;
    private OnSwipeablePetsFragmentListener mFragmentListener;
    private ProgressBar progressBar;

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
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSwipeablePetsFragmentListener) {
            mFragmentListener = (OnSwipeablePetsFragmentListener)context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_swipeable_pets, container, false);
        ButterKnife.bind(this, currentView);
        loadingProgressLayout = (LinearLayout)currentView.findViewById(R.id.loadingProgress);
        loadingMessageView = (TextView)currentView.findViewById(R.id.loadingMessage);
        progressBar = (ProgressBar)currentView.findViewById(R.id.pb);
        loadingProgressLayout.setVisibility(View.VISIBLE);
        mCardContainer.setAdapter(mCardAdapter);
        mCardContainer.setFlingListener(this);
        mCardContainer.setOnItemClickListener(this);
        if (mSearchFilter != null)
            doPetSearch(mSearchFilter);
        return currentView;
    }

    @Override
    public void removeFirstObjectInAdapter() {}

    @Override
    public void onLeftCardExit(Object o) {
        PetModel pet = (PetModel) o;
        skipPet(pet);
        mCardAdapter.remove(pet);
    }

    @Override
    public void onRightCardExit(Object o) {
        PetModel pet = (PetModel) o;
        likePet(pet);
        mCardAdapter.remove(pet);
    }

    @Override
    public void onAdapterAboutToEmpty(int itemsInAdapter) {
        if (itemsInAdapter > 0) {
            petSearch.doLoadMorePets(this);
        }
    }

    @Override
    public void onScroll(float scrollProgressPercent) {
        View view = mCardContainer.getSelectedView();
        view.findViewById(R.id.swipe_card_background).setAlpha(0); // hide the background frame on the card
        view.findViewById(R.id.item_swipe_right_indicator).setAlpha(scrollProgressPercent < 0 ? -scrollProgressPercent : 0);
        view.findViewById(R.id.item_swipe_left_indicator).setAlpha(scrollProgressPercent > 0 ? scrollProgressPercent : 0);
    }

    @Override
    public void onItemClicked(int i, Object o) {
        View view = mCardContainer.getSelectedView();
        view.findViewById(R.id.swipe_card_background).setAlpha(0);
        mFragmentListener.onPetSelected((PetModel) o, view.findViewById(R.id.image_pet));
    }

    @OnClick(R.id.button_like)
    public void onLikeButtonClicked(ImageButton button) {
        // hide the frame on the card being dismissed
        mCardContainer.getSelectedView().findViewById(R.id.swipe_card_background).setAlpha(0);
        // route the click through the card which understands which pet is currently selected
        mCardContainer.getTopCardListener().selectRight();
    }

    @OnClick(R.id.button_pass)
    public void onPassButtonClicked(Button button) {
        // hide the frame on the card being dismissed
        mCardContainer.getSelectedView().findViewById(R.id.swipe_card_background).setAlpha(0);
        mCardContainer.getTopCardListener().selectLeft();
    }

    private void likePet(PetModel pet) {
        FavoritesService.getInstance().addFavoritePet(pet);
    }

    private void skipPet(PetModel pet) {
        FavoritesService.getInstance().addFavoritePet(pet);
    }

    // start a pet search call
    public void doPetSearch(SearchFilter searchFilter) {
        loadingProgressLayout.setVisibility(View.VISIBLE);
        // when performing a new search, clear out all existing
        // search results
        mCardAdapter.clear();
        petSearch.doPetSearch(searchFilter, this);
    }
    public void onPetSearchSuccess(List<PetModel> pets) {
        mCardAdapter.addAll(pets);
        loadingProgressLayout.setVisibility(View.GONE);
        Log.d("FindActivity", "SUCCESS Finding Pets");
    }
    public void onPetSearchError(String result) {
        Log.d("FindActivity", "ERROR FINDING PETS");
        loadingMessageView.setText("Error finding pets, make sure you have an internet connection");
        progressBar.setVisibility(View.INVISIBLE);
    }

    public interface OnSwipeablePetsFragmentListener {
        void onPetSelected(PetModel pet, View transitionView);
    }
}
