package com.stockprocessor.stockprocessor.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stockprocessor.stockprocessor.config.KafkaConfigProps;
import com.stockprocessor.stockprocessor.dto.ProductUpdatedPublisherDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductUpdatedPublisherService implements ProductUpdatedPublisher {

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final KafkaConfigProps kafkaConfigProps;

    @Override
    public void publish(ProductUpdatedPublisherDTO productUpdatedPublisherDTO) {

        try {
            final String payload = objectMapper.writeValueAsString(productUpdatedPublisherDTO);

            //send to update topic
            kafkaTemplate.send(kafkaConfigProps.getUpdatedTopic(), payload);

            log.info("$$$$$$$$$$$$$$$$$$ $$$$$$$$$$ product updated and published to kafka topic: {}, payload: {}", kafkaConfigProps.getUpdatedTopic(), payload);
        } catch (final JsonProcessingException ex) {
            log.info("**************************** ************************ unable to publish product : {}", productUpdatedPublisherDTO);
        }
    }
}
