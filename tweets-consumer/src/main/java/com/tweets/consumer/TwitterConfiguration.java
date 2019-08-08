package com.tweets.consumer;

import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import io.micronaut.context.annotation.ConfigurationProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@ConfigurationProperties("twitter")
@Data
public class TwitterConfiguration {
    @NotNull
    private String consumerKey;
    @NotNull
    private String consumerSecret;
    @NotNull
    private String accessToken;
    @NotNull
    private String accessSecret;
    @NotNull
    private List<String> terms;

    private String clientAppId = "twitter-consumer-01";

    public Authentication getAuthentication() {
        return new OAuth1(consumerKey, consumerSecret, accessToken, accessSecret);
    }
}
