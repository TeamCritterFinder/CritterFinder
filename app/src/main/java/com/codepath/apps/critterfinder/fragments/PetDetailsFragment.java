package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/29/16.
 */
public class PetDetailsFragment extends Fragment {

    private static final String ARGUMENT_PET = "ARGUMENT_PET";

    @Bind(R.id.text_pet_info) TextView mPetGender;
    @Bind(R.id.text_pet_description) TextView mPetDescription;
    @Bind(R.id.text_pet_breed) TextView mPetBreed;
    @Bind(R.id.text_shelter_contact_name) TextView mShelterContactName;
    @Bind(R.id.text_shelter_contact_phone) TextView mShelterContactPhone;
    @Bind(R.id.text_shelter_state) TextView mShelterContactState;
    @Bind(R.id.text_shelter_city) TextView mShelterCity;
    @Bind(R.id.text_shelter_email) TextView mShelterEmail;

    private PetModel mPet;

    public static PetDetailsFragment newInstance(PetModel pet) {
        PetDetailsFragment petDetailsFragment = new PetDetailsFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARGUMENT_PET, Parcels.wrap(pet));
        petDetailsFragment.setArguments(args);
        return petDetailsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPet = Parcels.unwrap(getArguments().getParcelable(ARGUMENT_PET));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pet_details, container, false);
        ButterKnife.bind(this, view);
        setupPetDetailsView();
        return view;
    }

    private void setupPetDetailsView() {
        mPetGender.setText(mPet.getSizeSexAge());
        mPetBreed.setText(mPet.getBreedFullName());
        mPetDescription.setText(mPet.getDescription());
        mShelterContactName.setText(mPet.getContactName());
        mShelterContactPhone.setText(mPet.getContactPhone());
        mShelterContactState.setText(mPet.getContactState());
        mShelterCity.setText(mPet.getContactCity()+", ");
        mShelterEmail.setText(mPet.getContactEmail());
    }
}
