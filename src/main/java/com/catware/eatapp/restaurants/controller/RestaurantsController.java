package com.catware.eatapp.restaurants.controller;

import com.catware.eatapp.restaurants.dao.RestaurantRepository;
import com.catware.eatapp.restaurants.dao.UrlRepository;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.model.Url;
import com.catware.eatapp.restaurants.service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
public class RestaurantsController {

    private final ParseService jsoupParseService;
    private final RestaurantRepository restaurantRepository;
    private final UrlRepository urlRepository;

    public RestaurantsController(@Qualifier("JSoupService") ParseService jsoupParseService, RestaurantRepository restaurantRepository, UrlRepository urlRepository) {
        this.jsoupParseService = jsoupParseService;
        this.restaurantRepository = restaurantRepository;
        this.urlRepository = urlRepository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void refreshRestaurants() {
        try {
            log.info("Refreshing restaurants started");
            Url url = urlRepository.findAll().blockLast();
            List<Restaurant> restaurants = jsoupParseService.parsePage(url.getUrl());
            restaurantRepository.saveAll(restaurants).blockLast();
            log.info("Refreshing restaurants finished");
        } catch (Exception e) {
            log.error("Exception while refreshing restaurants list", e);
        }
    }
}
