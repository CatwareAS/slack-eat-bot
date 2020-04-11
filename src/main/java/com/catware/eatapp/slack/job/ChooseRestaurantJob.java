package com.catware.eatapp.slack.job;

import com.catware.eatapp.slack.service.SlackRestaurantsBookingService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ChooseRestaurantJob {

    private final SlackRestaurantsBookingService slackRestaurantsBookingService;

    public ChooseRestaurantJob(SlackRestaurantsBookingService slackRestaurantsBookingService) {
        this.slackRestaurantsBookingService = slackRestaurantsBookingService;
    }

    @Scheduled(cron = "0 0 10 * * FRI")
    public void remindUsersToApplyForFood() throws IOException {
        slackRestaurantsBookingService.remindUsersToApplyForFood();
    }

    @Scheduled(cron = "0 0 11 * * FRI")
    public void proposeUsersRestaurant() throws IOException {
        slackRestaurantsBookingService.proposeUsersRestaurant();
    }

    @Scheduled(cron = "0 20 11 * * FRI")
    public void chooseRestaurant() throws IOException {
        slackRestaurantsBookingService.chooseRestaurant();
    }
}
