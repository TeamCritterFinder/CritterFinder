package com.codepath.apps.critterfinder.services;

import android.util.Log;

import com.codepath.apps.critterfinder.models.SearchFilter;
import com.codepath.apps.critterfinder.network.PetFinderHttpClient;
import com.codepath.apps.critterfinder.models.PetModel;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

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

    public ArrayList<PetModel> petsList;
    PetFinderHttpClient client;
    PetSearchCallbackInterface callbackInterface;
    Integer currentPet = 0; // currently selected pet

    public PetSearch(PetSearchCallbackInterface callbackInterface) {
        client = new PetFinderHttpClient();
        petsList = new ArrayList<PetModel>();
        this.callbackInterface = callbackInterface;
    }


    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void doPetSearch(SearchFilter searchFilter) {

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
                            petsList = PetModel.fromJSONArray(petsArray);
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

    public PetModel getCurrentPet() {
        return petsList.get(currentPet);
    }

    public PetModel getNextPet() {
        if (currentPet < petsList.size() - 1) {
            return petsList.get(++currentPet);
        } else {
            Log.d("PetSearch","Find More Pets");
            return null;
            // TO DO implement another search call
        }
    }
}

