package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.job.RestaurantsSynchronize;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class RestaurantsController {

    private final RestaurantsService restaurantsService;
    private final RestaurantsSynchronize restaurantsSynchronize;

    public RestaurantsController(RestaurantsService restaurantsService, RestaurantsSynchronize restaurantsSynchronize) {
        this.restaurantsService = restaurantsService;
        this.restaurantsSynchronize = restaurantsSynchronize;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        return ResponseEntity.ok(restaurantsService.getAllRestaurants());
    }

    @GetMapping("/categories")
    public ResponseEntity<Map<String, List<Restaurant>>> getAllCategories() {
        return ResponseEntity.ok(restaurantsService.getAllCategories());
    }

    @GetMapping("/refresh-restaurants")
    public ResponseEntity<String> refreshRestaurantsData() {
        restaurantsSynchronize.refreshRestaurants();
        return ResponseEntity.ok("Refreshed");
    }
}

