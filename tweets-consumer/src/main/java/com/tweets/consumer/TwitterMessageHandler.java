package com.tweets.consumer;

public interface TwitterMessageHandler {
    void handle(String message);
}
