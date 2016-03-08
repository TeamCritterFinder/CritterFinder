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

        void onPetSearchSuccess(String result);
        void onPetSearchError(String result);
    }

    private static PetSearch mInstance = new PetSearch();

    public ArrayList<PetModel> petsList;
    PetFinderHttpClient client;
    Integer currentPet = 0; // currently selected pet
    private Integer pageBuffer = 4; // when to start loading
    SearchFilter searchFilter;

    public static PetSearch getInstance() {
        return mInstance;
    }

    public PetSearch() {
        client = new PetFinderHttpClient();
        petsList = new ArrayList<PetModel>();
    }

    public void doPetSearch(SearchFilter searchFilter, final PetSearchCallbackInterface callbackInterface) {
        this.searchFilter = searchFilter;
        client.findPetList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                String jsonString = json.toString();
                Log.d("DEBUG", "SUCCESS loading: " + jsonString);
                try {
                    JSONObject petsJsonObject = json.getJSONObject("petfinder").getJSONObject("pets");
                    if (petsJsonObject != null) {
                        petsList = new ArrayList<>();
                        JSONArray petsArray = petsJsonObject.getJSONArray("pet");
                        if (petsArray != null && petsArray.length() > 0) {
                            PetModel.fromJSONArray(petsList,petsArray);
                            callbackInterface.onPetSearchSuccess("SUCCESS");
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
        },searchFilter);
    }

    public void doLoadMorePets(SearchFilter searchFilter, final PetSearchCallbackInterface callbackInterface) {
        client.findPetList(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                String jsonString = json.toString();
                Log.d("DEBUG", "SUCCESS loading: " + jsonString);
                try {
                    JSONObject petsJsonObject = json.getJSONObject("petfinder").getJSONObject("pets");
                    if (petsJsonObject != null) {
                        JSONArray petsArray = petsJsonObject.getJSONArray("pet");
                        if (petsArray != null && petsArray.length() > 0) {
                            PetModel.fromJSONArray(petsList, petsArray);
                           // callbackInterface.onPetSearchSuccess("SUCCESS");
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
        },searchFilter);
    }

    public PetModel getCurrentPet() {
        return petsList.get(currentPet);
    }

    public PetModel getNextPet(final PetSearchCallbackInterface callbackInterface) {
        if (currentPet < petsList.size() - 1) {
            if (currentPet > petsList.size() - pageBuffer) {
                doLoadMorePets(searchFilter, callbackInterface);
            }
            return petsList.get(++currentPet);
        } else {
            Log.d("PetSearch","Find More Pets");
            return null;
            // TO DO implement another search call
        }
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

