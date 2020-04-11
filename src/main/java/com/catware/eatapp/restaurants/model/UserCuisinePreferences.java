package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.List;


@Document(collectionName = "user-cuisine-preferences")
public class UserCuisinePreferences {
    @DocumentId
    private String userId;
    private List<String> excludeCuisineTypes;

    public UserCuisinePreferences() {
    }

    public UserCuisinePreferences(String userId, List<String> excludeCuisineTypes) {
        this.userId = userId;
        this.excludeCuisineTypes = excludeCuisineTypes;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<String> getExcludeCuisineTypes() {
        return excludeCuisineTypes;
    }

    public void setExcludeCuisineTypes(List<String> excludeCuisineTypes) {
        this.excludeCuisineTypes = excludeCuisineTypes;
    }

}
