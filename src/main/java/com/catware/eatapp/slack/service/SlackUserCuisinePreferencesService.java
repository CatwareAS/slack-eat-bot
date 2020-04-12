package com.catware.eatapp.slack.service;

import com.catware.eatapp.restaurants.service.RestaurantsService;
import com.catware.eatapp.restaurants.service.UserCuisinePreferencesService;
import com.catware.eatapp.slack.config.Actions;
import com.slack.api.Slack;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.response.Response;
import com.slack.api.model.block.composition.OptionObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.*;
import static com.slack.api.model.block.element.BlockElements.multiStaticSelect;

@Service
public class SlackUserCuisinePreferencesService {

    private final Slack slack;
    private final RestaurantsService restaurantsService;
    private final UserCuisinePreferencesService userCuisinePreferencesService;
    @Value("${slack.channel.url}")
    private String channelUrl;

    public SlackUserCuisinePreferencesService(Slack slack,
                                              RestaurantsService restaurantsService,
                                              UserCuisinePreferencesService userCuisinePreferencesService) {
        this.slack = slack;
        this.restaurantsService = restaurantsService;
        this.userCuisinePreferencesService = userCuisinePreferencesService;
    }

    public Response showUserCuisinePreferences(String userId, SlashCommandContext ctx) {
        List<String> allRestaurantCategories = restaurantsService.getAllCategoriesNames();
        List<String> userExcludedCuisineTypes = userCuisinePreferencesService.getUserExcludedCuisineTypes(userId);

        return ctx.ack(asBlocks(
                section(section -> section
                        .text(markdownText(":knife_fork_plate: *Select cuisine categories that should be excluded*"))
                        .accessory(multiStaticSelect(staticSelect -> staticSelect
                                .actionId(Actions.SAVE_USER_CUISINE_PREFERENCES)
                                .options(toOptions(allRestaurantCategories))
                                .initialOptions(toOptions(userExcludedCuisineTypes))
                        ))
                )
        ));
    }

    public void saveUserCuisinePreferences(String userId, List<String> userCuisinePreferences) {
        userCuisinePreferencesService.saveUserExcludedCuisineTypes(userId, userCuisinePreferences);
    }

    private List<OptionObject> toOptions(List<String> strings) {
        //Very special logic from Slack
        if (strings.isEmpty()) {
            return null;
        }
        return strings.stream().map(s -> option(plainText(encode(s)), encode(s))).collect(Collectors.toList());
    }

    //FUCKING STUPID SLACK!!!
    private String encode(String s) {
        return new String(s.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
    }


}
