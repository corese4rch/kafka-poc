package com.filtered.tweets.consumer;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@KafkaListener
public class FilteredTweetsKafkaListener {

    private static final Logger LOG = LoggerFactory.getLogger(FilteredTweetsKafkaListener.class);
    private static final String FILTERED_TWEETS_TOPIC = "filtered-tweets";

    private TweetsRepository tweetsRepository;

    public FilteredTweetsKafkaListener(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    @Topic(FILTERED_TWEETS_TOPIC)
    public void receive(String tweet) {
        LOG.info("Received tweet {}", tweet);
        tweetsRepository.save(tweet);
    }
}
