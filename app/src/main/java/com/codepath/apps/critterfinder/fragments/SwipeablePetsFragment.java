package com.codepath.apps.critterfinder.fragments;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Our tinder-like pet browser
 */
public class SwipeablePetsFragment extends Fragment {

    @Bind(R.id.text_pet_gender) TextView mPetGender;
    @Bind(R.id.text_pet_name) TextView mPetName;
    @Bind(R.id.image_pet) ImageView mPetImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // nothing to do here yet...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View currentView = inflater.inflate(R.layout.fragment_swipeable_pets, container, false);
        ButterKnife.bind(this, currentView);

        setupPet();
        return currentView;
    }

    @OnClick(R.id.button_like)
    public void onLikeButtonClicked(Button button) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Yeah you like this pet!", Snackbar.LENGTH_LONG).show();
    }

    @OnClick(R.id.button_pass)
    public void onPassButtonClicked(Button button) {
        Snackbar.make(getActivity().findViewById(android.R.id.content), "Keep trying the right pet is out there!", Snackbar.LENGTH_LONG).show();
    }

    private void setupPet() {
        mPetName.setText("Bentley The Coton");
        mPetGender.setText("Male");
        Picasso.with(mPetImage.getContext()).
                load("http://scontent.cdninstagram.com/t51.2885-15/s640x640/sh0.08/e35/12071062_736784449785114_1231793951_n.jpg").
                into(mPetImage);
    }
}
