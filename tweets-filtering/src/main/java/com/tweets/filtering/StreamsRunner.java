package com.tweets.filtering;

import io.micronaut.context.annotation.Property;
import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;
import io.micronaut.scheduling.annotation.Async;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.Topology;

import javax.inject.Singleton;
import java.util.Properties;

@Singleton
public class StreamsRunner implements ApplicationEventListener<StartupEvent> {

    @Property(name = "kafka.streams")
    private Properties kafkaStreamsProperties;

    private Topology topology;

    public StreamsRunner(Topology topology) {
        this.topology = topology;
    }

    @Override
    @Async
    public void onApplicationEvent(StartupEvent event) {
        KafkaStreams kafkaStreams = new KafkaStreams(topology, kafkaStreamsProperties);
        Runtime.getRuntime().addShutdownHook(new Thread(kafkaStreams::close));
        kafkaStreams.start();
    }
}
