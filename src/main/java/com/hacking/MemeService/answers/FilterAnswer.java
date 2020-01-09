package com.hacking.MemeService.answers;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import org.apache.commons.collections4.CollectionUtils;

import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import lombok.AllArgsConstructor;
 
@AllArgsConstructor
public class FilterAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(final List<Meme> answerToCheck) {
        final List<Meme> allMemes = memeRepository.findAll();
        final Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        final Array<Meme> filteredMemes = memes.filter(greaterThanLimit);

        return CollectionUtils.isEqualCollection(filteredMemes.toCollection(), answerToCheck);
    }

    private static final F<Meme, Boolean> greaterThanLimit = i -> i.getPoints() > 59999;
}
