package com.codepath.apps.critterfinder.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.PetFavoritesAdapter;
import com.codepath.apps.critterfinder.models.PetModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PetFavoritesFragment extends Fragment {

private static final String ARGUMENT_PET = "ARGUMENT_PET";

    @Bind (R.id.rvPetFavorites)
    RecyclerView mrvPetFavorites;
   // @Bind(R.id.text_pet_favorites)
   // TextView mPetFavoritesLabel;

    OnItemClickListener mOnItemClickListener;

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

        // Initialize pets
        //pets = PetModel.fromJSONArray()
        // Create adapter passing in the sample user data
       // PetFavoritesAdapter adapter = new PetFavoritesAdapter(pe);
        // Attach the adapter to the recyclerview to populate items
       // mrvPetFavorites.setAdapter(adapter);
        // Set layout manager to position the items
        mrvPetFavorites.setLayoutManager(new LinearLayoutManager(getContext()));
        // That's all!

        return view;
    }


    public interface OnItemClickListener {
        /**
         * An item in the RecyclerView has been clicked
         * @param view
         * @param position
         */
        void onItemClick(View view, int position);
    }

    /**
     * Register a callback to be notified when an item in the RecyclerView is clicked
     * @param clickListener
     */
    public void setOnItemClickListener(final OnItemClickListener clickListener) {
        mOnItemClickListener = clickListener;
    }


}
