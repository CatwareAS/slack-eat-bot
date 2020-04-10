package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Data;
import org.springframework.cloud.gcp.data.firestore.Document;

@Document(collectionName = "restaurant-names")
@Data
public class RestaurantName {

    @DocumentId
    private String restaurantName;
}
