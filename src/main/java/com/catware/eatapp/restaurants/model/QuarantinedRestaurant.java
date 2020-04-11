package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "quarantined-restaurants")
public class QuarantinedRestaurant {

    @DocumentId
    private String restaurantName;

    private long id;

    public QuarantinedRestaurant() {
    }

    public QuarantinedRestaurant(String restaurantName) {
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
        QuarantinedRestaurant that = (QuarantinedRestaurant) o;
        return Objects.equals(restaurantName, that.restaurantName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(restaurantName);
    }

}
