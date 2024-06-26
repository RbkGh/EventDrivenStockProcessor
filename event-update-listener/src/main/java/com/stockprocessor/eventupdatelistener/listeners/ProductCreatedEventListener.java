package com.stockprocessor.eventupdatelistener.listeners;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.eventupdatelistener.db.EventDetail;
import com.stockprocessor.eventupdatelistener.dto.ProductDTO;
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
public class ProductCreatedEventListener {

    private final ObjectMapper objectMapper;
    private final EventDetailRepository eventDetailRepository;


    @KafkaListener(topics = "products.created")
    public void listens(final String incomingJSON) throws JsonProcessingException {
        log.info("Received Product: {}", incomingJSON);

        ProductDTO productDTO = readCreatedPayload(incomingJSON);

        eventDetailRepository.save(EventDetail.builder()
                .productID(productDTO.getId())
                .eventType("PRODUCT_CREATED")
                .eventBody(incomingJSON)
                .build());
    }

    private ProductDTO readCreatedPayload(String incomingJSON) throws JsonProcessingException {
        return objectMapper.readValue(incomingJSON, ProductDTO.class);
    }

}
