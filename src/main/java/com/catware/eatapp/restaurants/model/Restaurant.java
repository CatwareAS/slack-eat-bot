package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collectionName = "restaurants")
public class Restaurant {

    @DocumentId
    private String title;
    private String priceLevel;
    private List<String> cuisineTypes;
    private String arrivalTime;
    private double rating;
    private int reviewsCount;

    @Override
    public String toString() {
        return title + " ( rating: " + rating + ", " + "arrival time: " + arrivalTime + ", reviews count: " + reviewsCount + ")";
    }
}
