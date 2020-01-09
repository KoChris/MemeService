package com.hacking.MemeService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.hacking.MemeService.answers.FilterAnswer;
import com.hacking.MemeService.data.Challenge;
import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.exceptions.ForbiddenIndexException;
import com.hacking.MemeService.exceptions.WrongAnswerException;
import com.hacking.MemeService.students.StudentService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class AnswersRestControllerTest {

    @Mock
    private StudentService mockStudentService;

    public AnswersRestController objectToTest;
    
    static Meme meme1 = new Meme("7", "Test1", "A", "testlink1", 100000);
    static Meme meme2 = new Meme("8", "Test2", "B", "testlink2", 60000);
    static Meme meme3 = new Meme("9", "Test3", "C", "testlink3", 59999);
    static Meme meme4 = new Meme("10", "Test4", "D", "testlink4", 50000);

    @MockBean
    private static MemeRepository mockMemeRepository;

    @BeforeAll
    static void beforeAll() {
        List<Meme> listToReturn = new ArrayList<>();
        listToReturn.add(meme1);
        listToReturn.add(meme2);
        listToReturn.add(meme3);
        listToReturn.add(meme4);
        mockMemeRepository = mock(MemeRepository.class);
        when(mockMemeRepository.findAll()).thenReturn(listToReturn);
    }

    @BeforeEach
    public void setup() {
        mockStudentService = mock(StudentService.class);
        objectToTest = new AnswersRestController(mockStudentService, mockMemeRepository);
    }

    @Test
    @DisplayName("Accepts the correct answer for the filter question")
    public void canAnswerFilterQuestionCorrectly() throws WrongAnswerException, Exception {
        ArrayList<Meme> answer = new ArrayList<Meme>();
        answer.add(meme1);
        answer.add(meme2);
        objectToTest.answerFilterQuestion("John", "Johnson", answer);
        // List<Challenge> challenges = Arrays.asList(new Challenge(1, false, new Date()));
        // doReturn(new Student("johnemail", "John Doe", challenges))
        //     .when(mockStudentService).getStudent("johnemail", "John Doe");
        
    }

    @Test
    @DisplayName("Throws error when the answer for the filter question is incorrect")
    public void throwsErrorWhenFilterAnswerIsWrong() throws WrongAnswerException, Exception {
        ArrayList<Meme> answer = new ArrayList<Meme>();
        answer.add(meme1);
        assertThrows(WrongAnswerException.class, () -> objectToTest.answerFilterQuestion("John", "Johnson", answer));
    }
    
    @Test
    @DisplayName("Accepts the correct answer for the sum question")
    public void canAnswerSumQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerSumQuestion("John", "Johnson", 269999);
    }
    
    @Test
    @DisplayName("Throws error when the answer for the sum question is incorrect")
    public void throwsErrorWhenSumAnswerIsWrong() throws WrongAnswerException, Exception {
        assertThrows(WrongAnswerException.class, () -> objectToTest.answerSumQuestion("John", "Johnson", 26999));
    }
    
    @Test
    @DisplayName("Accepts the correct answer for the minimum question")
    public void canAnswerMinimumQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerMinQuestion("John", "Johnson", 50000);
    }
    
    @Test
    @DisplayName("Throws error when the answer for the minimum question is incorrect")
    public void throwsErrorWhenMinimumAnswerIsWrong() throws WrongAnswerException, Exception {
        assertThrows(WrongAnswerException.class, () -> objectToTest.answerMinQuestion("John", "Johnson", 26999));
    }

}