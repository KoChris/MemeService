package com.hacking.MemeService.reddit;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.hacking.MemeService.data.Meme;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Component
public class RedditMemeTransformer {
    private RedditService redditService;

    public List<Meme> filterAds(List<Meme> input) {
        return input.stream()
            .filter(meme -> meme.getPoints() > 1000)
            .filter(meme -> meme.getId().length() < 10)
            .collect(Collectors.toList());
    }

    public List<Meme> retrieveMemes(int approximateAmount) {
        String redditApiResponse = redditService.getTopAllTimeMemes("MemeEconomy");
        List<Meme> topMemes = extractMemes(redditApiResponse);

        while (topMemes.size() < approximateAmount) {
            String id = topMemes.get(topMemes.size() - 1).getId();

            String moreRedditApiResponse = redditService.getMemesAfter("MemeEconomy", id);
            List<Meme> moreMemes = extractMemes(moreRedditApiResponse);

            topMemes.addAll(moreMemes);
        }
        log.info(topMemes.size() + " memes retrieved from Reddit");
        return topMemes;
    }

    private List<Meme> extractMemes(String redditApiResponse) {
        List<Meme> returnMemes = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(redditApiResponse);
            JSONObject posts = obj.getJSONObject("posts");
            JSONArray postsKeys = obj.getJSONArray("postIds");
            returnMemes = extractMemeObjects(posts, postsKeys);
        } catch (JSONException e) {
            log.info("Error extracting Reddit API response: " + redditApiResponse);
            log.info(e.getMessage());
        } finally {
            log.info("Memes have been retrieved");
        }

        return returnMemes;
    }

    @NotNull
    private List<Meme> extractMemeObjects(JSONObject posts, JSONArray postsKeys) {
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
