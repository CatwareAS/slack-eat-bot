package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "urls")
public class Url {
    @DocumentId
    private String title;
    private String urlString;

    public Url() {
    }

    public Url(String title, String urlString) {
        this.title = title;
        this.urlString = urlString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrlString() {
        return urlString;
    }

    public void setUrlString(String urlString) {
        this.urlString = urlString;
    }

    @Override
    public String toString() {
        return "Url{" +
                "title='" + title + '\'' +
                ", url='" + urlString + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Url url1 = (Url) o;
        return Objects.equals(title, url1.title) &&
                Objects.equals(urlString, url1.urlString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, urlString);
    }
}
