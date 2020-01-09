package com.hacking.MemeService.answers;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import fj.data.Array;
import fj.data.Collectors;
import fj.function.Integers;
import lombok.AllArgsConstructor;

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
