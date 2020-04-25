package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "previously-chosen-restaurants")
public class PreviouslyChosenRestaurant {

    @DocumentId
    private String restaurantName;

    private long id;

    public PreviouslyChosenRestaurant() {
    }

    public PreviouslyChosenRestaurant(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PreviouslyChosenRestaurant that = (PreviouslyChosenRestaurant) o;
        return Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName);
    }

}
