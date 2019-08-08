package com.tweets.consumer;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Singleton
public class KafkaProducerTwitterMessageHandler implements TwitterMessageHandler {

    private static final Logger LOG = LoggerFactory.getLogger(KafkaProducerTwitterMessageHandler.class);

    private static final String TWITTER_MESSAGES_TOPIC = "tweets";

    private Producer<String, String> producer;

    public KafkaProducerTwitterMessageHandler(@KafkaClient Producer<String, String> producer) {
        this.producer = producer;
    }

    @Override
    public void handle(String message) {
        this.producer.send(new ProducerRecord<>(TWITTER_MESSAGES_TOPIC, message), (metadata, exception) -> {
            if (null == exception) {
                LOG.info("Message {} send to topic {}, partition {}", message, metadata.topic(), metadata.partition());
            } else {
                LOG.error("Error happened", exception);
            }
        });
    }
}
