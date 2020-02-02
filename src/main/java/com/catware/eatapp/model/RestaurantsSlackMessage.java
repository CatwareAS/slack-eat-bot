package com.catware.eatapp.model;

import com.catware.eatapp.model.ui.Element;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class RestaurantsSlackMessage {

    private String type = "modal";
    private Element title = new Element("Choose your destiny for Friday");
    private Element submit = new Element("Submit");
    private Element close = new Element("Close");
    private List<Element> blocks = new ArrayList<>();

}
