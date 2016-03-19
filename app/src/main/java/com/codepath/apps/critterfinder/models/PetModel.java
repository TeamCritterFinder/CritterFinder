package com.codepath.apps.critterfinder.models;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by srichard on 2/26/16.
 */
@Parcel
public class PetModel {
    String name;
    String imageUrl;        // the image used for the main Tinder view
    List<String> detailImageUrls;   // list of all images used for the details View
    String sex;
    String age;
    String size;
    String description;
    String contactName;
    String contactPhone;
    String contactEmail;
    String contactState;
    String contactCity;
    String contactZip;
    String contactAdress1;
    String contactAdress2;
    List<Breed> breeds;   // list of all the breeds the animal may be part of
    long serverId;

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

    // list of all images used for the details View
    public List<String> getDetailImageUrls() {
        return detailImageUrls;
    }

    public String getSexFullName() {

        switch (sex) {
            case "M" : return "Male";
            case "F" : return "Female";
        }
        return "";
    }

    public String getBreedFullName() {
        String breedName = "";
       for (int x=0;x<breeds.size();x++) {
           if (x > 0)
               breedName += ", ";
           breedName += breeds.get(x);
       }
        return breedName;
    }

    public String getSizeSexAge() {
        String sizeSexAge = getSizeFullName() + " " + getSexFullName() + " " + age;
        return sizeSexAge;
    }

    public String getDescription() {
        return description;
    }

    public long getServerId() {
        return serverId;
    }

    public String getAge(){
        return age;
    }

    public String getSize(){
        return size;
    }

    public String getSizeFullName(){
        switch (size){
            case "S": return "Small";
            case "M": return "Medium";
            case "L": return "large";
        }
        return "";
    }

    public String getContactName() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public String getContactEmail(){
        return  contactEmail;
    }

    public String getContactState() {
        return contactState;
    }

    public String getContactCity() {
        return contactCity;
    }

    public String getContactZip() {
        return contactZip;
    }

    public String getContactAdress1() {
        return contactAdress1;
    }

    public String getcontactAdress2() {
        return contactAdress2;
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
            this.serverId = petJson.getJSONObject("id").getLong(weirdNameSpace);
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
                detailImageUrls = findUrlsToImages(photosArray,"@size","pn");
            }

            if (petJson.getJSONObject("contact").has("name")){
                this.contactName = petJson.getJSONObject("contact").getJSONObject("name").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("contact").has("phone")){
                this.contactPhone = petJson.getJSONObject("contact").getJSONObject("phone").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("contact").has("email")) {
                this.contactEmail = petJson.getJSONObject("contact").getJSONObject("email").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("contact").has("state")) {
                this.contactState = petJson.getJSONObject("contact").getJSONObject("state").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("contact").has("city")){
                this.contactCity = petJson.getJSONObject("contact").getJSONObject("city").getString(weirdNameSpace);
            }
            if(petJson.getJSONObject("contact").has("zip")) {
                this.contactZip = petJson.getJSONObject("contact").getJSONObject("zip").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("breeds").has("breed")) {
                try {
                    JSONObject jsonObject = petJson.getJSONObject("breeds").getJSONObject("breed");
                    if (jsonObject instanceof JSONObject) {
                        String breedName = jsonObject.getString(weirdNameSpace);
                        this.breeds = new ArrayList<Breed>();
                        this.breeds.add(new Breed(breedName));
                        Log.d("PetModel", "JSONObject breed");
                    } else {
                        Log.d("PetModel", "NOT a JSONObject");
                    }
                } catch (Exception e) {
                    JSONArray jsonArray = petJson.getJSONObject("breeds").getJSONArray("breed");
                    if (jsonArray instanceof JSONArray) {
                        Log.d("PetModel", "Is a JSONArray");
                        this.breeds = new ArrayList<Breed>();
                        for (int x=0; x< jsonArray.length();x++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(x);
                            if (jsonObject != null && jsonObject.has(weirdNameSpace)) {
                                this.breeds.add(new Breed(jsonObject.getString(weirdNameSpace)));
                            }
                        }
                    }
                }
            }
            if (petJson.getJSONObject("contact").has("address1")){
                this.contactAdress1 = petJson.getJSONObject("contact").getJSONObject("address1").getString(weirdNameSpace);
            }
            if (petJson.getJSONObject("contact").has("address2")) {
                this.contactAdress2 = petJson.getJSONObject("contact").getJSONObject("address2").getString(weirdNameSpace);
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

    // look up all the images that have property name with specified size
    public static List<String> findUrlsToImages(JSONArray jsonArray,String name, String size) {
        List<String> matchingObjects = null;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject currentObject = null;
            try {
                currentObject = (JSONObject)jsonArray.get(i);
                String objectValue = currentObject.getString(name);
                if (objectValue.length() > 0) {
                    if (objectValue.compareTo(size) == 0) {
                        String imageUrl = currentObject.getString(weirdNameSpace);
                        imageUrl.replace("\\/","/");
                        if (matchingObjects == null) {
                            matchingObjects = new ArrayList<String>();
                        }
                        matchingObjects.add(imageUrl);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return matchingObjects;
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
