package com.catware.eatapp.slack.handler;

import com.catware.eatapp.slack.service.SlackRestaurantsBookingService;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RejectRestaurantActionHandler implements BlockActionHandler {

    private static final Logger log = LoggerFactory.getLogger(RejectRestaurantActionHandler.class);

    private final SlackRestaurantsBookingService slackRestaurantsBookingService;

    public RejectRestaurantActionHandler(SlackRestaurantsBookingService slackRestaurantsBookingService) {
        this.slackRestaurantsBookingService = slackRestaurantsBookingService;
    }

    @Override
    public Response apply(BlockActionRequest req, ActionContext ctx) throws IOException {
        slackRestaurantsBookingService.proposeUsersRestaurantAfterRejection();
        return ctx.ack();
    }
}
