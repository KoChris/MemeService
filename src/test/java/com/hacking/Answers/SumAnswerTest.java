package com.hacking.Answers;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class SumAnswerTest {
    SumAnswer sumAnswer;
    @Mock
    private static MemeRepository memeRepository;

    @BeforeAll
    static void beforeAll() {
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
    }

    @BeforeEach
    void setUp() {
        sumAnswer = new SumAnswer(memeRepository);
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