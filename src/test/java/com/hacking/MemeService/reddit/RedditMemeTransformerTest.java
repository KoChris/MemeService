package com.hacking.MemeService.reddit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.hacking.MemeService.data.Meme;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class RedditMemeTransformerTest {
    @Mock
    private static RedditService mockRedditService;
    private static RedditMemeTransformer redditMemeTransformer;

    @BeforeEach
    void setUp() {
        mockRedditService = mock(RedditService.class);
        redditMemeTransformer = new RedditMemeTransformer(mockRedditService);
    }

    @Test
    @DisplayName("Can call the Reddit API and receive a response")
    void callRedditApi() {
        Meme expectedMeme = new Meme("TheGreatest", "Star Wars", "Obi Wan", "samplelink", 7);
        List<Meme> expectedMemeList = new ArrayList<>();
        expectedMemeList.add(expectedMeme);

        when(mockRedditService.getTopAllTimeMemes("MemeEconomy"))
                .thenReturn("{\"postIds\":" +
                "[\"TheGreatest\"]," +
                "\"posts\":{" +
                "\"TheGreatest\": { " +
                "\"title\":\"Star Wars\"," +
                "\"author\":\"Obi Wan\"," +
                "\"permalink\":\"samplelink\"," +
                "\"score\":\"7\"" +
                "}}}"
            );

        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes(1);

        assertEquals(expectedMemeList,actualMemeList);
    }

    @Test
    @DisplayName("When the Reddit API response is empty returns and empty MEME list")
    void returnsEmptyListOnEmptyApiResponse() {
        List<Meme> expectedEmptyMemeList = new ArrayList<>();

        when(mockRedditService.getTopAllTimeMemes("MemeEconomy"))
                .thenReturn("");

        // TODO: When you fix the infinite loop, fix this test
        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes(0);
        assertEquals(expectedEmptyMemeList,actualMemeList);
    }

    @Test
    @DisplayName("When the Reddit API response has a problem returns an empty MEME list")
    void returnsEmptyListWhenMemeInformationIsMalformed() {
        List<Meme> expectedEmptyMemeList = new ArrayList<>();

        when(mockRedditService.getTopAllTimeMemes("MemeEconomy"))
                .thenReturn("{\"postIds\":" +
                        "[\"TheGreatest\"]," +
                        "\"posts\":{" +
                        "\"TheGreatest\": { " +
                        "\"\":\"Star Wars\"," +
                        "\"author\":\"Obi Wan\"," +
                        "\"permalink\":\"samplelink\"," +
                        "\"score\":\"7\"" +
                        "}}}"
                );

        // TODO: sorry
        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes(0);
        assertEquals(expectedEmptyMemeList,actualMemeList);
    }
}