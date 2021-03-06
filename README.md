# MemeService for the Meme Economy

## About

This service reads in Meme information from the Meme Economy Subreddit and stores it in a Mongo database.  The service exposes three challenges which encourage the user of the API to solve puzzles with functional programming.

## Interface

The interface for the Meme Service is documented in Open API 3.0 under [src/main/swagger](src/main/swagger).

For the workshop, the [OpenAPI 3.0 spec is publicly available](https://app.swaggerhub.com/apis/rbc-cusec/meme-service/0.0.0)

## Puzzles

Templates are available to start calling the service through [lindsaybangs/MemeServiceTemplates](https://github.com/lindsaybangs/MemeServiceTemplates)

### Puzzle 1

For the first challenge, filter all the Memes to contain only those with a score of 60,000 or more.

### Puzzle 2

For the second challenge, calculate the sum of all the Memes in the database

### Puzzle 3

For the third challenge, return the minimum score required for a Meme to reach the Top All-Time

## Installation

To install this service, run `mvn install` from the project root

## Running

To run this service, run `mvn spring-boot:run` from the project root

### Running in Development with Mongo

The embedded mongo is only scoped to test in order to not run in pcf, in order to run embedded mongo locally, you must comment out the test scope of the dependency

```xml
<dependency>
    <groupId>de.flapdoodle.embed</groupId>
    <artifactId>de.flapdoodle.embed.mongo</artifactId>
    <version>1.50.5</version>
    <!-- Uncomment below line to run in local -->
    <scope>test</scope>
</dependency>
```
		
