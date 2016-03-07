package com.codepath.apps.critterfinder.network;

import com.codepath.apps.critterfinder.models.SearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

/**
 * Created by srichard on 2/24/16.
 */
public class PetFinderHttpClient  {
    private static PetFinderHttpClient mInstance = null;
    AsyncHttpClient client = null;
    public static final String REST_URL = "http://api.petfinder.com"; // PetFinder, base API URL
    public static final String REST_CONSUMER_KEY = "7afaf97ae32a304d684d620381a63e3b";       // PetFinder
    public static final String REST_CONSUMER_SECRET = "f83cf7247c48af6df08bb61064ba01ab"; 	// Petfinder Secret

//    public static PetFinderHttpClient getInstance(){
//        if(mInstance == null)
//        {
//            mInstance = new PetFinderHttpClient();
//        }
//        return mInstance;
//    }

    public PetFinderHttpClient() {
        client = new AsyncHttpClient();
    }

    // PetFinder get list of pets
    public void findPetList(JsonHttpResponseHandler handler,SearchFilter searchFilter) {
        String apiUrl = getApiUrl("pet.find");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("format", "json");
		params.put("count", 25);
        params.put("key", REST_CONSUMER_KEY);
        if (searchFilter.getPostalCode() != null)
            params.put("location", searchFilter.getPostalCode());
        else    // location is a REQUIRED field
            params.put("location","94140");
        if (searchFilter.getSpecies() != null)
            params.put("animal", searchFilter.getSpecies());
        List<SearchFilter.Age> ages = searchFilter.getAges();
        if (ages != null) {
            for (int x = 0; x < ages.size(); x++) {
                params.add("age", ages.get(x).toString());
            }
        }
        if (searchFilter.getGender() != null && (searchFilter.getGender().toString().length() > 0))
            params.put("sex",searchFilter.getGender());
        List<SearchFilter.Size> sizes = searchFilter.getSizes();
        if (sizes != null) {
            for (int x = 0; x < sizes.size(); x++) {
                params.add("size", sizes.get(x).toString());
            }
        }
        client.get(apiUrl, params, handler);
    }

    /**
     * Return a list of breeds for a particular species
     * @param species
     * @param handler
     */
    public void getBreedList(SearchFilter.Species species, TextHttpResponseHandler handler) {
        String apiUrl = getApiUrl("breed.list");

        RequestParams params = new RequestParams();
        params.put("format", "json");
        params.put("animal", species.toString());
        params.put("key", REST_CONSUMER_KEY);

        client.get(apiUrl, params, handler);
    }

    private String getApiUrl(String appendToUrl) {
        String newUrl = REST_URL;
        newUrl = newUrl.concat("/" + appendToUrl);
        return newUrl;
    }
}
