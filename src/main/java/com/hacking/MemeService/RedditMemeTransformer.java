package com.hacking.MemeService;

import com.hacking.MemeService.data.Meme;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class RedditMemeTransformer {
    RedditApiHandler redditApiHandler;

    public List<Meme> retrieveMemes() {
        String redditApiResponse = redditApiHandler.getTopAllTimeMemes("MemeEconomy");
        List<Meme> returnList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(redditApiResponse);
            JSONObject posts = obj.getJSONObject("posts");
            JSONArray postsKeys = obj.getJSONArray("postIds");
            returnList = extractMemeList(posts, postsKeys);
        } catch (JSONException e) {
            log.info("Error extracting Reddit API response: " + redditApiResponse);
            log.info(e.getMessage());
        } finally {
            return returnList;
        }
    }

    @NotNull
    private List<Meme> extractMemeList(JSONObject posts, JSONArray postsKeys) {
        List<Meme> topMemes = new ArrayList<>();
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
}
