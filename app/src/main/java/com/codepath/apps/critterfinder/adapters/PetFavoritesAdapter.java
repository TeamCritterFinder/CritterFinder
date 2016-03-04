package com.codepath.apps.critterfinder.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.critterfinder.R;
import com.codepath.apps.critterfinder.models.PetModel;

import java.util.List;

/**
 * Created by carlybaja on 3/2/16.
 */
public class PetFavoritesAdapter extends RecyclerView.Adapter<PetFavoritesAdapter.ViewHolder> {

    AdapterView.OnItemClickListener mOnItemClickListener;

    private List<PetModel> mPets;

    // Pass in the contact array into the constructor
    public PetFavoritesAdapter(List<PetModel> petModels) {
        mPets = petModels;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView tvPetFavName;
        public TextView tvPetFavSex;
        public ImageView ivPetFavImage;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            tvPetFavName = (TextView) itemView.findViewById(R.id.petFavName);
            tvPetFavSex = (TextView) itemView.findViewById(R.id.petFavSex);
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
        tvSex.setText(petModel.getSex());

    }

    // Return the total count of items
    @Override
    public int getItemCount() {
        return mPets.size();
    }


}
