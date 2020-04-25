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

    @Scheduled(cron = "${slack.jobs.remind-to-apply-for-restaurant}", zone = "Europe/Kiev")
    public void remindUsersToApplyForFood() throws IOException {
        slackRestaurantsBookingService.remindUsersToApplyForFood();
    }

    @Scheduled(cron = "${slack.jobs.propose-restaurant}", zone = "Europe/Kiev")
    public void proposeUsersRestaurant() throws IOException {
        slackRestaurantsBookingService.proposeUsersRestaurant();
    }

    @Scheduled(cron = "${slack.jobs.message-about-chosen-restaurant}", zone = "Europe/Kiev")
    public void chooseRestaurant() throws IOException {
        slackRestaurantsBookingService.chooseRestaurant();
    }
}
