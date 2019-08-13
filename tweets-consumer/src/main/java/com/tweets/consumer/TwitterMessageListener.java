package com.tweets.consumer;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Async;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Singleton
public class TwitterMessageListener implements ApplicationEventListener<StartupEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(TwitterMessageListener.class);

    private TwitterClient twitterClient;
    private List<TwitterMessageHandler> messageHandlers;

    public TwitterMessageListener(TwitterClient twitterClient, List<TwitterMessageHandler> messageHandlers) {
        this.twitterClient = twitterClient;
        this.messageHandlers = messageHandlers;
    }

    public void listenToTwitterMessages() {
        twitterClient.connect();

        try {
            while (!twitterClient.isDone()) {
                twitterClient.pollMessage(100, TimeUnit.MILLISECONDS)
                        .ifPresent(message -> messageHandlers.forEach(handler -> handler.handle(message)));
            }
        } catch (InterruptedException e) {
            LOG.error("Interruption exception occurred: ", e);
        } finally {
            twitterClient.stop();
        }
    }

    @Override
    @Async
    public void onApplicationEvent(StartupEvent event) {
        listenToTwitterMessages();
    }
}
