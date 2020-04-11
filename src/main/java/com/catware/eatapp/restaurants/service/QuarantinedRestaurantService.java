package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.QuarantinedRestaurantRepository;
import com.catware.eatapp.restaurants.model.QuarantinedRestaurant;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuarantinedRestaurantService {

    private static final int RESTAURANTS_MAX_COUNT = 5;

    private final QuarantinedRestaurantRepository quarantinedRestaurantRepository;

    public QuarantinedRestaurantService(QuarantinedRestaurantRepository quarantinedRestaurantRepository) {
        this.quarantinedRestaurantRepository = quarantinedRestaurantRepository;
    }

    public void addRestaurant(QuarantinedRestaurant quarantinedRestaurant) {
        List<QuarantinedRestaurant> allChosenRestaurants = getAllQuarantinedRestaurantsSortedById();

        quarantinedRestaurant.setId(getMaxId(allChosenRestaurants) + 1);

        allChosenRestaurants.add(quarantinedRestaurant);

        if (allChosenRestaurants.size() == RESTAURANTS_MAX_COUNT + 1) {
            allChosenRestaurants.remove(0);
        }
        quarantinedRestaurantRepository.deleteAll().block();
        quarantinedRestaurantRepository.saveAll(allChosenRestaurants).blockFirst();
    }

    public List<QuarantinedRestaurant> getAllQuarantinedRestaurantsSortedById() {
        return quarantinedRestaurantRepository.findAll().toStream()
                .sorted(Comparator.comparing(QuarantinedRestaurant::getId))
                .collect(Collectors.toList());
    }

    private long getMaxId(List<QuarantinedRestaurant> restaurants) {
        if (restaurants.isEmpty()) {
            return 0L;
        }
        return restaurants.get(restaurants.size() - 1).getId();
    }
}
