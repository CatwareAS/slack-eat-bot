package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.RestaurantRepository;
import com.catware.eatapp.restaurants.model.Restaurant;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.Collator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
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

    @Cacheable("categories-name")
    public List<String> getAllCategoriesNames() {
        Collator uaCollator = Collator.getInstance(new Locale("uk", "UA"));
        uaCollator.setStrength(Collator.PRIMARY);
        return getAllCategories().keySet().stream()
                .sorted(uaCollator::compare)
                .collect(Collectors.toList());
    }

}
