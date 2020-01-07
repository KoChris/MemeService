package com.hacking.Answers;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import fj.function.Integers;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class SumAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(int answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        int sumMemePoints = memes.map(meme -> meme.getPoints()).foldLeft(Integers.add, 0);

        return answerToCheck == sumMemePoints;
    }
}
