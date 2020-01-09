package com.hacking.MemeService;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.hacking.MemeService.data.Challenge;
import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.exceptions.WrongAnswerException;
import com.hacking.MemeService.students.StudentService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
    
    final Meme meme1 = new Meme("7", "Test1", "A", "testlink1", 100000);
    final Meme meme2 = new Meme("8", "Test2", "B", "testlink2", 60000);
    final Meme meme3 = new Meme("9", "Test3", "C", "testlink3", 59999);
    final Meme meme4 = new Meme("10", "Test4", "D", "testlink4", 50000);

    final Date date = new Date(0l);

    @MockBean
    private static MemeRepository mockMemeRepository;

    @BeforeEach
    public void setup() {

        mockStudentService = mock(StudentService.class);
        mockMemeRepository = mock(MemeRepository.class);

        objectToTest = new AnswersRestController(mockStudentService, mockMemeRepository);

        List<Meme> listToReturn = Arrays.asList(meme1, meme2, meme3, meme4);
        when(mockMemeRepository.findAll()).thenReturn(listToReturn);
    }

    @Test
    @DisplayName("Accepts the correct answer for the filter question for a new student")
    public void canAnswerFilterQuestionCorrectlyForNewStudent() throws WrongAnswerException, Exception {
        List<Meme> answer = Arrays.asList(meme1, meme2);
        objectToTest.answerFilterQuestion("John", "Johnson", answer);
    }

    @Test
    @DisplayName("Accepts the correct answer for the filter question for an existing student")
    public void existingStudentCanAnswerFilterQuestionCorrectly() throws WrongAnswerException, Exception {
        Student student = new Student("johnemail", "John", Arrays.asList(new Challenge(1, false, date)));
        doReturn(student).when(mockStudentService).getOrCreateStudent("johnemail", "John");
        
        List<Meme> answer = Arrays.asList(meme1, meme2);
        objectToTest.answerFilterQuestion("John", "johnemail", answer);
    }

    @Test
    @DisplayName("Throws error when the answer for the filter question is incorrect")
    public void throwsErrorWhenFilterAnswerIsWrong() throws WrongAnswerException, Exception {
        List<Meme> answer = Arrays.asList(meme1);
        assertThrows(WrongAnswerException.class, () -> 
                objectToTest.answerFilterQuestion("John", "Johnson", answer));
    }
    
    @Test
    @DisplayName("Accepts the correct answer for the sum question for a new student")
    public void newStudentCanAnswerSumQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerSumQuestion("John", "Johnson", 269999);
    }

    @Test
    @DisplayName("Accepts the correct answer for the sum question for an existing student")
    public void existingStudentCanAnswerSumQuestionCorrectly() throws WrongAnswerException, Exception {
        Student student = new Student("Johnson", "John", Arrays.asList(new Challenge(1, false, date), new Challenge(2, false, date)));
        doReturn(student).when(mockStudentService).getOrCreateStudent("Johnson", "John");

        objectToTest.answerSumQuestion("John", "Johnson", 269999);
    }
    
    @Test
    @DisplayName("Throws error when the answer for the sum question is incorrect")
    public void throwsErrorWhenSumAnswerIsWrong() throws WrongAnswerException, Exception {
        assertThrows(WrongAnswerException.class, () -> objectToTest.answerSumQuestion("John", "Johnson", 26999));
    }
    
    @Test
    @DisplayName("Accepts the correct answer for the minimum question for a new student")
    public void newStudentCanAnswerMinimumQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerMinQuestion("John", "Johnson", 50000);
    }

    @Test
    @DisplayName("Accepts the correct answer for the minimum question for an existing student") 
    public void existingStudentCanAnswerMinimumQuestionCorrectly() throws Exception {
        Student student = new Student("Johnson", "John", Arrays.asList(new Challenge(1, false, date), new Challenge(2, false, date)));
        doReturn(student).when(mockStudentService).getOrCreateStudent("Johnson", "John");

        objectToTest.answerMinQuestion("John", "Johnson", 50000);
    }
    
    @Test
    @DisplayName("Throws error when the answer for the minimum question is incorrect")
    public void throwsErrorWhenMinimumAnswerIsWrong() throws WrongAnswerException, Exception {
        assertThrows(WrongAnswerException.class, () -> objectToTest.answerMinQuestion("John", "Johnson", 26999));
    }

}