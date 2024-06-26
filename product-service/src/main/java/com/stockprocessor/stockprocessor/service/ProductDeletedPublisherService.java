package com.stockprocessor.stockprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.stockprocessor.config.KafkaConfigProps;
import com.stockprocessor.stockprocessor.dto.ProductDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductDeletedPublisherService implements ProductDeletedPublisher {
    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    @Override
    public void publish(ProductDTO productDTO) {
        try {
            final String payload = objectMapper.writeValueAsString(productDTO);

            //send to deleted topic
            kafkaTemplate.send(kafkaConfigProps.getDeletedTopic(), payload);

            log.info("$$$$$$$$$$$$$$$$$$ $$$$$$$$$$ product deleted and published to kafka topic: {}, payload: {}", kafkaConfigProps.getDeletedTopic(), payload);
        } catch (final JsonProcessingException ex) {
            log.info("**************************** ************************ unable to publish deleted product : {}", productDTO);
        }
    }
}
