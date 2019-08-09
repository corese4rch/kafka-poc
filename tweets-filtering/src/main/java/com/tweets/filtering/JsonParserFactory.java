package com.tweets.filtering;

import com.google.gson.JsonParser;
import io.micronaut.context.annotation.Factory;

import javax.inject.Singleton;

@Factory
public class JsonParserFactory {

    @Singleton
    public JsonParser jsonParser() {
        return new JsonParser();
    }
}
