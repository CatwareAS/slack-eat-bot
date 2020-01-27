package com.catware.eatapp.restaurants.job;

import com.catware.eatapp.restaurants.dao.RestaurantRepository;
import com.catware.eatapp.restaurants.dao.UrlRepository;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.model.Url;
import com.catware.eatapp.restaurants.service.ParseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RestaurantsSynchronize {

    private final ParseService jsoupParseService;
    private final UrlRepository urlRepository;
    private final RestaurantRepository restaurantRepository;

    public RestaurantsSynchronize(@Qualifier("JSoupService") ParseService jsoupParseService, UrlRepository urlRepository, RestaurantRepository restaurantRepository) {
        this.jsoupParseService = jsoupParseService;
        this.urlRepository = urlRepository;
        this.restaurantRepository = restaurantRepository;
    }

    @Scheduled(cron = "0 0 11 * * *")
    @CacheEvict(cacheNames = {"restaurants", "categories"})
    public void refreshRestaurants() {
        try {
            log.info("Refreshing restaurants started");
            Url url = urlRepository.findAll().blockLast();
            List<Restaurant> restaurants = jsoupParseService.parsePage(url.getUrl());
            restaurants.stream().map(Restaurant::toString).forEach(log::info);
            restaurantRepository.saveAll(restaurants).blockLast();
            log.info("Refreshing restaurants finished");
        } catch (Exception e) {
            log.error("Exception while refreshing restaurants list", e);
        }
    }
}
