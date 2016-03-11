package com.codepath.apps.critterfinder.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by smacgregor on 3/8/16.
 */

public class SwipeableCardAdapter extends ArrayAdapter<PetModel> {

    static class SwipeCardViewHolder {
        public static FrameLayout mBackground;
        @Bind(R.id.image_pet) ImageView mCardImage;
        @Bind(R.id.text_pet_name) TextView mCardText;
        @Bind(R.id.text_pet_gender) TextView mGenderText;

        public SwipeCardViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public SwipeableCardAdapter(Context context, List<PetModel> pets) {
        super(context, R.layout.item_swipeable_card, pets);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PetModel pet = getItem(position);

        SwipeCardViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_swipeable_card, parent, false);
            viewHolder = new SwipeCardViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (SwipeCardViewHolder) convertView.getTag();
            prepareViewForReuse(viewHolder);
        }

        setupView(viewHolder, pet);
        return convertView;
    }

    private void prepareViewForReuse(SwipeCardViewHolder swipeCardViewHolder) {
        // do nothing for now
    }

    private void setupView(SwipeCardViewHolder viewHolder, PetModel pet) {
        viewHolder.mCardText.setText(pet.getName());
        viewHolder.mGenderText.setText(pet.getSexFullName());
        Picasso.with(getContext())
                .load(pet.getImageUrl())
                //.placeholder(R.drawable.photo_placeholder)
                //.resize(viewHolder.imageWidth, 0)
                .into(viewHolder.mCardImage);

    }
}
