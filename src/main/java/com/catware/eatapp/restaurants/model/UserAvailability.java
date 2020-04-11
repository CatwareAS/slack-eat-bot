package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.util.Objects;

@Document(collectionName = "user-availability")
public class UserAvailability {

    @DocumentId
    private String userId;

    public UserAvailability() {
    }

    public UserAvailability(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAvailability that = (UserAvailability) o;
        return Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId);
    }
}
