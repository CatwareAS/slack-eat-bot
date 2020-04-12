package com.catware.eatapp.restaurants.service;

import com.catware.eatapp.restaurants.model.Restaurant;
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
public class JSoupService implements ParseService {

    public static final String RESTAURANTS_SELECTOR = "main>div>div>div";


    public static final String RESTAURANT_HREF_SELECTOR = ">div>a";

    public static final String RESTAURANT_INFO_SELECTOR = RESTAURANT_HREF_SELECTOR + ">div>div>div";
    public static final String RESTAURANT_ARRIVAL_TIME_SELECTOR = RESTAURANT_INFO_SELECTOR + ">div>div>div>div";
    public static final String RESTAURANT_RATTING_AND_REVIEWS_COUNT_SELECTOR = RESTAURANT_INFO_SELECTOR + ">div>div>div";

    public static final String SLASH = "/";
    public static final String DOT = "•";
    public static final String OPEN_BRACKET = "(";
    public static final String CLOSE_BRACKET = ")";
    public static final String PLUS = "+";
    public static final String EMPTY_STRING = "";


    public List<Restaurant> parsePage(String stringUrl) throws IOException {
        URL url = new URL(stringUrl);
        Document doc = Jsoup.parse(url, 1000 * 30);
        Elements articles = doc.select(RESTAURANTS_SELECTOR);

        List<Restaurant> restaurants = new ArrayList<>();
        String prefixUrl = url.getProtocol() + "://" + url.getHost();

        for (Element article : articles) {
            restaurants.add(
                    new Restaurant(
                            extractTitle(article),
                            extractPriceLevel(article),
                            extractCuisineTypes(article),
                            extractArrivalTime(article),
                            extractRating(article),
                            extractReviewsCount(article),
                            extractImageUrl(prefixUrl, article),
                            extractUrl(prefixUrl, article)
                    ));
        }

        return restaurants;
    }

    private String extractTitle(Element article) {
        return article.select(RESTAURANT_INFO_SELECTOR).get(0).text().replace(SLASH, EMPTY_STRING).trim();
    }

    private String extractPriceLevel(Element article) {
        return article.select(RESTAURANT_INFO_SELECTOR).get(1).text().split(DOT)[0].trim();
    }

    private List<String> extractCuisineTypes(Element article) {
        List<String> priceLevelAndCuisineTypes = Arrays.asList(article.select(RESTAURANT_INFO_SELECTOR).get(1).text().split(DOT));
        priceLevelAndCuisineTypes = priceLevelAndCuisineTypes.subList(1, priceLevelAndCuisineTypes.size());
        return priceLevelAndCuisineTypes.stream().map(String::trim).collect(Collectors.toList());
    }

    private String extractArrivalTime(Element article) {
        return article.select(RESTAURANT_ARRIVAL_TIME_SELECTOR).text().trim();
    }

    private double extractRating(Element article) {
        String ratingAndReviewsCount = article.select(RESTAURANT_RATTING_AND_REVIEWS_COUNT_SELECTOR).get(1).text();
        String rating = ratingAndReviewsCount.substring(0, ratingAndReviewsCount.indexOf(OPEN_BRACKET));
        return Double.parseDouble(rating);
    }

    private int extractReviewsCount(Element article) {
        String ratingAndReviewsCount = article.select(RESTAURANT_RATTING_AND_REVIEWS_COUNT_SELECTOR).get(1).text();
        ratingAndReviewsCount = ratingAndReviewsCount.replace(PLUS, EMPTY_STRING);
        String reviewsCount = ratingAndReviewsCount.substring(ratingAndReviewsCount.indexOf(OPEN_BRACKET) + 1, ratingAndReviewsCount.indexOf(CLOSE_BRACKET));
        return Integer.parseInt(reviewsCount);
    }

    private String extractImageUrl(String prefixUrl, Element article) throws IOException {
        URL restaurantUrl = new URL(prefixUrl + article.select(RESTAURANT_HREF_SELECTOR).attr("href"));
        Document doc = Jsoup.parse(restaurantUrl, 1000 * 30);
        return doc.select(">html>body>div>div>main>div>div>figure>div>img").attr("src");
    }

    private String extractUrl(String prefixUrl, Element article) {
        return prefixUrl + article.select(RESTAURANT_HREF_SELECTOR).attr("href");
    }
}
