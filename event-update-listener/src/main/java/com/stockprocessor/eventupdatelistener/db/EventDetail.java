package com.stockprocessor.eventupdatelistener.db;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collation = "eventdetail")
@Data
@Builder
public class EventDetail {

    @Id
    private String id;

    private String productID;

    private String eventType;

    private String eventBody;
}
