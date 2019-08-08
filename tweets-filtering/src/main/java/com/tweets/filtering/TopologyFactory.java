package com.tweets.filtering;

import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import io.micronaut.context.annotation.Factory;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;

@Factory
public class TopologyFactory {

    private static final Logger LOG = LoggerFactory.getLogger(TopologyFactory.class);

    private static String TWEETS_TOPIC = "tweets";
    private static String FILTERED_TWEETS_TOPIC = "filtered-tweets";

    @Singleton
    public Topology filterTopology(JsonParser jsonParser) {
        StreamsBuilder streamsBuilder = new StreamsBuilder();
        streamsBuilder.<String, String>stream(TWEETS_TOPIC)
                .filter((k, tweetBody) -> {
                    LOG.info("FILTERING MESSAGE {}", tweetBody);
                    return extractUserFollowers(tweetBody, jsonParser) > 100;
                })
                .to(FILTERED_TWEETS_TOPIC);

        return streamsBuilder.build();
    }

    private int extractUserFollowers(String tweet, JsonParser parser) {
        try {
            return parser.parse(tweet).getAsJsonObject().get("user").getAsJsonObject().get("followers_count").getAsInt();
        } catch (JsonSyntaxException e) {
            return 0;
        }
    }
}
