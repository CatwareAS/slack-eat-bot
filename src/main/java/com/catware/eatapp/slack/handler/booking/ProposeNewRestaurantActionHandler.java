package com.catware.eatapp.slack.handler.booking;

import com.catware.eatapp.restaurants.service.UserAvailabilityService;
import com.catware.eatapp.slack.config.Commands;
import com.catware.eatapp.slack.service.SlackRestaurantsBookingService;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;
import static com.slack.api.model.block.element.BlockElements.imageElement;

@Service
public class ProposeNewRestaurantActionHandler implements BlockActionHandler {

    private final SlackRestaurantsBookingService slackRestaurantsBookingService;
    private final UserAvailabilityService userAvailabilityService;

    @Value("${slack.misc.images.no-no-no}")
    private String noAccessImageUrl;

    public ProposeNewRestaurantActionHandler(SlackRestaurantsBookingService slackRestaurantsBookingService,
                                             UserAvailabilityService userAvailabilityService) {
        this.slackRestaurantsBookingService = slackRestaurantsBookingService;
        this.userAvailabilityService = userAvailabilityService;
    }

    @Override
    public Response apply(BlockActionRequest req, ActionContext ctx) throws IOException {
        String userId = req.getPayload().getUser().getId();
        if (userAvailabilityService.getAllRegisteredUsers().contains(userId)) {
            slackRestaurantsBookingService.proposeUsersRestaurantAfterRejection();
            return ctx.ack();
        }
        ctx.respond(ab -> ab.blocks(
                asBlocks(
                        section(section -> section.text(
                                markdownText(
                                        "*Only users who applied for restaurant booking can choose restaurant*\n" +
                                                "To apply for restaurant booking use command `" + Commands.BOOK_RESTAURANT + "`"
                                ))
                                .accessory(imageElement(
                                        image -> image
                                                .imageUrl(noAccessImageUrl)
                                                .altText("No No No")
                                ))
                        )
                )
                )
        );
        return ctx.ack();
    }
}
