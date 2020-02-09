package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.RestaurantRepository;
import com.catware.eatapp.restaurants.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RestaurantsService {

    private static final int RESTAURANTS_RANDOM_AMOUNT = 4;

    private final RestaurantRepository restaurantRepository;

    public RestaurantsService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    @Cacheable("restaurants")
    public List<Restaurant> getAllRestaurants() {
        return this.restaurantRepository.findAll().toStream().collect(Collectors.toList());
    }

    @Cacheable("categories")
    public Map<String, List<Restaurant>> getAllCategories() {
        List<Restaurant> restaurants = getAllRestaurants();
        return restaurants.stream()
                .map(
                        r -> r.getCuisineTypes().stream().collect(Collectors.toMap(c -> c, c -> r))
                )
                .flatMap(map -> map.entrySet().stream())
                .collect(
                        Collectors.groupingBy(
                                Map.Entry::getKey,
                                Collectors.mapping(Map.Entry::getValue, Collectors.toList())
                        )
                );
    }

    @Cacheable("categories-map")
    public Map<Integer, String> getAllCategoriesPickMap() {
        final AtomicInteger counter = new AtomicInteger();
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        uaCollator.setStrength(Collator.PRIMARY);
        return getAllCategories().keySet().stream()
                .sorted(uaCollator::compare)
                .collect(Collectors.toMap(v -> counter.incrementAndGet(),
                        v -> v, (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    public List<Restaurant> getRandomRestaurants(List<String> excludeCuisineTypes) {
        List<Restaurant> restaurants = getAllRestaurants().stream()
                .filter(r -> !isExcludedRestaurant(r, excludeCuisineTypes))
                .collect(Collectors.toList());
        Collections.shuffle(restaurants);
        return restaurants.subList(0, RESTAURANTS_RANDOM_AMOUNT).stream()
                .sorted(Comparator.comparing(Restaurant::getRating).reversed())
                .collect(Collectors.toList());
    }

    private boolean isExcludedRestaurant(Restaurant restaurant, List<String> excludeCuisineTypes) {
        return restaurant.getCuisineTypes().stream().anyMatch(excludeCuisineTypes::contains);
    }

}
