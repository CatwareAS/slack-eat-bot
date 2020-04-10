package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.ArrayList;
import java.util.List;


@Document(collectionName = "user")
public class SlackUser {
    @DocumentId
    private String id;
    private List<String> excludeCuisineTypes;

    public SlackUser() {
    }

    public SlackUser(String id) {
        this.id = id;
        this.excludeCuisineTypes = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getExcludeCuisineTypes() {
        return excludeCuisineTypes;
    }

    public void setExcludeCuisineTypes(List<String> excludeCuisineTypes) {
        this.excludeCuisineTypes = excludeCuisineTypes;
    }

}
