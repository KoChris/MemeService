package com.hacking.MemeService.answers;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
@AllArgsConstructor
public class MinAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(int answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        int minMemePoints = memes.map(meme -> meme.getPoints()).foldLeft(minimum, Integer.MAX_VALUE);
        log.debug("ANSWER FOR MIN QUESTION IS: {}", minMemePoints);

        return answerToCheck == minMemePoints;
    }

    private static final F<Integer, F<Integer, Integer>> minimum = i -> (j -> Integer.min(i, j));
}
