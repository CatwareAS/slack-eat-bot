package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.model.Restaurant;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public interface ParseService {
    List<Restaurant> parsePage(String url) throws IOException;
}
