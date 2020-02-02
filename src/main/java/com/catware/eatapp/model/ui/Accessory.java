package com.catware.eatapp.model.ui;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Accessory {

    private String type;
    @JsonProperty("action_id")
    private String actionId;
    @JsonProperty("initial_optionObject")
    private Option initialOptionObject;
    private List<Option> options = new ArrayList<>();

}
