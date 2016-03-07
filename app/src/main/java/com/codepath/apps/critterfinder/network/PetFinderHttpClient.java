package com.codepath.apps.critterfinder.network;

import com.codepath.apps.critterfinder.models.SearchFilter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

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
        if (searchFilter.getAge() != null)
            params.put("age",searchFilter.getAge());
        if (searchFilter.getGender() != null && (searchFilter.getGender().toString().length() > 0))
            params.put("sex",searchFilter.getGender());
        if (searchFilter.getSize() != null)
            params.put("size",searchFilter.getSize());
        client.get(apiUrl, params, handler);
    }

    private String getApiUrl(String appendToUrl) {
        String newUrl = REST_URL;
        newUrl = newUrl.concat("/" + appendToUrl);
        return newUrl;
    }
}
