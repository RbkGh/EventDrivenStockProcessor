package com.stockprocessor.stockprocessor.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@Profile("production")
public class KafkaConfig {

    @Bean
    public NewTopic productCreatedTopic(final KafkaConfigProps kafkaConfigProps) {
        return TopicBuilder.name(kafkaConfigProps.getCreatedTopic())
        .partitions(10)
        .replicas(1)
        .build();
    }

    @Bean
    public NewTopic productUpdatedTopic(final KafkaConfigProps kafkaConfigProps) {
        return TopicBuilder.name(kafkaConfigProps.getUpdatedTopic())
                .partitions(10)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic productDeletedTopic(final KafkaConfigProps kafkaConfigProps) {
        return TopicBuilder.name(kafkaConfigProps.getDeletedTopic())
                .partitions(10)
                .replicas(1)
                .build();
    }
}
