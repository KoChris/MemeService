package com.hacking.MemeService.answers;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import fj.data.Array;
import fj.data.Collectors;
import fj.function.Integers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
public class SumAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(int answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        int sumMemePoints = memes.map(meme -> meme.getPoints()).foldLeft(Integers.add, 0);
        log.debug("ANSWER FOR SUM QUESTION IS: {}", sumMemePoints);

        return answerToCheck == sumMemePoints;
    }

    public boolean isDemoCorrect(int answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        int sumMemePoints = memes.map(meme -> 1).foldLeft(Integers.add, 0);
        log.debug("ANSWER FOR SUM QUESTION IS: {}", sumMemePoints);

        return answerToCheck == sumMemePoints;
    }
}
