package com.tweets.consumer;

import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;

import javax.annotation.PreDestroy;
import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

@Singleton
public class TwitterClient {

    private final TwitterConfiguration twitterConfiguration;
    private final Client client;
    private final BlockingQueue<String> messageQueue = new LinkedBlockingQueue<>();

    public TwitterClient(TwitterConfiguration twitterConfiguration) {
        this.twitterConfiguration = twitterConfiguration;
        this.client = setUpClient();
    }

    public Optional<String> pollMessage(long timeout, TimeUnit timeUnit) throws InterruptedException {
        return Optional.ofNullable(messageQueue.poll(timeout, timeUnit));
    }

    public void connect() {
        this.client.connect();
    }

    public void stop() {
        this.client.stop();
    }

    public boolean isDone() {
        return this.client.isDone();
    }

    private Client setUpClient() {
        Hosts hsbHosts = new HttpHosts(Constants.STREAM_HOST);
        StatusesFilterEndpoint hsdEndpoint = new StatusesFilterEndpoint()
                .trackTerms(twitterConfiguration.getTerms());

        return new ClientBuilder()
                .name(twitterConfiguration.getClientAppId())
                .hosts(hsbHosts)
                .authentication(twitterConfiguration.getAuthentication())
                .endpoint(hsdEndpoint)
                .processor(new StringDelimitedProcessor(messageQueue))
                .build();
    }

    @PreDestroy
    public void cleanUp() {
        client.stop();
    }
}
