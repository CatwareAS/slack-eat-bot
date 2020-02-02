package com.catware.eatapp.model.ui;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Option {

    private String value;
    private Text text;

    public Option(String value, String text) {
        this.value = value;
        this.text = new Text(text);
    }

    public Option(String value, Text text) {
        this.value = value;
        this.text = text;
    }

}
