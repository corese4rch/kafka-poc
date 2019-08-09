package com.filtered.tweets.consumer;

import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class InMemoryTweetsRepository implements TweetsRepository {

    private List<String> tweets = new ArrayList<>();

    @Override
    public void save(String tweet) {
        tweets.add(tweet);
    }

    @Override
    public List<String> getAll() {
        return tweets;
    }
}