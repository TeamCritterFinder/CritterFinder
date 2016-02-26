package com.codepath.apps.critterfinder.models;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by srichard on 2/26/16.
 */
public class PetModel implements Serializable {
    String penName;
    String imageUrl;
    ArrayList<PetModel> petList;

    public PetModel(JSONObject petJson) {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
