package com.catware.eatapp.slack.config;

import com.catware.eatapp.slack.handler.BookRestaurantCommandHandler;
import com.catware.eatapp.slack.handler.RejectRestaurantActionHandler;
import com.catware.eatapp.slack.handler.UserCuisinePreferencesActionHandler;
import com.catware.eatapp.slack.handler.UserCuisinePreferencesCommandHandler;
import com.catware.eatapp.slack.utils.DebugMiddleware;
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
    private final RejectRestaurantActionHandler rejectRestaurantActionHandler;
    private final UserCuisinePreferencesActionHandler userCuisinePreferencesActionHandler;

    private final DebugMiddleware debugMiddleware;

    @Value("${slack.signingSecret}")
    private String signingSecret;

    @Value("${slack.botToken}")
    private String botToken;

    public Config(BookRestaurantCommandHandler bookRestaurantCommandHandler,
                  DebugMiddleware debugMiddleware,
                  UserCuisinePreferencesCommandHandler userCuisinePreferencesCommandHandler,
                  RejectRestaurantActionHandler rejectRestaurantActionHandler,
                  UserCuisinePreferencesActionHandler userCuisinePreferencesActionHandler) {
        this.bookRestaurantCommandHandler = bookRestaurantCommandHandler;
        this.debugMiddleware = debugMiddleware;
        this.userCuisinePreferencesCommandHandler = userCuisinePreferencesCommandHandler;
        this.rejectRestaurantActionHandler = rejectRestaurantActionHandler;
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

        app.use(debugMiddleware);

        app.command(Commands.BOOK_RESTAURANT, bookRestaurantCommandHandler);
        app.command(Commands.SHOW_USER_CUISINES, userCuisinePreferencesCommandHandler);
        app.blockAction(Actions.REJECT_RESTAURANT, rejectRestaurantActionHandler);
        app.blockAction(Actions.SAVE_USER_CUISINE_PREFERENCES, userCuisinePreferencesActionHandler);

        return app;
    }
}
