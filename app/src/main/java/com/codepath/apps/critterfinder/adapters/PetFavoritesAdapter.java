package com.codepath.apps.critterfinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.OnClick;

/**
 * Created by carlybaja on 3/2/16.
 */
public class PetFavoritesAdapter extends RecyclerView.Adapter<PetFavoritesAdapter.ViewHolder> {

    private OnItemClickListener mOnItemClickListener;

    // Store a member variable for the pets
    private List<PetModel> mPets;

    // Pass in the pet array into the constructor
    public PetFavoritesAdapter(List<PetModel> petModels) {
        mPets = petModels;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvPetFavName;
        public TextView tvPetFavSex;
        public RoundedImageView ivPetFavImage;
        public TextView tvPetFavSize;
        public TextView tvPetFavAge;
        public TextView tvPetFavBreeds;
        public ImageButton ibRemovePetPav;
        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvPetFavName = (TextView) itemView.findViewById(R.id.petFavName);
            tvPetFavSex = (TextView) itemView.findViewById(R.id.petFavSex);
            ivPetFavImage = (RoundedImageView) itemView.findViewById(R.id.petFavImage);
            tvPetFavSize = (TextView) itemView.findViewById(R.id.petFavSize);
            tvPetFavAge = (TextView) itemView.findViewById(R.id.petFavAge);
            tvPetFavBreeds = (TextView) itemView.findViewById(R.id.petFavBreeds);
            ibRemovePetPav = (ImageButton) itemView.findViewById(R.id.ibRemovePetFav);
            ibRemovePetPav.setOnClickListener(this);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (v instanceof ImageButton){
                mOnItemClickListener.onButtonClicked((ImageButton)v,this,getAdapterPosition());
            }else {

                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(v, ivPetFavImage, getAdapterPosition());
                }
            }
        }

        @OnClick({R.id.ibRemovePetFav})
        public void onButtonClicked(View button) {
            onClick(button);
        }

    }

    @Override
    public PetFavoritesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the custom layout
        View petFavoritesView = inflater.inflate(R.layout.pet_favorites_items, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(petFavoritesView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(PetFavoritesAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        PetModel petModel = mPets.get(position);

        // Set item views based on the data model
        TextView tvName = viewHolder.tvPetFavName;
        tvName.setText(petModel.getName());

        // Set item views based on the data model
        TextView tvSex = viewHolder.tvPetFavSex;
        tvSex.setText(petModel.getSexFullName());

        //
        ImageView ivImage = viewHolder.ivPetFavImage;
        Picasso.with(ivImage.getContext())
                .load(petModel.getImageUrl())
                .placeholder(R.drawable.ic_launcher)
                .into(viewHolder.ivPetFavImage);
        viewHolder.ivPetFavImage.setTag(position);

        // Set item views based on the data model
        TextView tvSize = viewHolder.tvPetFavSize;
        tvSize.setText(petModel.getSizeFullName());

        //Set item views based on the data model
        TextView tvAge = viewHolder.tvPetFavAge;
        tvAge.setText(petModel.getAge());

        //Set item views based on the data model
        TextView tvBreeds = viewHolder.tvPetFavBreeds;
        tvBreeds.setText(petModel.getBreedFullName());
    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mPets.size();
    }

    public interface OnItemClickListener {
        /**
         * An item in the recycler view has been clicked
         * @param view
         * @param position
         */
        void onItemClick(View view, View transitionSourceView, int position);
        void onButtonClicked(ImageButton button,ViewHolder viewHolder, int position);

        }

    /**
     * Register a callback to be notified when an item in the RecyclerView is clicked
     * @param mOnItemClickListener
     */
    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

}
