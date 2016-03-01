package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/29/16.
 */
public class PetDetailsFragment extends Fragment {

    private static final String ARGUMENT_PET = "ARGUMENT_USER_ID";

    @Bind(R.id.text_pet_details) TextView mPetDetailsLabel;

    public static PetDetailsFragment newInstance(PetModel pet) {
        PetDetailsFragment petDetailsFragment = new PetDetailsFragment();
        Bundle args = new Bundle();

        // TODO - once our pet model is parcelable wire it up
        args.putString(ARGUMENT_PET, "");
        petDetailsFragment.setArguments(args);
        return petDetailsFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_details, container, false);
        ButterKnife.bind(this, view);

        setupPetDetailsView();
        return view;
    }

    private void setupPetDetailsView() {
        mPetDetailsLabel.setText(getResources().getString(R.string.pet_details_description));
    }
}
