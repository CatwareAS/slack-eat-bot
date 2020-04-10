package com.catware.eatapp.restaurants.dao;

import com.catware.eatapp.restaurants.model.SlackUser;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;

public interface SlackUserRepository extends FirestoreReactiveRepository<SlackUser> {
}
