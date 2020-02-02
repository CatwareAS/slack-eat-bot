package com.catware.eatapp.model.ui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Text {

    private String text;
    private String type;

    public Text(String text) {
        this.text = text;
        this.type = "plain_text";
    }

    public Text(String text, String type) {
        this.text = text;
        this.type = type;
    }

}
