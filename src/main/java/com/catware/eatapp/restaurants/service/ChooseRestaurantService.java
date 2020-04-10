package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.RestaurantNameRepository;
import com.catware.eatapp.restaurants.model.RestaurantName;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ChooseRestaurantService {

    private static final int RESTAURANTS_MAX_COUNT = 5;

    private final RestaurantNameRepository restaurantNameRepository;

    public ChooseRestaurantService(RestaurantNameRepository restaurantNameRepository) {
        this.restaurantNameRepository = restaurantNameRepository;
    }

    public void chooseRestaurant(RestaurantName restaurantName) {
        List<RestaurantName> allChosenRestaurants = getAllChosenRestaurants();
        allChosenRestaurants.add(restaurantName);
        if (allChosenRestaurants.size() == RESTAURANTS_MAX_COUNT + 1) {
            allChosenRestaurants.remove(0);
        }
        restaurantNameRepository.deleteAll().block();
        restaurantNameRepository.saveAll(allChosenRestaurants).blockFirst();
    }

    public List<RestaurantName> getAllChosenRestaurants() {
        return restaurantNameRepository.findAll().toStream().collect(Collectors.toList());
    }
}
