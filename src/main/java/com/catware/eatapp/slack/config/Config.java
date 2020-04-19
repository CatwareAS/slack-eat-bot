package com.catware.eatapp.slack.config;

import com.catware.eatapp.slack.handler.booking.BookRestaurantCommandHandler;
import com.catware.eatapp.slack.handler.booking.ProposeNewRestaurantActionHandler;
import com.catware.eatapp.slack.handler.userpreferences.UserCuisinePreferencesActionHandler;
import com.catware.eatapp.slack.handler.userpreferences.UserCuisinePreferencesCommandHandler;
import com.slack.api.bolt.App;
import com.slack.api.bolt.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final BookRestaurantCommandHandler bookRestaurantCommandHandler;
    private final UserCuisinePreferencesCommandHandler userCuisinePreferencesCommandHandler;
    private final ProposeNewRestaurantActionHandler proposeNewRestaurantActionHandler;
    private final UserCuisinePreferencesActionHandler userCuisinePreferencesActionHandler;

    @Value("${slack.signingSecret}")
    private String signingSecret;

    @Value("${slack.botToken}")
    private String botToken;

    public Config(BookRestaurantCommandHandler bookRestaurantCommandHandler,
                  UserCuisinePreferencesCommandHandler userCuisinePreferencesCommandHandler,
                  ProposeNewRestaurantActionHandler proposeNewRestaurantActionHandler,
                  UserCuisinePreferencesActionHandler userCuisinePreferencesActionHandler) {
        this.bookRestaurantCommandHandler = bookRestaurantCommandHandler;
        this.userCuisinePreferencesCommandHandler = userCuisinePreferencesCommandHandler;
        this.proposeNewRestaurantActionHandler = proposeNewRestaurantActionHandler;
        this.userCuisinePreferencesActionHandler = userCuisinePreferencesActionHandler;
    }

    @Bean
    public AppConfig appConfig() {
        AppConfig appConfig = new AppConfig();
        appConfig.setSigningSecret(signingSecret);
        appConfig.setSingleTeamBotToken(botToken);
        return appConfig;
    }

    @Bean
    public App app(@Autowired AppConfig appConfig) {
        App app = new App(appConfig);

        app.command(Commands.BOOK_RESTAURANT, bookRestaurantCommandHandler);
        app.command(Commands.SHOW_USER_CUISINES, userCuisinePreferencesCommandHandler);
        app.blockAction(Actions.REJECT_RESTAURANT, proposeNewRestaurantActionHandler);
        app.blockAction(Actions.SAVE_USER_CUISINE_PREFERENCES, userCuisinePreferencesActionHandler);

        return app;
    }
}
