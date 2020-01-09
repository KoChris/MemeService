package com.hacking.MemeService.answers;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class MinAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(int answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        int sumMemePoints = memes.map(meme -> meme.getPoints()).foldLeft(minimum, Integer.MAX_VALUE);

        return answerToCheck == sumMemePoints;
    }

    private static final F<Integer, F<Integer, Integer>> minimum = i -> (j -> Integer.min(i, j));
}
