package com.codepath.apps.critterfinder.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;

import org.parceler.Parcels;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 2/29/16.
 */
public class PetDetailsFragment extends Fragment {

    private static final String ARGUMENT_PET = "ARGUMENT_PET";
    private View mFragmentView; // View for the inflated Fragment
    private PetDetailsFragment.OnDetailsFragmentListener mDetailsFragmentListener;

    @Bind(R.id.text_pet_info) TextView mPetInfo;
    @Bind(R.id.text_pet_description) TextView mPetDescription;
    @Bind(R.id.text_pet_breed) TextView mPetBreed;
    @Bind(R.id.text_shelter_contact_name) TextView mShelterContactName;
    @Bind(R.id.text_shelter_contact_phone) TextView mShelterContactPhone;
    @Bind(R.id.text_shelter_location) TextView mShelterLocation;
    @Bind(R.id.text_shelter_email) TextView mShelterEmail;
    @Bind(R.id.email_icon) ImageView mEmailIcon;
    @Bind(R.id.phone_icon) ImageView mPhoneIcon;
    @Bind(R.id.locate_icon) ImageView mLocateIcon;

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
        mFragmentView = inflater.inflate(R.layout.fragment_pet_details, container, false);
        ButterKnife.bind(this, mFragmentView);
        setupPetDetailsView();
        mEmailIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmail();
            }
        });
        mPhoneIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
        mShelterEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onEmail();
            }
        });
        mLocateIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLocate();
            }
        });
        mShelterContactPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCall();
            }
        });
        mShelterLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLocate();
            }
        });

        return mFragmentView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnDetailsFragmentListener) {
            mDetailsFragmentListener = (OnDetailsFragmentListener)context;
        } else {
            throw new RuntimeException(context.toString());
        }
    }

    private void setupPetDetailsView() {
        mPetInfo.setText(mPet.getSizeSexAge());
        mPetBreed.setText(mPet.getBreedFullName());
        mPetDescription.setText(mPet.getDescription());
        mShelterContactName.setText(mPet.getContactName());
  //      mShelterContactPhone.setText(mPet.getContactPhone());
  //      mShelterLocation.setText(mPet.getLocation());
        addUnderline(mShelterLocation,mPet.getLocation());
        addUnderline(mShelterEmail, mPet.getContactEmail());
        addUnderline(mShelterContactPhone,mPet.getContactPhone());
//        mShelterEmail.setText(mPet.getContactEmail());
    }

    private void addUnderline(TextView textView,String textString) {
        SpannableString content = new SpannableString(textString);
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }
    // open in map
    public void onLocate() {
        String address = mPet.getLocation();
        // Looks like the address1 is always a PO Box # which won't help us to map the location !
        if (mPet.getContactAddress2() != null && mPet.getContactAddress2().length() > 0) {
            address = mPet.getContactAddress2();
            address += " " + mPet.getLocation();
        } else
        {
            address = mPet.getLocation();
        }
        String uriEncoded;
        try {
            uriEncoded = URLEncoder.encode(address, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return;
        }
        String uri = String.format(Locale.ENGLISH, "geo:0,0?q=%s", uriEncoded);
        Log.d("PetDetailsFragment", "Opening address in Google Maps: " + uri);

        if (mDetailsFragmentListener != null) {
            mDetailsFragmentListener.onMap(uri);
        }
    }

    // Call the shelter
    public void onCall() {
        Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + mPet.getContactPhone()));
        startActivity(intent);
    }

    // Create an Email to the shelter
    public void onEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("plain/text");

        final String subject = getString(R.string.contact_pet_subject, mPet.getName());
        final String body = getString(R.string.contact_pet_body, mPet.getName());

        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{mPet.getContactEmail()});
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        try {
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(mFragmentView, getString(R.string.contact_no_email_client), Snackbar.LENGTH_LONG).show();
        }
    }

    public interface OnDetailsFragmentListener {
        void onMap(String url);
    }

}
