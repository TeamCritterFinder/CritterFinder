package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.services.FavoritesService;
import com.codepath.apps.critterfinder.services.PetSearch;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Our tinder-like pet browser
 */
public class SwipeablePetsFragment extends Fragment implements PetSearch.PetSearchCallbackInterface  {

    private static final String ARGUMENT_SEARCH_FILTER = "ARGUMENT_SEARCH_FILTER";

    private PetSearch petSearch;
    private LinearLayout loadingProgress;
    private SearchFilter mSearchFilter;
    private PetModel mCurrentPet;

    @Bind(R.id.text_pet_gender) TextView mPetGender;
    @Bind(R.id.text_pet_name) TextView mPetName;
    @Bind(R.id.image_pet) ImageView mPetImage;

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
        petSearch = PetSearch.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_swipeable_pets, container, false);
        ButterKnife.bind(this, currentView);
        loadingProgress = (LinearLayout)getActivity().findViewById(R.id.loadingProgress);
        doPetSearch(mSearchFilter);
        return currentView;
    }

    @OnClick(R.id.button_like)
    public void onLikeButtonClicked(Button button) {
        FavoritesService.getInstance().addFavoritePet(mCurrentPet);
        updateViewWithPet(petSearch.getNextPet(this));
    }

    @OnClick(R.id.button_pass)
    public void onPassButtonClicked(Button button) {
        FavoritesService.getInstance().skipPet(mCurrentPet);
        updateViewWithPet(petSearch.getNextPet(this));
    }

    // update the View with the image and data for a Pet
    private void updateViewWithPet(PetModel petModel) {
        this.mPetName.setText(petModel.getName());
        this.mPetGender.setText(petModel.getSexFullName());
        mCurrentPet = petModel;
        Picasso.with(mPetImage.getContext()).load(petModel.getImageUrl()).into(mPetImage);
    }

    // start a pet search call
    public void doPetSearch(SearchFilter searchFilter) {
 //       loadingProgress.setVisibility(View.VISIBLE);
        petSearch.doPetSearch(searchFilter, this);
    }
    public void onPetSearchSuccess(String result) {
 //       loadingProgress.setVisibility(View.GONE);
        updateViewWithPet(petSearch.getCurrentPet());
        Log.d("FindActivity", "SUCCESS");
    }
    public void onPetSearchError(String result) {
 //       loadingProgress.setVisibility(View.GONE);
        Log.d("FindActivity", "ERROR");
    }

}
