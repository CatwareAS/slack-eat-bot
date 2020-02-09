package com.catware.eatapp.controller;

import com.catware.eatapp.model.RestaurantsSlackMessage;
import com.catware.eatapp.model.ui.Element;
import com.catware.eatapp.model.ui.Option;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import me.ramswaroop.jbot.core.slack.models.Attachment;
import me.ramswaroop.jbot.core.slack.models.RichMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/slack/slash")
public class SlackController {

    @Autowired
    private RestaurantsService restaurantsService;

    @Deprecated
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
        RestaurantsSlackMessage message = new RestaurantsSlackMessage();
        Element optionElement = new Element();
        optionElement.setType("radio_buttons");
        optionElement.setActionId("test_actin_id");
        optionElement.setOptions(getRestaurantOptions());
        message.getBlocks().add(optionElement);
        return message;
    }

    private List<Option> getRestaurantOptions() {
        return restaurantsService.getAllRestaurants().stream()
                .map(r -> new Option(r.getTitle(), r.getTitle()))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/cuisine", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage showAllCuisineTypes() {
        RichMessage richMessage = new RichMessage("Categories of restaurants from UBER EATS");
        Attachment[] cuisineArray = restaurantsService.getAllCategoriesPickMap().entrySet().stream()
                .map(entry -> entry.getKey() + ". " + entry.getValue())
                .map(this::createAttachment)
                .toArray(Attachment[]::new);
        richMessage.setAttachments(cuisineArray);
        return richMessage.encodedMessage();
    }

    @PostMapping(value = "/random-restaurants", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RichMessage getRandomRestaurants(@RequestParam(value = "text", required = false) String excludeCuisineNums) {
        Map<Integer, String> allCategoriesPickMap = restaurantsService.getAllCategoriesPickMap();
        List<String> excludeCuisineTypes = Arrays.stream(Optional.ofNullable(excludeCuisineNums).orElse("-1").split("\\s+"))
                .map(Integer::valueOf)
                .map(allCategoriesPickMap::get)
                .collect(Collectors.toList());
        RichMessage richMessage = new RichMessage("Random pick result (Excluded cuisines: "
                + String.join(", ", excludeCuisineTypes) + ")");
        Attachment[] restaurants = restaurantsService.getRandomRestaurants(excludeCuisineTypes).stream()
                .map(Restaurant::toString)
                .map(this::createAttachment)
                .toArray(Attachment[]::new);
        richMessage.setAttachments(restaurants);
        return richMessage.encodedMessage();
    }

    private Attachment createAttachment(String text) {
        Attachment attachment = new Attachment();
        attachment.setText(text);
        return attachment;
    }

}