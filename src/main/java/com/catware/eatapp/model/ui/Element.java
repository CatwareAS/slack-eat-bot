package com.catware.eatapp.model.ui;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Element {

    private String type;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String text;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("action_id")
    private String actionId;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("initial_optionObject")
    private Option initialOptionObject;
    private boolean emoji;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Option> options = new ArrayList<>();

    public Element() {
    }

    public Element(String text) {
        this.text = text;
        this.type = "plain_text";
        this.emoji = true;
    }

}
