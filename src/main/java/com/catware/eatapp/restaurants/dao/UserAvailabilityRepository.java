package com.catware.eatapp.restaurants.dao;

import com.catware.eatapp.restaurants.model.UserAvailability;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAvailabilityRepository extends FirestoreReactiveRepository<UserAvailability> {
}
