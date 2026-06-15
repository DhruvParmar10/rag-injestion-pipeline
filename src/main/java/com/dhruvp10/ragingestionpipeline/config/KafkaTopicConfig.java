package com.dhruvp10.ragingestionpipeline.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {
    public static final String INGESTION_TOPIC = "document-ingestion-events";
    public static final String DLQ_TOPIC = "document-ingestion-dlq";

    @Bean
    public NewTopic documentIngestionEventTopic() {
        return TopicBuilder.name(INGESTION_TOPIC)
                .partitions(3)
                .replicas(1)
                .config("retention.ms","86400000")
                .build();
    }

    @Bean
    public NewTopic documentIngestionDLQTopic() {
        return TopicBuilder.name(DLQ_TOPIC)
                .partitions(1)
                .replicas(1)
                .config("retention.ms", "604800000") // 7-day retention for failed messages
                .build();
    }

}
