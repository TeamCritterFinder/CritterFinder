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
     * Given a list of type T and a set of indices returns a sublist of type T
     * containing just the selected indices
     *
     * @param list
     * @param which
     * @param <T>
     * @return
     */
    public static <T> List<T> generateSubsetFromList(List<T> list, Integer[] which) {
        ArrayList<T> sublist = new ArrayList<>();
        for (Integer index : which) {
            sublist.add(list.get(index));
        }
        return sublist;
    }

    /**
     * Convert a list of breeds into a string using the passed in delimeter
     *
     * @param list
     * @param delimiter
     * @return
     */
    public static <T> String generateStringForList(List<T> list, String delimiter) {
        String displayString = "";
        if (list != null) {
            for (T item : list) {
                if (!TextUtils.isEmpty(displayString)) {
                    displayString += delimiter;
                }
                displayString += item.toString();
            }
        }
        return displayString;
    }

    public static <T extends Enum<T>> Integer[] getIndicesForList(List<T> list) {
        Integer[] selectedIndices = null;
        if (list != null) {
            selectedIndices = new Integer[list.size()];
            for (int index = 0; index < list.size(); index++) {
                selectedIndices[index] = list.get(index).ordinal();
            }
        }
        return selectedIndices;
    }

    // Look up the breeds from the selected list and create an array mapping to the offset in the list of all the Breeds
    public static <T extends Enum<T>> Integer[] getIndicesForBreeds(List<Breed> list,List<Breed> allBreeds) {
        Integer[] selectedIndices = null;
        if (list != null) {
            selectedIndices = new Integer[list.size()];
            for (int index = 0; index < list.size(); index++) {
                Breed comparisonBreed = new Breed(list.get(index).toString());  // create a Breed to lookup in the list
                selectedIndices[index] = allBreeds.indexOf(comparisonBreed);    // find the offset in the list off breeds
            }
        }
        return selectedIndices;
    }
}
