package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.gcp.data.firestore.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collectionName = "user")
public class SlackUser {
    @DocumentId
    private String userId;
    private List<String> excludeCuisineTypes;
    private LocalDateTime lastUpdatedPreferences;

    public SlackUser(String userId) {
        this.userId = userId;
        this.excludeCuisineTypes = new ArrayList<>();
    }
}
