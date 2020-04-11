package com.catware.eatapp.slack.handler;

import com.catware.eatapp.slack.service.SlackUserCuisinePreferencesService;
import com.slack.api.app_backend.interactive_components.payload.BlockActionPayload;
import com.slack.api.bolt.context.builtin.ActionContext;
import com.slack.api.bolt.handler.builtin.BlockActionHandler;
import com.slack.api.bolt.request.builtin.BlockActionRequest;
import com.slack.api.bolt.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserCuisinePreferencesActionHandler implements BlockActionHandler {

    private static final Logger log = LoggerFactory.getLogger(UserCuisinePreferencesActionHandler.class);

    private final SlackUserCuisinePreferencesService slackUserCuisinePreferencesService;

    public UserCuisinePreferencesActionHandler(SlackUserCuisinePreferencesService slackUserCuisinePreferencesService) {
        this.slackUserCuisinePreferencesService = slackUserCuisinePreferencesService;
    }

    @Override
    public Response apply(BlockActionRequest req, ActionContext ctx) {

        String userId = req.getPayload().getUser().getId();

        List<String> selectedCuisines = req.getPayload().getActions().get(0).getSelectedOptions().stream()
                .map(BlockActionPayload.Action.SelectedOption::getValue)
                .collect(Collectors.toList());

        slackUserCuisinePreferencesService.saveUserCuisinePreferences(userId, selectedCuisines);

        return ctx.ack();
    }
}
