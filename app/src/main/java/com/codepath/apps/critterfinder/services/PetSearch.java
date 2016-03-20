package com.codepath.apps.critterfinder.services;

import android.util.Log;

import com.codepath.apps.critterfinder.models.Breed;
import com.codepath.apps.critterfinder.models.PetModel;
import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.network.PetFinderHttpClient;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by srichard on 3/4/16.
 */
public class PetSearch {
    //define callback interface
    public interface PetSearchCallbackInterface {

        void onPetSearchSuccess(List<PetModel> pets);
        void onPetSearchError(String result);
    }

    private static String weirdNameSpace = "$t";
    private static PetSearch mInstance = new PetSearch();

    PetFinderHttpClient client;
    SearchFilter searchFilter;
    private long mOffsetForNextSearch;

    public static PetSearch getInstance() {
        return mInstance;
    }

    public PetSearch() {
        client = new PetFinderHttpClient();
        mOffsetForNextSearch = 0;
    }

    /**
     * A new search for pets that match the passed in searchFilter
     * @param searchFilter
     * @param callbackInterface
     */
    public void doPetSearch(SearchFilter searchFilter, final PetSearchCallbackInterface callbackInterface) {
        // clear search results
        this.searchFilter = searchFilter;
        mOffsetForNextSearch = 0;
        fetchPets(callbackInterface);
    }

    /**
     * Load more pets for the current search
     * @param callbackInterface
     */
    public void doLoadMorePets(final PetSearchCallbackInterface callbackInterface) {
        fetchPets(callbackInterface);
    }

    /**
     * Helper method to allow searching and loading pets to share the same code
     * @param callbackInterface
     */
    private void fetchPets(final PetSearchCallbackInterface callbackInterface) {
        client.findPetList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                String jsonString = json.toString();
                Log.d("DEBUG", "SUCCESS loading: " + jsonString);
                try {
                    JSONObject petFinderObject = json.getJSONObject("petfinder");
                    JSONObject offsetObject = petFinderObject.getJSONObject("lastOffset");
                    if (offsetObject != null) {
                        String offsetString = offsetObject.getString(weirdNameSpace);
                        if (offsetObject.length() > 0) {
                            mOffsetForNextSearch = Integer.parseInt(offsetString);
                        }
                    }

                    JSONObject petsJsonObject = petFinderObject.getJSONObject("pets");
                    if (petsJsonObject != null) {
                        JSONArray petsArray = petsJsonObject.getJSONArray("pet");
                        if (petsArray != null && petsArray.length() > 0) {
                            ArrayList<PetModel> petsList = new ArrayList<>();
                            PetModel.fromJSONArray(petsList, petsArray);
                            callbackInterface.onPetSearchSuccess(petsList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", "ERROR loading: " + errorResponse.toString());
                callbackInterface.onPetSearchError(errorResponse.toString());
            }
        }, searchFilter, mOffsetForNextSearch);
    }

    /**
     * Fetch a list of breeds for a particular animal
     * @param species
     * @param breedsReceivedCallback
     */
    public void fetchBreeds(final SearchFilter.Species species, final PetBreedsReceivedCallback breedsReceivedCallback) {
        client.getBreedList(species, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                breedsReceivedCallback.onBreedsFailed(species);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                List<Breed> breeds = null;
                if (responseString != null) {
                    breeds = parseBreedsFromJSON(responseString);
                }
                breedsReceivedCallback.onBreedsReceived(species, breeds);
            }
        });
    }

    // Private helper class to allow gson to extract breeds out of a deeply nested response
    private class BreedResponse {
        PetFinder petfinder;
        private class PetFinder {
            Breeds breeds;
            private class Breeds {
                List<Breed> breed;
            }
        }
        public List<Breed> getBreeds() { return petfinder.breeds.breed; }
    }

    private List<Breed> parseBreedsFromJSON(String response) {
        Gson gson = new GsonBuilder().
                setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).
                excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC).
                create();
        BreedResponse breedResponse = gson.fromJson(response, BreedResponse.class);
        return breedResponse.getBreeds();
    }

    /**
     * Callback for fetching breeds for a species
     */
    public interface PetBreedsReceivedCallback {
        void onBreedsReceived(SearchFilter.Species species, List<Breed> breeds);
        void onBreedsFailed(SearchFilter.Species species);
    }
}

