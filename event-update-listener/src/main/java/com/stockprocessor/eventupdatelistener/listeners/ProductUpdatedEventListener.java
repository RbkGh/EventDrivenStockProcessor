package com.stockprocessor.eventupdatelistener.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.eventupdatelistener.db.EventDetail;
import com.stockprocessor.eventupdatelistener.dto.ProductUpdatedPublisherDTO;
import com.stockprocessor.eventupdatelistener.repository.EventDetailRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Profile("production")
@Slf4j
public class ProductUpdatedEventListener {

    private final ObjectMapper objectMapper;
    private final EventDetailRepository eventDetailRepository;

    @KafkaListener(topics = "products.updated")
    public void listensProductUpdates(final String incomingJSON) throws JsonProcessingException {
        log.info("Received UPDATED Product: "+ incomingJSON);

        ProductUpdatedPublisherDTO productUpdatedPublisherDTO =
                objectMapper.readValue(incomingJSON,ProductUpdatedPublisherDTO.class);

        //store in mongo
        eventDetailRepository.save(EventDetail.builder()
                .productID(productUpdatedPublisherDTO.getGetProductDTOUpdated().getId())
                .eventType("PRODUCT_UPDATED")
                .eventBody(incomingJSON)
                .build());
    }

}
