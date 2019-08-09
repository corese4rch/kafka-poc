package com.filtered.tweets.consumer;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;

import java.util.stream.Collectors;

@Controller("/tweets")
public class TweetsController {

    private TweetsRepository tweetsRepository;

    public TweetsController(TweetsRepository tweetsRepository) {
        this.tweetsRepository = tweetsRepository;
    }

    @Get(produces = MediaType.APPLICATION_JSON)
    public String getTweets() {
        return tweetsRepository.getAll().stream().collect(Collectors.joining(", ", "[", "]"));
    }
}
