package com.codepath.apps.critterfinder.models;

import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

/**
 * Created by smacgregor on 3/5/16.
 */

@Parcel
public class Breed {

    @SerializedName("$t") String mName;

    public Breed() {}

    public Breed(String name) {
        mName = name;
    }

    public String getName() {
        return mName;
    }

    @Override
    public String toString() {
        return getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Breed other = (Breed) obj;
        if (getName().matches(other.getName()) )
            return true;
        else
            return false;
    }
}
