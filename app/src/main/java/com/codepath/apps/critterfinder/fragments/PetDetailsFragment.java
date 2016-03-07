package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/29/16.
 */
public class PetDetailsFragment extends Fragment {

    private static final String ARGUMENT_PET = "ARGUMENT_PET";

    @Bind(R.id.text_pet_description) TextView mPetDescription;
    @Bind(R.id.text_pet_gender) TextView mPetGender;
    @Bind(R.id.text_pet_name) TextView mPetName;
    @Bind(R.id.image_pet) ImageView mPetImage;

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
        mPetName.setText(mPet.getName());
        mPetGender.setText(mPet.getSex());
        Picasso.with(mPetImage.getContext()).
                load(mPet.getImageUrl()).
                into(mPetImage);
        mPetDescription.setText("Mona Lisa is a super loving and affectionate girl. She is about 4 years old, 53 lbs and 16 inches tall. She is very gentle and gets along with all the people she meets including children as young as babies. Whenever she goes to the coffee shop to keep her foster mom company she is greeted by people who want to pet her. She is great at accepting pets and treats from all people and at settling down to watch people walk by while her foster mom drinks her tea and catches up with friends. She is not good with cats. She enjoys belly rubs, cuddles, walks, toys and rides in the car. She likes to sit next to people she meets and is very friendly. She is devoted to her family. She is good in the car, happy at the park, she is a wonderful warm girl. She loves the beach, digging the sand, jumping in the water and running! She is also a good camping dog! Loving and very playful. A smarty too. This good girl would make a fantastic addition to a household where she can be loved and adored for the rest of her life. A very loving loyal girl looking for her loving forever home. Mona would do well as an only dog or with males. She is slow to warm up to other dogs but is very affectionate and playful once she has gotten to know a dog. She cuddles, runs around with, and plays tug of war with her current foster brother. Mona LisaÃ¢Â€Â™s adoption fee is $250. She is being fostered in Walnut Creek, Ca All our dogs are in private foster homes and arrangements to meet our dogs occur after an application has been received. Please write to dogzonerescue@yahoo.com with questions and to receive an application to fill in.");
    }
}
