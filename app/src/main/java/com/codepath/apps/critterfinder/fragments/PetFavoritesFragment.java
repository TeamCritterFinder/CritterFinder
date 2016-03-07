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
        final ArrayList<PetModel> pets = new ArrayList<PetModel>();
        pets.add(new PetModel("Bentley", "Male", "http://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/12071062_736784449785114_1231793951_n.jpg"));
        pets.add(new PetModel("Poupy", "Female", "https://scontent.fsnc1-1.fna.fbcdn.net/hphotos-xpf1/v/t1.0-9/12728762_466082140242189_6767280098049037634_n.jpg?oh=c126f89d6cf86e2565cfee259860f2d8&oe=57617860"));
        pets.add(new PetModel("Medor", "Female", "https://scontent.fsnc1-1.fna.fbcdn.net/hphotos-xfp1/v/t1.0-9/12745854_466081670242236_4794760548093027033_n.jpg?oh=d57de859d7025f418bcc38d7e0ea2bdd&oe=5767E3EC"));
        pets.add(new PetModel("Jade", "male", "https://scontent.fsnc1-1.fna.fbcdn.net/hphotos-xaf1/v/t1.0-9/421787_597568786939521_640112203_n.jpg?oh=7485347901753050c7a41e07de9cfd17&oe=5796EE28"));

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
