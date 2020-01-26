package com.catware.eatapp.restaurants.dao;

import com.catware.eatapp.restaurants.model.Restaurant;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository extends FirestoreReactiveRepository<Restaurant> {
}
