package com.filtered.tweets.consumer;

import java.util.List;

public interface TweetsRepository {
    void save(String tweet);
    List<String> getAll();
}
