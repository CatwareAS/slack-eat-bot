package com.catware.eatapp.controller;

import com.catware.eatapp.model.RestaurantsSlackMessage;
import com.catware.eatapp.model.ui.Accessory;
import com.catware.eatapp.model.ui.Option;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slack/slash")
public class SlackController {

    @Autowired
    private RestaurantsService restaurantsService;

    @PostMapping(value = "/restaurants", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RestaurantsSlackMessage onReceiveSlashCommand(@RequestParam(value = "team_id", required = false) String teamId,
                                                         @RequestParam(value = "team_domain", required = false) String teamDomain,
                                                         @RequestParam(value = "channel_id", required = false) String channelId,
                                                         @RequestParam(value = "channel_name", required = false) String channelName,
                                                         @RequestParam(value = "user_id", required = false) String userId,
                                                         @RequestParam(value = "user_name", required = false) String userName,
                                                         @RequestParam(value = "command", required = false) String command,
                                                         @RequestParam(value = "text", required = false) String text,
                                                         @RequestParam(value = "response_url", required = false) String responseUrl) {
        RestaurantsSlackMessage message = new RestaurantsSlackMessage("Choose your restaurants");
        message.setResponseType("in_channel");
        Accessory accessory = new Accessory();
        accessory.setType("radio_buttons");
        accessory.setActionId("test_actin_id");
        accessory.setOptions(getRestaurantOptions());
        message.setAccessory(accessory);
        return message;
    }

    private List<Option> getRestaurantOptions() {
        return restaurantsService.getAllRestaurants().stream()
                .map(r -> new Option(r.getTitle(), r.getTitle()))
                .collect(Collectors.toList());
    }

}