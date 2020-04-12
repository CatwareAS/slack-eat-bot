package com.catware.eatapp.slack.service;

import com.catware.eatapp.restaurants.model.QuarantinedRestaurant;
import com.catware.eatapp.restaurants.model.Restaurant;
import com.catware.eatapp.restaurants.service.QuarantinedRestaurantService;
import com.catware.eatapp.restaurants.service.RestaurantsService;
import com.catware.eatapp.restaurants.service.UserAvailabilityService;
import com.catware.eatapp.restaurants.service.UserCuisinePreferencesService;
import com.catware.eatapp.slack.config.Actions;
import com.catware.eatapp.slack.config.Commands;
import com.slack.api.Slack;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.slack.api.model.block.Blocks.*;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.composition.BlockCompositions.plainText;
import static com.slack.api.model.block.element.BlockElements.*;
import static com.slack.api.webhook.WebhookPayloads.payload;

@Service
public class SlackRestaurantsBookingService {

    private final RestaurantsService restaurantsService;
    private final QuarantinedRestaurantService quarantinedRestaurantService;
    private final Slack slack;
    private final UserAvailabilityService userAvailabilityService;
    private final UserCuisinePreferencesService userCuisinePreferencesService;
    @Value("${slack.channel.url}")
    private String channelUrl;

    public SlackRestaurantsBookingService(RestaurantsService restaurantsService,
                                          QuarantinedRestaurantService quarantinedRestaurantService,
                                          Slack slack, UserAvailabilityService userAvailabilityService,
                                          UserCuisinePreferencesService userCuisinePreferencesService) {
        this.restaurantsService = restaurantsService;
        this.quarantinedRestaurantService = quarantinedRestaurantService;
        this.slack = slack;
        this.userAvailabilityService = userAvailabilityService;
        this.userCuisinePreferencesService = userCuisinePreferencesService;
    }

    public void remindUsersToApplyForFood() throws IOException {
        slack.send(channelUrl, payload(p -> p
                .blocks(asBlocks(
                        section(section -> section.text(markdownText("*Remember to apply for food ordering*"))),
                        divider(),
                        section(section -> section.text(markdownText("You can do it any time. To apply - use command `" + Commands.BOOK_RESTAURANT + "`")))

                ))
        ));
    }

    public void proposeUsersRestaurant() throws IOException {
        List<Restaurant> allRestaurants = restaurantsService.getAllRestaurants();

        List<String> previouslyChosenRestaurants = quarantinedRestaurantService.getAllQuarantinedRestaurantsSortedById().stream()
                .map(QuarantinedRestaurant::getRestaurantName)
                .collect(Collectors.toList());

        allRestaurants.removeIf(r -> previouslyChosenRestaurants.contains(r.getTitle()));

        List<String> allRegisteredUsers = userAvailabilityService.getAllRegisteredUsers();
        Set<String> allIgnoredCuisineTypes = allRegisteredUsers.stream()
                .map(userCuisinePreferencesService::getUserExcludedCuisineTypes)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());

        //TODO: test me properly
        allRestaurants.removeIf(r -> !Collections.disjoint(allIgnoredCuisineTypes, r.getCuisineTypes()));

        Optional<Restaurant> restaurant = allRestaurants.stream()
                .max(Comparator.comparing(Restaurant::getRating));

        if (restaurant.isPresent()) {

            quarantinedRestaurantService.addRestaurant(new QuarantinedRestaurant(restaurant.get().getTitle()));

            slack.send(channelUrl, payload(p -> p
                    .blocks(asBlocks(
                            section(section -> section.text(
                                    markdownText(createMessage(restaurant.get())))
                                    .accessory(imageElement(
                                            image -> image.imageUrl(restaurant.get().getImageUrl()).altText(restaurant.get().getTitle())
                                    ))
                            ),
                            actions(actions -> actions
                                    .elements(asElements(
                                            button(b -> b
                                                    .text(plainText(pt -> pt.emoji(true).text("Propose new restaurant")))
                                                    .actionId(Actions.REJECT_RESTAURANT)
                                                    .style("danger")
                                            )
                                    ))
                            )
                    ))
            ));
        } else {
            sendMessage(channelUrl, "*All restaurants filtered out! No food for today!*");
        }
    }

    @NotNull
    private String createMessage(Restaurant r) {
        return "<" + r.getUrl() + "|*" + r.getTitle() + "*>\n" +
                r.getPriceLevel() + " | " + String.join(" | ", r.getCuisineTypes()) + "\n" +
                r.getArrivalTime() + " | " + r.getRating() + ":star: (" + r.getReviewsCount() + ")";
    }

    public void proposeUsersRestaurantAfterRejection() throws IOException {
        sendMessage(channelUrl, "*Rejected*");
        proposeUsersRestaurant();
    }

    private void sendMessage(String channelUrl, String message) throws IOException {
        slack.send(channelUrl, payload(p -> p
                .blocks(asBlocks(
                        section(section -> section.text(markdownText(message))),
                        divider()
                ))
        ));
    }

    public void chooseRestaurant() throws IOException {
        List<String> previouslyChosenRestaurants = quarantinedRestaurantService.getAllQuarantinedRestaurantsSortedById().stream()
                .map(QuarantinedRestaurant::getRestaurantName)
                .collect(Collectors.toList());

        String chosenRestaurant = previouslyChosenRestaurants.get(previouslyChosenRestaurants.size() - 1);

        userAvailabilityService.clearRegisteredUsers();

        slack.send(channelUrl, payload(p -> p
                .blocks(asBlocks(
                        section(section -> section.text(markdownText("Chosen restaurant: *" + chosenRestaurant + "*"))),
                        divider()
                ))
        ));
    }
}
