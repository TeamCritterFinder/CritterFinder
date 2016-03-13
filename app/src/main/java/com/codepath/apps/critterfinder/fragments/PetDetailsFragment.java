package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.adapters.ImageGalleryAdapter;
import com.codepath.apps.critterfinder.models.PetModel;
import com.viewpagerindicator.CirclePageIndicator;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

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
    @Bind(R.id.text_shelter_contact_name) TextView mShelterContactName;
    @Bind(R.id.text_shelter_contact_phone) TextView mShelterContactPhone;
    @Bind(R.id.text_shelter_state) TextView mShelterContactState;
    @Bind(R.id.text_shelter_city) TextView mShelterCity;
    @Bind(R.id.text_shelter_email) TextView mShelterEmail;
    @Bind(R.id.pet_image_gallery) ViewPager mImageGallery;
    @Bind(R.id.image_gallery_page_indicator) CirclePageIndicator mPageIndicator;
    private ImageGalleryAdapter mImageGalleryAdapter;

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
        setupPetImageGallery();
        return view;
    }

    private void setupPetDetailsView() {
        mPetName.setText(mPet.getName());
        mPetGender.setText(mPet.getSexFullName());
        mPetDescription.setText(mPet.getDescription());
        mPetDescription.setMovementMethod(new ScrollingMovementMethod());
        mShelterContactName.setText(mPet.getContactName());
        mShelterContactPhone.setText(mPet.getContactPhone());
        mShelterContactState.setText(mPet.getContactState());
        mShelterCity.setText(mPet.getContactCity()+", ");
        mShelterEmail.setText(mPet.getContactEmail());
    }

    private void setupPetImageGallery() {
        // TODO - once our pet models has an array of pet images we can use that
        List<String> images = new ArrayList<>();
        images.add(mPet.getImageUrl());
        images.add("https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/12729479_184511348589610_36145994_n.jpg");
        images.add("https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/12750125_1667429310176636_4996515_n.jpg");
        images.add("https://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/12071062_736784449785114_1231793951_n.jpg");
        mImageGalleryAdapter = new ImageGalleryAdapter(getContext(), mPet.getDetailImageUrls());
        mImageGallery.setAdapter(mImageGalleryAdapter);
        mPageIndicator.setSnap(true);
        mPageIndicator.setViewPager(mImageGallery);
    }
}
