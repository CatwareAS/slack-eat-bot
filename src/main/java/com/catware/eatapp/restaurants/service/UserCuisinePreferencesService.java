package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.UserCuisinePreferencesRepository;
import com.catware.eatapp.restaurants.model.UserCuisinePreferences;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UserCuisinePreferencesService {

    private final UserCuisinePreferencesRepository userCuisinePreferencesRepository;

    public UserCuisinePreferencesService(UserCuisinePreferencesRepository userCuisinePreferencesRepository) {
        this.userCuisinePreferencesRepository = userCuisinePreferencesRepository;
    }

    public void saveUserExcludedCuisineTypes(String userId, List<String> excludedCuisineTypes) {
        userCuisinePreferencesRepository.save(new UserCuisinePreferences(userId, excludedCuisineTypes)).block();
    }

    public List<String> getUserExcludedCuisineTypes(String userId) {
        return userCuisinePreferencesRepository
                .findById(userId)
                .blockOptional()
                .map(UserCuisinePreferences::getExcludeCuisineTypes)
                .orElse(Collections.emptyList());
    }
}
