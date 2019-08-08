# kafka-poc
Proof of concept for kafka usage.

# Prerequisites
1. java 11
2. docker
3. docker-compose
4. twitter-dev account

# How to use
To run this example on your machine you have to:

1. Set your tweeter credentials in `tweets-consumer/src/main/resources/application.yml`
2. Go to the project root directory.
    ```
    cd kafka-poc
    ```
3. Build and generate docker images for each module.
    ```
    ./mnvw clean install
    ```
4. Run docker compose.
    ```
    docker-compose -f docker/docker-compose.yml up
    ```
If everything is fine you should be able to access filtered tweets by executing HTTP 
request GET localhost:8080/tweets   
