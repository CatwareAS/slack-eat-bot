package com.catware.eatapp.slack.handler.userpreferences;

import com.catware.eatapp.slack.service.SlackUserCuisinePreferencesService;
import com.slack.api.bolt.context.builtin.SlashCommandContext;
import com.slack.api.bolt.handler.builtin.SlashCommandHandler;
import com.slack.api.bolt.request.builtin.SlashCommandRequest;
import com.slack.api.bolt.response.Response;
import org.springframework.stereotype.Service;

@Service
public class UserCuisinePreferencesCommandHandler implements SlashCommandHandler {

    private final SlackUserCuisinePreferencesService slackUserCuisinePreferencesService;

    public UserCuisinePreferencesCommandHandler(SlackUserCuisinePreferencesService slackUserCuisinePreferencesService) {
        this.slackUserCuisinePreferencesService = slackUserCuisinePreferencesService;
    }

    @Override
    public Response apply(SlashCommandRequest req, SlashCommandContext ctx) {
        String userId = req.getPayload().getUserId();
        return slackUserCuisinePreferencesService.showUserCuisinePreferences(userId, ctx);
    }

}