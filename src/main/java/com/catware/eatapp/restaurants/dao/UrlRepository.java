package com.catware.eatapp.restaurants.dao;

import com.catware.eatapp.restaurants.model.Url;
import org.springframework.cloud.gcp.data.firestore.FirestoreReactiveRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends FirestoreReactiveRepository<Url> {
}
