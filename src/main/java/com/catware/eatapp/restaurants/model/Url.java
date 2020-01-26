package com.catware.eatapp.restaurants.model;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cloud.gcp.data.firestore.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collectionName = "urls")
public class Url {
    @DocumentId
    private String title;
    private String url;
}
