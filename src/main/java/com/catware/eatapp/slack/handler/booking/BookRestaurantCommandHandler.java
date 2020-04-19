package com.catware.eatapp.slack.handler.booking;

import com.catware.eatapp.restaurants.service.UserAvailabilityService;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

import static com.slack.api.model.block.Blocks.asBlocks;
import static com.slack.api.model.block.Blocks.section;
import static com.slack.api.model.block.composition.BlockCompositions.markdownText;

@Service
public class BookRestaurantCommandHandler implements SlashCommandHandler {

    private final UserAvailabilityService userAvailabilityService;

    public BookRestaurantCommandHandler(UserAvailabilityService userAvailabilityService) {
        this.userAvailabilityService = userAvailabilityService;
    }

    @Override
    public Response apply(SlashCommandRequest slashCommandRequest, SlashCommandContext context) throws UnsupportedEncodingException {

        String userId = slashCommandRequest.getPayload().getUserId();
        userAvailabilityService.registerUser(userId);

        return context.ack(asBlocks(section(section -> section.text(markdownText(":wave: You are registered for next restaurant order")))));
    }
}
