package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.model.Restaurant;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service("JSoupService")
@Slf4j
public class JSoupService implements ParseService {

    public static final String ARTICLE = "article";
    public static final String DIV_DIV_DIV_SPAN = "div>div>div>span";
    public static final String PLUS = "+";
    public static final String OPEN_BRACKET = "(";
    public static final String DIV_DIV = "div>div";
    public static final String CLOSE_BRACKET = ")";
    public static final String DOT = "•";
    public static final String EMPTY_STRING = "";

    public List<Restaurant> parsePage(String url) throws IOException {
        Document doc = Jsoup.parse(new URL(url), 1000 * 30);
        Elements articles = doc.getElementsByTag(ARTICLE);

        List<Restaurant> restaurants = new ArrayList<>();

        for (Element article : articles) {
            restaurants.add(
                    new Restaurant(
                            extractTitle(article),
                            extractPriceLevel(article),
                            extractCuisineTypes(article),
                            extractArrivalTime(article),
                            extractRating(article),
                            extractReviewsCount(article)
                    ));
        }

        return restaurants;
    }

    private int extractReviewsCount(Element article) {
        String ratingAndReviewsCount = article.select(DIV_DIV_DIV_SPAN).last().parent().text();
        ratingAndReviewsCount = ratingAndReviewsCount.replace(PLUS, EMPTY_STRING);
        String reviewsCount = ratingAndReviewsCount.substring(ratingAndReviewsCount.indexOf(OPEN_BRACKET) + 1, ratingAndReviewsCount.indexOf(CLOSE_BRACKET));
        return Integer.parseInt(reviewsCount);
    }

    private double extractRating(Element article) {
        String ratingAndReviewsCount = article.select(DIV_DIV_DIV_SPAN).last().parent().text();
        String rating = ratingAndReviewsCount.substring(0, ratingAndReviewsCount.indexOf(OPEN_BRACKET));
        return Double.parseDouble(rating);
    }

    private String extractArrivalTime(Element article) {
        return article.select(DIV_DIV_DIV_SPAN).first().text();
    }

    private List<String> extractCuisineTypes(Element article) {
        List<String> priceLevelAndCuisineTypes = Arrays.asList(article.select(DIV_DIV).get(3).text().split(DOT));
        priceLevelAndCuisineTypes = priceLevelAndCuisineTypes.subList(1, priceLevelAndCuisineTypes.size());
        return priceLevelAndCuisineTypes.stream().map(String::trim).collect(Collectors.toList());
    }

    private String extractPriceLevel(Element article) {
        return article.select(DIV_DIV).get(3).text().split(DOT)[0];
    }

    private String extractTitle(Element article) {
        String t = article.select(DIV_DIV).get(2).text();
        if (t.length() > 0 && t.contains(OPEN_BRACKET)) {
            return t.substring(0, t.indexOf(OPEN_BRACKET) - 1);
        }
        return t;
    }
}
