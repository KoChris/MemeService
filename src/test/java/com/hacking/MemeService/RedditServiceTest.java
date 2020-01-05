package com.hacking.MemeService;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RedditServiceTest {
    private static MockWebServer mockWebServer;
    private static RedditService redditService;

    @BeforeAll
    static void setUp() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();
    }

    @BeforeEach
    void initialize() {
        String baseUrl = String.format("http://localhost:%s", mockWebServer.getPort());
        redditService = new RedditService(baseUrl);
    }

    @AfterAll
    static void afterAll() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    @DisplayName("Returns the JSON object on a successful call")
    void returnsObjectString() {
        String expected = "{\"posts\": \"AndrewTheGreat\"}";
        String actual;

        mockWebServer.enqueue(new MockResponse()
            .setBody(expected)
            .addHeader("Content-Type", "application/json"));

        actual = redditService.getTopAllTimeMemes("MemeEconomy");

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Returns empty string when getting a 400 response")
    void returnsEmptyStringWhen400() {
        String expected = "";
        String actual;

        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(400));

        actual = redditService.getTopAllTimeMemes("MemeEconomy");

        assertEquals(expected, actual);
    }
    @Test
    @DisplayName("Returns empty string when getting a 500 response")
    void returnsEmptyStringWhen500() {
        String expected = "";
        String actual;

        mockWebServer.enqueue(new MockResponse()
            .setResponseCode(500));

        actual = redditService.getTopAllTimeMemes("MemeEconomy");

        assertEquals(expected, actual);
    }
}