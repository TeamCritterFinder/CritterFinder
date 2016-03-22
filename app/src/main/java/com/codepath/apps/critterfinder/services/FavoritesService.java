package com.codepath.apps.critterfinder.services;

import com.codepath.apps.critterfinder.models.PetModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by smacgregor on 3/7/16.
 */
public class FavoritesService {

    private static FavoritesService mInstance = new FavoritesService();
    private List<PetModel> mFavorites;
    private Set<Long> mSkippedSet;

    public static FavoritesService getInstance() {
        return mInstance;
    }

    private FavoritesService() {
        mFavorites = new ArrayList<>();
        mSkippedSet = new HashSet<>();
    }

    /**
     * Mark a pet as a favorite
     * @param pet
     */
    public void addFavoritePet(PetModel pet) {
        mFavorites.add(0, pet);
    }

    /**
     * The list of pets the user has marked as favorites
     * @return
     */
    public List<PetModel> getFavoritePets() {
        return mFavorites;
    }

    /**
     * When a user is not interested in a particular pet
     * @param pet
     */
    public void skipPet(PetModel pet) {
        mSkippedSet.add(pet.getServerId());
    }

    /**
     * Remove all pets the user has previously decided they weren't interested in
     * from the passed in list of pets.
     * @param pets
     * @return
     */
    public List<PetModel> removeSkippedPets(List<PetModel> pets) {
        List<PetModel> filterdPetList = new ArrayList<>(pets.size());
        for (PetModel pet : pets) {
            if (!mSkippedSet.contains(pet.getServerId())) {
                filterdPetList.add(pet);
            }
        }
        return filterdPetList;
    }
}
