package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.job.RestaurantsSynchronize;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.model.RestaurantName;
import com.catware.eatapp.restaurants.service.ChooseRestaurantService;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import com.catware.eatapp.slack.job.ChooseRestaurantJob;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class RestaurantsController {

    private final RestaurantsService restaurantsService;
    private final RestaurantsSynchronize restaurantsSynchronize;
    private final ChooseRestaurantService chooseRestaurantService;
    private final ChooseRestaurantJob chooseRestaurantJob;

    public RestaurantsController(RestaurantsService restaurantsService,
                                 RestaurantsSynchronize restaurantsSynchronize,
                                 ChooseRestaurantService chooseRestaurantService, ChooseRestaurantJob chooseRestaurantJob) {
        this.restaurantsService = restaurantsService;
        this.restaurantsSynchronize = restaurantsSynchronize;
        this.chooseRestaurantService = chooseRestaurantService;
        this.chooseRestaurantJob = chooseRestaurantJob;
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
    public ResponseEntity<List<RestaurantName>> getAllChosenRestaurants() {
        return ResponseEntity.ok(chooseRestaurantService.getAllChosenRestaurants());
    }

    @PostMapping("/choose-restaurant")
    public ResponseEntity<String> chooseRestaurant(@RequestBody RestaurantName restaurantName) {
        chooseRestaurantService.chooseRestaurant(restaurantName);
        return ResponseEntity.ok("Added");
    }

    @GetMapping("/notify")
    public ResponseEntity<String> notifyUser() throws IOException {
        chooseRestaurantJob.askUsersToConfirmRestaurant();
        return ResponseEntity.ok("Notified");
    }
}

