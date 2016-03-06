package com.codepath.apps.critterfinder.models;

import org.parceler.Parcel;

import java.util.List;

/**
 * Created by smacgregor on 3/3/16.
 */
@Parcel
public class SearchFilter {

    // TODO - should these enums be pulled outside of the search filter class
    // so they can be leveraged by our pet models?

    public enum Gender {
        MALE("M"),
        FEMALE("F"),
        ALL("");

        private final String mName;

        Gender(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return this.mName;
        }
    }

    public enum Size {
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L");

        private final String mName;

        Size(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return this.mName;
        }
    }

    public enum Species {
        DOG("dog"),
        CAT("cat");

        private final String mName;

        Species(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return this.mName;
        }
    }

    public enum Age {
        BABY,
        YOUNG,
        ADULT,
        SENIOR
    }

    Gender mGender;
    Size mSize;
    Species mSpecies;
    String mPostalCode;
    List<Breed> mBreeds;
    Age mAge;

    public SearchFilter() {
        mGender = Gender.ALL;
        mSpecies = Species.DOG;
    }

    public Gender getGender() {
        return mGender;
    }
    public void setGender(Gender gender) {
        mGender = gender;
    }

    public String getPostalCode() {
        return mPostalCode;
    }
    public void setPostalCode(String postalCode) {
        mPostalCode = postalCode;
    }

    public Species getSpecies() {
        return mSpecies;
    }
    public void setSpecies(Species species) {
        mSpecies = species;
    }

    public List<Breed> getBreeds() {
        return mBreeds;
    }
    public void setBreeds(List<Breed> breed) {
        mBreeds = breed;
    }

    public Age getAge() {
        return mAge;
    }
    public void setAge(Age age) {
        mAge = age;
    }

    public Size getSize() {
        return mSize;
    }
    public void setSize(Size size) {
        mSize = size;
    }
}
