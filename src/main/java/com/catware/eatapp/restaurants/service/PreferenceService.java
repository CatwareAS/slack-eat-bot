package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.dao.SlackUserRepository;

import com.catware.eatapp.restaurants.model.SlackUser;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PreferenceService {

    private final SlackUserRepository slackUserRepository;
    private final RestaurantsService restaurantsService;

    public PreferenceService(SlackUserRepository slackUserRepository, RestaurantsService restaurantsService) {
        this.slackUserRepository = slackUserRepository;
        this.restaurantsService = restaurantsService;
    }

    public void savePreferences(String userId, Map<String, Boolean> preferenceValues) {
        Mono<SlackUser> user = slackUserRepository.findById(userId);
        List<String> excludeTypes = preferenceValues.keySet().stream()
                .filter(preferenceValues::get)
                .collect(Collectors.toList());
        SlackUser slackUser = user.blockOptional().orElse(new SlackUser(userId));
        slackUser.getExcludeCuisineTypes().clear();
        slackUser.getExcludeCuisineTypes().addAll(excludeTypes);
        slackUserRepository.save(slackUser).block();
    }

    public Map<String, Boolean> getPreferences(String userId) {
        Mono<SlackUser> user = slackUserRepository.findById(userId);
        SlackUser slackUser = user.blockOptional().orElse(new SlackUser(userId));
        List<String> excludeCategories = slackUser.getExcludeCuisineTypes();
        Set<String> allCategories = restaurantsService.getOnlyCategories();
        return allCategories.stream()
                .collect(Collectors.toMap(c -> c, excludeCategories::contains));

    }
}