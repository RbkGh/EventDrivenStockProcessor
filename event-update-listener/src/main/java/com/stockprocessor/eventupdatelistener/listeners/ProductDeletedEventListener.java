package com.stockprocessor.eventupdatelistener.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.eventupdatelistener.db.EventDetail;
import com.stockprocessor.eventupdatelistener.dto.ProductDTO;
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
public class ProductDeletedEventListener {

    private final ObjectMapper objectMapper;
    private final EventDetailRepository eventDetailRepository;

    @KafkaListener(topics = "products.deleted")
    public void listensProductDeleted(final String incomingJSON) throws JsonProcessingException {
        log.info("Received DELETED Product: " + incomingJSON);

        ProductDTO productDTO =
                objectMapper.readValue(incomingJSON,ProductDTO.class);

        //store in mongo
        eventDetailRepository.save(EventDetail.builder()
                .productID(productDTO.getId())
                .eventType("PRODUCT_DELETED")
                .eventBody(incomingJSON)
                .build());
    }

}
