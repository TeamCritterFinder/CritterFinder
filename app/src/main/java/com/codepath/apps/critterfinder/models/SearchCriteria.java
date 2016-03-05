package com.codepath.apps.critterfinder.models;

/**
 * Created by smacgregor on 3/4/16.
 */
public class SearchCriteria {

    public enum CriteriaType {
        LOCATION,
        GENDER,
        SPECIES,
        AGE,
        SIZE,
        BREEDS
    }

    public String title;
    public String value;
    public SearchCriteria.CriteriaType criteriaType;

    public SearchCriteria(CriteriaType criteriaType, String title, String value) {
        this.criteriaType = criteriaType;
        this.title = title;
        this.value = value;
    }
}
