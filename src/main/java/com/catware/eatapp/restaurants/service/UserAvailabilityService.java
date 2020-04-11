package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.UserAvailabilityRepository;
import com.catware.eatapp.restaurants.model.UserAvailability;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserAvailabilityService {

    private final UserAvailabilityRepository userAvailabilityRepository;

    public UserAvailabilityService(UserAvailabilityRepository userAvailabilityRepository) {
        this.userAvailabilityRepository = userAvailabilityRepository;
    }

    public void registerUser(String userId) {
        this.userAvailabilityRepository.save(new UserAvailability(userId)).block();
    }

    public List<String> getAllRegisteredUsers() {
        return this.userAvailabilityRepository.findAll().toStream().map(UserAvailability::getUserId).collect(Collectors.toList());
    }

    public void clearRegisteredUsers() {
        this.userAvailabilityRepository.deleteAll().block();
    }
}
