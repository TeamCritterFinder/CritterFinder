package com.codepath.apps.critterfinder.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.activities.PetDetailsActivity;
import com.codepath.apps.critterfinder.adapters.PetFavoritesAdapter;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.services.PetSearch;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetFavoritesFragment extends Fragment {

    private static final String ARGUMENT_PET = "ARGUMENT_PET";
    @Bind (R.id.rvPetFavorites) RecyclerView mrvPetFavorites;

    public static PetFavoritesFragment newInstance(PetModel pet) {
        PetFavoritesFragment petFavoritesFragment = new PetFavoritesFragment();
        Bundle args = new Bundle();

        // TODO - once our pet model is parcelable wire it up
        args.putString(ARGUMENT_PET, "");
        petFavoritesFragment.setArguments(args);
        return petFavoritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_favorites, container, false);
        ButterKnife.bind(this, view);

        setupFavoritesPet();
        return view;
    }

    public void setupFavoritesPet(){
        // Create and Initialize pets
        final ArrayList<PetModel> pets = PetSearch.getInstance().petsList;

        // Create adapter passing in the sample user data
        PetFavoritesAdapter adapter = new PetFavoritesAdapter(pets);

        adapter.setOnItemClickListener(new PetFavoritesAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                startActivity(PetDetailsActivity.getStartIntent(getContext(),pets.get(position)));
            }
        });
        // Attach the adapter to the recyclerview to populate items
        mrvPetFavorites.setAdapter(adapter);
        // Set layout manager to position the items
        mrvPetFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
