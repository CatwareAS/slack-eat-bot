package com.catware.eatapp.slack.job;

import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.model.RestaurantName;
import com.catware.eatapp.restaurants.service.ChooseRestaurantService;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import com.slack.api.Slack;
import com.slack.api.webhook.WebhookResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
public class ChooseRestaurantJob {

    private static final Logger log = LoggerFactory.getLogger(ChooseRestaurantJob.class);

    private final RestaurantsService restaurantsService;
    private final ChooseRestaurantService chooseRestaurantService;

    public ChooseRestaurantJob(RestaurantsService restaurantsService,
                               ChooseRestaurantService chooseRestaurantService) {
        this.restaurantsService = restaurantsService;
        this.chooseRestaurantService = chooseRestaurantService;
    }

    @Scheduled(cron = "0 10 11 * * FRI")
    public void askUsersToConfirmRestaurant() throws IOException {
        Slack slack = Slack.getInstance();

        List<Restaurant> allRestaurants = restaurantsService.getAllRestaurants();

        List<String> previouslyChosenRestaurants = chooseRestaurantService.getAllChosenRestaurants().stream()
                .map(RestaurantName::getRestaurantName)
                .collect(Collectors.toList());

        allRestaurants.removeIf(r -> previouslyChosenRestaurants.contains(r.getTitle()));

        WebhookResponse response = slack.send(
                "https://hooks.slack.com/services/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",
                payload(p -> p.text("Hello, World!"))
        );

        log.info(response.toString());

        //TODO: remove by category from user preferences;.
    }
}
