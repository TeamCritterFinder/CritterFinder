package com.codepath.apps.critterfinder.fragments;


import android.os.Bundle;
import android.support.annotation.BinderThread;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityManager;
import android.widget.ImageButton;
import android.widget.Toast;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.activities.PetDetailsActivity;
import com.codepath.apps.critterfinder.adapters.PetFavoritesAdapter;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.services.FavoritesService;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetFavoritesFragment extends Fragment {

    @Bind(R.id.rvPetFavorites)
    RecyclerView mrvPetFavorites;
    // Create and Initialize pets
    final List<PetModel> pets = FavoritesService.getInstance().getFavoritePets();

    public static PetFavoritesFragment newInstance() {
        PetFavoritesFragment petFavoritesFragment = new PetFavoritesFragment();
        return petFavoritesFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_favorites, container, false);
        ButterKnife.bind(this, view);

        setupFavoritesPet();
        return view;
    }

    public void setupFavoritesPet() {
        // Create and Initialize pets
       // final List<PetModel> pets = FavoritesService.getInstance().getFavoritePets();


        // Create adapter passing in the sample user data
        final PetFavoritesAdapter adapter = new PetFavoritesAdapter(pets);

//        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST);
//        mrvPetFavorites.addItemDecoration(itemDecoration);

        adapter.setOnItemClickListener(new PetFavoritesAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, View transitionSourceView, int position) {

                if (view.getId() == R.id.ibRemovePetFav) {
                    pets.remove(position);
                    adapter.notifyItemRemoved(position);
                } else {
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(getActivity(), transitionSourceView, "details");
                    startActivity(PetDetailsActivity.getStartIntent(getContext(), pets.get(position)), options.toBundle());
                }
            }

        });
        // Attach the adapter to the recyclerview to populate items
        mrvPetFavorites.setAdapter(adapter);
        // Set layout manager to position the items
        mrvPetFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
    }

}
