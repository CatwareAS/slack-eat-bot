package com.catware.eatapp.model;

import com.catware.eatapp.model.ui.Accessory;
import me.ramswaroop.jbot.core.slack.models.RichMessage;

public class RestaurantsSlackMessage extends RichMessage {

    private Accessory accessory;

    public RestaurantsSlackMessage() {
    }

    public RestaurantsSlackMessage(String text) {
        super(text);
    }

    public Accessory getAccessory() {
        return accessory;
    }

    public void setAccessory(Accessory accessory) {
        this.accessory = accessory;
    }

}
