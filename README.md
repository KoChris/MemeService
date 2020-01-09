# MemeService for the Meme Economy

## About

This service reads in Meme information from the Meme Economy Subreddit and stores it in a Mongo database.  The service exposes three challenges which encourage the user of the API to solve puzzles with functional programming

## Interface

The interface for the Meme Service is documented in Open API 3.0 under [src/main/swagger](src/main/swagger)

## Puzzles

### Puzzle 1

For the first challenge, filter all the Memes to contain only those with a score of 60,000 or more.

### Puzzle 2

For the second challenge, calculate the sum of all the Memes in the database

### Puzzle 3

For the third challenge, return the minimum score required for a Meme to reach the Top All-Time