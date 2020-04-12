package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.slack.service.SlackRestaurantsBookingService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestaurantBookingController {

    private final SlackRestaurantsBookingService slackRestaurantsBookingService;

    public RestaurantBookingController(SlackRestaurantsBookingService slackRestaurantsBookingService) {
        this.slackRestaurantsBookingService = slackRestaurantsBookingService;
    }

    @GetMapping("/remind-to-book-restaurant")
    public void remindToBookRestaurant() throws IOException {
        slackRestaurantsBookingService.remindUsersToApplyForFood();
    }

    @GetMapping("/propose-restaurant")
    public void proposeRestaurant() throws IOException {
        slackRestaurantsBookingService.proposeUsersRestaurant();
    }

    @GetMapping("/book-restaurant")
    public void bookRestaurant() throws IOException {
        slackRestaurantsBookingService.chooseRestaurant();
    }

}

