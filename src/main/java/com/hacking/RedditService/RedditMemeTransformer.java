package com.hacking.RedditService;

import com.hacking.MemeService.data.Meme;
import com.hacking.RedditService.RedditService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@AllArgsConstructor
public class RedditMemeTransformer {
    private RedditService redditService;

    public List<Meme> retrieveMemes() {
        String redditApiResponse = redditService.getTopAllTimeMemes("MemeEconomy");
        List<Meme> returnMemes = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(redditApiResponse);
            JSONObject posts = obj.getJSONObject("posts");
            JSONArray postsKeys = obj.getJSONArray("postIds");
            returnMemes = extractMemes(posts, postsKeys);
        } catch (JSONException e) {
            log.info("Error extracting Reddit API response: " + redditApiResponse);
            log.info(e.getMessage());
        } finally {
            return returnMemes;
        }
    }

    @NotNull
    private List<Meme> extractMemes(JSONObject posts, JSONArray postsKeys) {
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
