package com.hacking.MemeService;

import com.hacking.MemeService.data.Meme;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RedditMemeTransformerTest {
    @Mock
    RedditApiHandler mockRedditApiHandler;
    RedditMemeTransformer redditMemeTransformer;

    @BeforeEach
    void setUp() {
        mockRedditApiHandler = mock(RedditApiHandler.class);
        redditMemeTransformer = new RedditMemeTransformer(mockRedditApiHandler);
    }

    @Test
    @DisplayName("Can call the Reddit API and receive a response")
    void callRedditApi() {
        Meme expectedMeme = new Meme("TheGreatest", "Star Wars", "Obi Wan", "samplelink", 7);
        List<Meme> expectedMemeList = new ArrayList<>();
        expectedMemeList.add(expectedMeme);

        when(mockRedditApiHandler.getTopAllTimeMemes("MemeEconomy"))
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

        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes();

        assertEquals(expectedMemeList,actualMemeList);
    }

    @Test
    @DisplayName("When the Reddit API response is empty returns and empty MEME list")
    void returnsEmptyListOnEmptyApiResponse() {
        List<Meme> expectedEmptyMemeList = new ArrayList<>();

        when(mockRedditApiHandler.getTopAllTimeMemes("MemeEconomy"))
                .thenReturn("");

        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes();
        assertEquals(expectedEmptyMemeList,actualMemeList);
    }

    @Test
    @DisplayName("When the Reddit API response has a problem returns an empty MEME list")
    void returnsEmptyListWhenMemeInformationIsMalformed() {
        List<Meme> expectedEmptyMemeList = new ArrayList<>();

        when(mockRedditApiHandler.getTopAllTimeMemes("MemeEconomy"))
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

        List<Meme> actualMemeList = redditMemeTransformer.retrieveMemes();
        assertEquals(expectedEmptyMemeList,actualMemeList);
    }
}