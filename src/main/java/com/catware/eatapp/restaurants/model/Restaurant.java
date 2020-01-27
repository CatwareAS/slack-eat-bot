package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.List;

@Data
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
}
