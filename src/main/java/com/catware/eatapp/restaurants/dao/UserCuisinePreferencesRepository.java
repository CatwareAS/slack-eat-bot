package com.catware.eatapp.restaurants.dao;

import com.catware.eatapp.restaurants.model.UserCuisinePreferences;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCuisinePreferencesRepository extends FirestoreReactiveRepository<UserCuisinePreferences> {
}
