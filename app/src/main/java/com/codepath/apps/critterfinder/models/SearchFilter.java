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
        FEMALE("F");

        private final String mName;

        Gender(String name) {
            mName = name;
        }
        public String toString() {
            return this.mName;
        }
    }

    public enum Size {
        SMALL("S"),
        MEDIUM("M"),
        LARGE("L"),
        XLARGE("XL");

        private final String mName;

        Size(String name) {
            mName = name;
        }
        public String toString() {
            return this.mName;
        }
    }

    Gender mGender;
    Size mSize;

    public SearchFilter() {
        mGender = Gender.MALE;
    }

    public Gender getGender() {
        return mGender;
    }

    public void setGender(Gender gender) {
        mGender = gender;
    }
}
