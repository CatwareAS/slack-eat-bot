package com.catware.eatapp.slack.handler;

import com.catware.eatapp.slack.service.SlackUserCuisinePreferencesService;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class UserCuisinePreferencesCommandHandler implements SlashCommandHandler {

    private static final Logger log = LoggerFactory.getLogger(UserCuisinePreferencesCommandHandler.class);

    private final SlackUserCuisinePreferencesService slackUserCuisinePreferencesService;

    public UserCuisinePreferencesCommandHandler(SlackUserCuisinePreferencesService slackUserCuisinePreferencesService) {
        this.slackUserCuisinePreferencesService = slackUserCuisinePreferencesService;
    }

    @Override
    public Response apply(SlashCommandRequest req, SlashCommandContext ctx) throws IOException {
        String userId = req.getPayload().getUserId();
        slackUserCuisinePreferencesService.showUserCuisinePreferences(userId);
        return ctx.ack();
    }

}