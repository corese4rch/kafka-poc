FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
COPY target/filtered-tweets-consumer*.jar filtered-tweets-consumer.jar
CMD java -XX:+UnlockExperimentalVMOptions -XX:+UseCGroupMemoryLimitForHeap -Dcom.sun.management.jmxremote -Dkafka.bootstrap.servers=${KAFKA_BOOTSTRAP_SERVER} -noverify ${JAVA_OPTS} -jar filtered-tweets-consumer.jar