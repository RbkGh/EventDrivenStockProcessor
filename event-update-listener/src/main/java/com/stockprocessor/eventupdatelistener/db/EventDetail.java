package com.stockprocessor.eventupdatelistener.db;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "eventdetail")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventDetail {

    @Id
    private String id;

    private String productID;

    private String eventType;

    private String eventBody;
}
