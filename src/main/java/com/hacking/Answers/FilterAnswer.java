package com.hacking.Answers;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import fj.F;
import fj.data.Array;
import fj.data.Collectors;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

@AllArgsConstructor
public class FilterAnswer {

    MemeRepository memeRepository;

    public boolean isCorrect(List<Meme> answerToCheck) {
        List<Meme> allMemes = memeRepository.findAll();
        Array<Meme> memes = allMemes.stream().collect(Collectors.toArray());

        Array<Meme> filteredMemes = memes.filter(greaterThan59999);

        return CollectionUtils.isEqualCollection(filteredMemes.toCollection(), answerToCheck);
    }

    public static final F<Meme, Boolean> greaterThan59999 = i -> i.getPoints() > 59999;
}
