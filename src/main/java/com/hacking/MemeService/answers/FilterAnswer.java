package com.hacking.MemeService.answers;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import lombok.AllArgsConstructor;

@Slf4j
@AllArgsConstructor
public class FilterAnswer {

    private MemeRepository memeRepository;

    public boolean isCorrect(final List<Meme> answerToCheck) {
        final List<Meme> allMemes = memeRepository.findAll();
        final Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        final Array<Meme> filteredMemes = memes.filter(greaterThanLimit);
        log.debug("ANSWER FOR FILTER QUESTION IS: {}", filteredMemes.toCollection());

        return CollectionUtils.isEqualCollection(filteredMemes.toCollection(), answerToCheck);
    }

    public boolean isDemoCorrect(final List<Meme> answerToCheck) {
        final List<Meme> allMemes = memeRepository.findAll();
        final Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        final Array<Meme> filteredMemes = memes.filter(i -> i.getPoints() > 190000);

        return CollectionUtils.isEqualCollection(filteredMemes.toCollection(), answerToCheck);
    }

    private static final F<Meme, Boolean> greaterThanLimit = i -> i.getPoints() > 59999;
}
