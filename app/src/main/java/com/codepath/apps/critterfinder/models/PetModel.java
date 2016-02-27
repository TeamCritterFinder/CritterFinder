package com.codepath.apps.critterfinder.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srichard on 2/26/16.
 */
public class PetModel implements Serializable {
    String name;
    String imageUrl;
    String sex;
    private static String weirdNameSpace = "$t";

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSex() {
        return sex;
    }



    public PetModel(JSONObject petJson) {
        try {
            this.name = petJson.getJSONObject("name").getString(weirdNameSpace);
            this.sex = petJson.getJSONObject("sex").getString(weirdNameSpace);
            JSONObject photosObject = petJson.getJSONObject("media").getJSONObject("photos");
            if (photosObject != null) {
                JSONArray photosArray = photosObject.getJSONArray("photo");
                if (photosArray != null & photosArray.length() > 0) {
//                    JSONObject photoObject = (JSONObject)photosArray.get(0);
                    JSONObject photoObject = findObjectWithNameValuePair(photosArray,"@size","pn");
                    imageUrl = photoObject.getString(weirdNameSpace);
                    imageUrl.replace("\\/","/");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // look up a node in the array that contains a matching name value pair and return it
    public static JSONObject findObjectWithNameValuePair(JSONArray jsonArray,String name, String value) {
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentObject = null;
            try {
                currentObject = (JSONObject)jsonArray.get(i);
                String objectValue = currentObject.getString(name);
                if (objectValue.length() > 0) {
                    if (objectValue.compareTo(value) == 0)
                        return currentObject;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static ArrayList<PetModel> fromJSONArray(JSONArray array) {
        ArrayList<PetModel> results = new ArrayList<>();

        for (int x=0; x< array.length(); x++) {
            try {
                results.add(new PetModel(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return results;
    }
}
