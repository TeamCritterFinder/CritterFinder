package com.codepath.apps.critterfinder.models;

import org.parceler.Parcel;

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
        DOG,
        CAT
    }

    public enum Age {
        BABY,
        YOUNG,
        ADULT,
        SENIOR
    }

    // TODO - this should become a first level model populated by the
    // breeds end point.
    // A filter should work with an array of breeds
    public enum Breed {
        COTON("Coton Du Tulear"),
        KINGCHARLES("King Charles Cavalier"),
        HAVANESE("Havanese");

        private final String mName;

        Breed(String name) {
            mName = name;
        }

        @Override
        public String toString() {
            return this.mName;
        }
    }

    Gender mGender;
    Size mSize;
    Species mSpecies;
    String mPostalCode;
    Breed mBreed;
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

    public Breed getBreed() {
        return mBreed;
    }
    public void setBreed(Breed breed) {
        mBreed = breed;
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
