package com.stockprocessor.stockprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.stockprocessor.config.KafkaConfigProps;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


/**
 * Publishes product to a Kafka topic.
 */
@Service
@Slf4j
public class ProductCreatedPublisherService implements ProductCreatedPublisher {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    public ProductCreatedPublisherService(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate, KafkaConfigProps kafkaConfigProps) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
        this.kafkaConfigProps = kafkaConfigProps;
    }

    @Override
    public void publish(ProductDTO productDTO) {
        try {
            final String payload = objectMapper.writeValueAsString(productDTO);
            kafkaTemplate.send(kafkaConfigProps.getCreatedTopic(), payload);
            log.info("$$$$$$$$$$$$$$$$$$ $$$$$$$$$$ product created and published to kafka topic: {}, payload: {}", kafkaConfigProps.getCreatedTopic(), payload);
        } catch (final JsonProcessingException ex) {
            System.out.println("**************************** ************************ unable to publish product : " + productDTO.getProductName());
        }
    }
}
