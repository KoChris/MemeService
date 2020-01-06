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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class FilterAnswerTest {
    FilterAnswer filterAnswer;
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
        filterAnswer = new FilterAnswer(memeRepository);
    }

    @Test
    @DisplayName("Returns in the affirmative when the correct filter is applied")
    void returnsTrueWhenListsAreEqual() {
        List<Meme> answer = new ArrayList<>();
        answer.add(new Meme("7","Test1","A", "testlink1", 100000));
        answer.add(new Meme("8","Test2","B", "testlink2", 60000));
        assertTrue(filterAnswer.isCorrect(answer));
    }

    @Test
    @DisplayName("Returns in the negative when the correct filter is not applied")
    void returnsFalseWhenListsAreEqual() {
        List<Meme> answer = new ArrayList<>();
        answer.add(new Meme("7","Test1","A", "testlink1", 100000));
        assertFalse(filterAnswer.isCorrect(answer));
    }

    @Test
    @DisplayName("Returns in the negative when the answer is empty")
    void returnsFalseWhenTheAnswerIsEmpty() {
        List<Meme> answer = new ArrayList<>();
        assertFalse(filterAnswer.isCorrect(answer));
    }
}