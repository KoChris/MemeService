package com.hacking.MemeService.answers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

class SumAnswerTest {
    SumAnswer sumAnswer;
    
    @Mock
    private MemeRepository memeRepository;

    @BeforeEach
    void setUp() {
        List<Meme> listToReturn = new ArrayList<>();
        Meme meme1 = new Meme("7","Test1","A", "testlink1", 100000);
        Meme meme2 = new Meme("8","Test2","B", "testlink2", 60000);
        Meme meme3 = new Meme("9","Test3","C", "testlink3", 59999);
        Meme meme4 = new Meme("10","Test4","D", "testlink4", 50000);
        listToReturn.add(meme1);
        listToReturn.add(meme2);
        listToReturn.add(meme3);
        listToReturn.add(meme4);
        memeRepository = mock(MemeRepository.class);
        when(memeRepository.findAll()).thenReturn(listToReturn);

        sumAnswer = new SumAnswer(memeRepository);
    }

    @Test
    @DisplayName("Does shit when there's no memes")
    void noMemememememes() {
        when(memeRepository.findAll()).thenReturn(Collections.emptyList());
        assertTrue(sumAnswer.isCorrect(0));
    }

    @Test
    @DisplayName("Successfully indicates if the answer was correct")
    void returnsTrueOnCorrectAnswer() {
        assertTrue(sumAnswer.isCorrect(269999));
    }

    @Test
    @DisplayName("Successfully indicates if the answer was incorrect")
    void returnsFalseOnIncorrectAnswer() {
        assertFalse(sumAnswer.isCorrect(269998));
    }
}