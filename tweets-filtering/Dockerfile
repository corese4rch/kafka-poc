FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/tweets-filtering*.jar tweets-filtering.jar
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dcom.sun.management.jmxremote -Dkafka.streams.bootstrap.servers=${KAFKA_BOOTSTRAP_SERVER} -noverify ${JAVA_OPTS} -jar tweets-filtering.jar