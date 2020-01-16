package com.hacking.MemeService.reddit;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RedditService {
    private static WebClient webClient;

    RedditService(String baseUrl) {
        webClient = WebClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("authority", "gateway.reddit.com")
                .defaultHeader(HttpHeaders.ORIGIN, "https://www.reddit.com")
                .defaultHeader("x-reddit-loid", "0000000000518q0n1w.2.1573960721631.Z0FBQUFBQmQwTHdSSEJoQldoNUNfSEl3Q1FfY2VqcVA2NXdBNEZaYXd6ZlNDLVA4VlhHc1U2OWNZSkk4b0pDMlQxR19SaXp1TDJhdkFYV2Z5TmVOdjBLWWJHVGk5THVpWkpKVVpJTm5XSXBVTU81OG9jOGVyMFZoclRwa3RlU0Nqd2ZGMTk0NFNnb0I")
                .defaultHeader("x-reddaid", "ZK3J7DPOU3S72PIA")
                .defaultHeader(HttpHeaders.USER_AGENT, "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.108 Safari/537.36")
                .defaultHeader("x-reddit-session", "v51K0loUXrYsO7fTzP.0.1578162741157.Z0FBQUFBQmVFTm8xWk5RaGJBS01TazF1czg4UzllMTlONFhmbVZxUDdtX1EzYzdsczFuR3k1Sk9PRDNOeS1LelRTNWpOTjFqcm9kQzVjN1pfdGF5alJmQm9DNjZNTTA4T1FuRi1MNlpnRlRYRVFCSkhJYU55cWdrWjlmcm5EdVhHelFqSGdGdGMwWkI")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded")
                .defaultHeader(HttpHeaders.ACCEPT, "*/*")
                .defaultHeader("sec-fetch-site", "same-site")
                .defaultHeader("sec-fetch-mode", "cors")
                .defaultHeader(HttpHeaders.REFERER, "https://www.reddit.com/")
                .defaultHeader(HttpHeaders.ACCEPT_ENCODING, "gzip, deflate, br")
                .defaultHeader(HttpHeaders.ACCEPT_LANGUAGE, "en-US,en;q=0.9")
                .build();
    }

    public RedditService() {
        this("https://gateway.reddit.com/desktopapi/v1/");
    }

    public String getMemesAfter(String subReddit, String memeId) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("subreddits/" + subReddit)
                            .queryParam("rtj","only")
                            .queryParam("redditWebClient","web2x")
                            .queryParam("app","web2x-client-production")
                            .queryParam("allow_over18","false")
                            .queryParam("include","prefsSubreddit")
                            .queryParam("t","all")
                            .queryParam("sort","top")
                            .queryParam("geo_filter","CA")
                            .queryParam("layout","card")
                            .queryParam("after", memeId)
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientException e) {
            log.info("Got a bad response from the reddit API: " + e.getMessage());
            return "";
        }
    }


    public String getTopAllTimeMemes(String subReddit) {
        try {
            return webClient.get()
                    .uri(uriBuilder -> uriBuilder.path("subreddits/" + subReddit)
                            .queryParam("rtj","only")
                            .queryParam("redditWebClient","web2x")
                            .queryParam("app","web2x-client-production")
                            .queryParam("allow_over18","false")
                            .queryParam("include","prefsSubreddit")
                            .queryParam("t","all")
                            .queryParam("sort","top")
                            .queryParam("geo_filter","CA")
                            .queryParam("layout","card")
                            .build())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (WebClientException e) {
            log.info("Got a bad response from the reddit API: " + e.getMessage());
            return "";
        }
    }

}
