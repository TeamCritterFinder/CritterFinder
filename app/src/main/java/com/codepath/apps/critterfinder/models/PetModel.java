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
            JSONArray media = petJson.getJSONArray("media");
            if (media != null && media.length() > 0) {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
