package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.QuarantinedRestaurantRepository;
import com.catware.eatapp.restaurants.model.PreviouslyChosenRestaurant;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreviouslyChosenRestaurantsService {

    private static final int RESTAURANTS_MAX_COUNT = 5;

    private final QuarantinedRestaurantRepository quarantinedRestaurantRepository;

    public PreviouslyChosenRestaurantsService(QuarantinedRestaurantRepository quarantinedRestaurantRepository) {
        this.quarantinedRestaurantRepository = quarantinedRestaurantRepository;
    }

    public void addRestaurant(PreviouslyChosenRestaurant restaurant) {
        List<PreviouslyChosenRestaurant> allChosenRestaurants = getAllQuarantinedRestaurantsSortedById();

        restaurant.setId(getMaxId(allChosenRestaurants) + 1);

        allChosenRestaurants.add(restaurant);

        if (allChosenRestaurants.size() == RESTAURANTS_MAX_COUNT + 1) {
            allChosenRestaurants.remove(0);
        }
        quarantinedRestaurantRepository.deleteAll().block();
        quarantinedRestaurantRepository.saveAll(allChosenRestaurants).blockFirst();
    }

    public List<PreviouslyChosenRestaurant> getAllQuarantinedRestaurantsSortedById() {
        return quarantinedRestaurantRepository.findAll().toStream()
                .sorted(Comparator.comparing(PreviouslyChosenRestaurant::getId))
                .collect(Collectors.toList());
    }

    private long getMaxId(List<PreviouslyChosenRestaurant> restaurants) {
        if (restaurants.isEmpty()) {
            return 0L;
        }
        return restaurants.get(restaurants.size() - 1).getId();
    }
}
