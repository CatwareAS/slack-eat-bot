package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.job.RestaurantsSynchronize;
import com.catware.eatapp.restaurants.model.PreviouslyChosenRestaurant;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.service.PreviouslyChosenRestaurantsService;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class RestaurantsController {

    private final RestaurantsService restaurantsService;
    private final RestaurantsSynchronize restaurantsSynchronize;
    private final PreviouslyChosenRestaurantsService previouslyChosenRestaurantsService;

    public RestaurantsController(RestaurantsService restaurantsService,
                                 RestaurantsSynchronize restaurantsSynchronize,
                                 PreviouslyChosenRestaurantsService previouslyChosenRestaurantsService) {
        this.restaurantsService = restaurantsService;
        this.restaurantsSynchronize = restaurantsSynchronize;
        this.previouslyChosenRestaurantsService = previouslyChosenRestaurantsService;
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
    public ResponseEntity<String> chooseRestaurant() {
        restaurantsSynchronize.refreshRestaurants();
        return ResponseEntity.ok("Refreshed");
    }

    @GetMapping("/chosen-restaurants")
    public ResponseEntity<List<PreviouslyChosenRestaurant>> getAllChosenRestaurants() {
        return ResponseEntity.ok(previouslyChosenRestaurantsService.getAllQuarantinedRestaurantsSortedById());
    }

}

