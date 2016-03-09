package com.codepath.apps.critterfinder.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by srichard on 2/26/16.
 */
@Parcel
public class PetModel {
    String name;
    String imageUrl;
    String sex;
    String age;
    String size;
    String description;

    private static String weirdNameSpace = "$t";

    public PetModel() {}

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getSex() {
        return sex;
    }

    public String getSexFullName() {

        switch (sex) {
            case "M" : return "Male";
            case "F" : return "Female";
        }
        return "";
    }

    public String getDescription() {
        return description;
    }

    public String getAge(){
        return age;
    }

    public String getSize(){
        return size;
    }

   public PetModel(String name, String sex, String image){
       this.name = name;
       this.sex = sex;
       this.imageUrl = image;
   }

    public PetModel(JSONObject petJson) {
        try {
            this.name = petJson.getJSONObject("name").getString(weirdNameSpace);
            this.sex = petJson.getJSONObject("sex").getString(weirdNameSpace);
            this.age = petJson.getJSONObject("age").getString(weirdNameSpace);
            this.size = petJson.getJSONObject("size").getString(weirdNameSpace);
            this.description = petJson.getJSONObject("description").getString(weirdNameSpace);
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

    // converts the JSONArray to an ArrayList of pets appending to existing petList
    public static void fromJSONArray(ArrayList<PetModel> petList,JSONArray array) {
        if (petList == null)
            petList = new ArrayList<>();

        for (int x=0; x < array.length(); x++) {
            try {
                petList.add(new PetModel(array.getJSONObject(x)));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
