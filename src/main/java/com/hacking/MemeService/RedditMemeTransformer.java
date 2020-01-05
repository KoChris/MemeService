package com.hacking.MemeService;

import com.hacking.MemeService.data.Meme;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

public class RedditMemeTransformer {
    WebClient redditWebClient;

    RedditMemeTransformer() {
        redditWebClient = WebClient.builder()
                .baseUrl("https://gateway.reddit.com/desktopapi/v1/")
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

    public List<Meme> retrieveMemes() {

        JSONObject obj = new JSONObject(getRedditTopMemes());
        JSONObject posts = obj.getJSONObject("posts");
        JSONArray postsKeys = obj.getJSONArray("postIds");

        return extractMemeList(posts, postsKeys);
    }

    private List<Meme> extractMemeList(JSONObject posts, JSONArray postsKeys) {
        ArrayList<Meme> topMemes = new ArrayList<>();
        for(int i = 0; i < postsKeys.length(); i++) {
            JSONObject post = posts.getJSONObject(postsKeys.getString(i));
            topMemes.add(new Meme(
                    postsKeys.getString(i),
                    post.getString("title"),
                    post.getString("author"),
                    post.getString("permalink"),
                    post.getInt("score")
            ));
        }
        return topMemes;
    }

    private String getRedditTopMemes() {
        return redditWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("subreddits/MemeEconomy")
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
    }
}
