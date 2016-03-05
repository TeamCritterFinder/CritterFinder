package com.codepath.apps.critterfinder.services;

import android.util.Log;
import android.widget.ProgressBar;

import com.codepath.apps.critterfinder.PetFinderHttpClient;
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

    String zipCode;
    String animalType;
    String age;
    String size;
    String sex;
    public ArrayList<PetModel> petsList;
    PetFinderHttpClient client;
    PetSearchCallbackInterface callbackInterface;
    Integer currentPet = 0; // currently selected pet

    public PetSearch(PetSearchCallbackInterface callbackInterface) {
        client = new PetFinderHttpClient();
        petsList = new ArrayList<PetModel>();
        this.callbackInterface = callbackInterface;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    public void doPetSearch() {

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
        });
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

