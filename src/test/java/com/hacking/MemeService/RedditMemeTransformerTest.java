package com.hacking.MemeService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedditMemeTransformerTest {
    RedditMemeTransformer transformerToTest;

    @BeforeEach
    void setUp() {
        transformerToTest = new RedditMemeTransformer();
    }

    @Test
    @DisplayName("Can call the Reddit API and receive a response")
    void callRedditApi() {
        transformerToTest.retrieveMemes();
    }
}