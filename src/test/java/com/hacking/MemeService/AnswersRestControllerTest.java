package com.hacking.MemeService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.exceptions.ForbiddenIndexException;
import com.hacking.MemeService.exceptions.WrongAnswerException;
import com.hacking.MemeService.students.StudentService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AnswersRestControllerTest {

    @Mock
    private StudentService mockStudentService;

    public AnswersRestController objectToTest;

    @BeforeEach
    public void setup() {
        mockStudentService = mock(StudentService.class);

        objectToTest = new AnswersRestController(mockStudentService);
    }

    @Test
    @DisplayName("Accepts the correct answer for the first question")
    public void canAnswerFirstQuestionCorrectly() throws WrongAnswerException, Exception {
        doReturn(new Student("johnemail", "John Doe", "00000")).when(mockStudentService).getStudent("johnemail", "John Doe");
        objectToTest.answerQuestion("1", "John Doe", "johnemail", "Hello World");
    }

    @Disabled
    @Test
    @DisplayName("Does not accept the wrong answer for the first question")
    public void cannotAnswerFirstQuestionIncorrectly() throws WrongAnswerException, Exception {
        WrongAnswerException ex = assertThrows(WrongAnswerException.class, () -> {
            objectToTest.answerQuestion("1", "John Doe", "johnemail", "Hello World");
        });
        assertEquals("String", ex.getLocalizedMessage());
    }

    @Disabled
    @Test
    @DisplayName("Accepts the correct answer for the second question")
    public void canAnswerSecondQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerQuestion("2", "John Doe", "johnemail", "Hello World");
    }

    @Test
    @DisplayName("Does not accept the wrong answer for the second question")
    public void cannotAnswerSecondQuestionIncorrectly() throws WrongAnswerException, Exception {
        WrongAnswerException ex = assertThrows(WrongAnswerException.class, () -> {
            objectToTest.answerQuestion("2", "John Doe", "johnemail", "Hello World");
        });
        assertEquals("Question 2 was wrong", ex.getLocalizedMessage());
    }

    @Disabled
    @Test
    @DisplayName("Accepts the correct answer for the third question")
    public void canAnswerThirdQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerQuestion("3", "John Doe", "johnemail", "Hello World");
    }

    @Test
    @DisplayName("Does not accept the wrong answer for the third question")
    public void cannotAnswerThirdQuestionIncorrectly() throws WrongAnswerException, Exception {
        WrongAnswerException ex = assertThrows(WrongAnswerException.class, () -> {
            objectToTest.answerQuestion("3", "John Doe", "johnemail", "Hello World");
        });
        assertEquals("Question 3 was wrong", ex.getLocalizedMessage());
    }

    @Disabled
    @Test
    @DisplayName("Accepts the correct answer for the fourth question")
    public void canAnswerTheFourthQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerQuestion("2", "John Doe", "johnemail", "Hello World");
    }

    @Test
    @DisplayName("Does not accept the wrong answer for the fourth question")
    public void cannotAnswerFourthQuestionIncorrectly() throws WrongAnswerException, Exception {
        WrongAnswerException ex = assertThrows(WrongAnswerException.class, () -> {
            objectToTest.answerQuestion("4", "John Doe", "johnemail", "Hello World");
        });
        assertEquals("Question 4 was wrong", ex.getLocalizedMessage());
    }

    @Disabled
    @Test
    @DisplayName("Accepts the correct answer for the fifth question")
    public void canAnswerTheFifthQuestionCorrectly() throws WrongAnswerException, Exception {
        objectToTest.answerQuestion("2", "John Doe", "johnemail", "Hello World");
    }

    @Test
    @DisplayName("Does not accept the wrong answer for the fifth question")
    public void cannotAnswerFifthQuestionIncorrectly() throws WrongAnswerException, Exception {
        WrongAnswerException ex = assertThrows(WrongAnswerException.class, () -> {
            objectToTest.answerQuestion("5", "John Doe", "johnemail", "Hello World");
        });
        assertEquals("Question 5 was wrong", ex.getLocalizedMessage());
    }


    @Test
    @DisplayName("Throws an error if the question index is out of bounds")
    public void throwsErrorIfQuestionIndexIsOutOfBounds() {
        assertThrows(ForbiddenIndexException.class, () -> {
            objectToTest.answerQuestion("6", "John Doe", "johnemail", "Hello World!");
        });
    }
}