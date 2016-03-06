package com.codepath.apps.critterfinder.utils;

import android.text.TextUtils;

import com.codepath.apps.critterfinder.models.Breed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smacgregor on 3/6/16.
 */
public class SearchFilterHelpers {

    /**
     * Given a list of breeds and a set of indices, returns a sublist of breeds corresponding to the
     * indices
     * @param breeds
     * @param which
     * @return
     */
    public static List<Breed> generateSubsetFromList(List<Breed> breeds, Integer[] which) {
        ArrayList<Breed> newBreeds = new ArrayList<>();
        for (Integer index: which) {
            newBreeds.add(breeds.get(index));
        }
        return newBreeds;
    }

    /**
     * Convert a list of breeds into a string using the passed in delimeter
     * @param breeds
     * @param delimeter
     * @return
     */
    public static String generateStringForBreeds(List<Breed> breeds, String delimeter) {
        String displayString = "";
        if (breeds != null) {
            for (Breed breed : breeds) {
                if (!TextUtils.isEmpty(displayString)) {
                    displayString += delimeter;
                }
                displayString += breed.getName();
            }
        }
        return displayString;
    }
}
