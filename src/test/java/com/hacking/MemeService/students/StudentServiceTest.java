package com.hacking.MemeService.students;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import com.hacking.MemeService.data.Challenge;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.data.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @MockBean
    StudentRepository mockStudentRepo;

    StudentService objectToTest;

    @BeforeEach
    public void setup() {

        mockStudentRepo = mock(StudentRepository.class);

        objectToTest = new StudentService(mockStudentRepo);
    }

    @Test
    @DisplayName("Student can be retrieved")
    public void testGetStudent() {

        Student expected = new Student("email", "name", Arrays.asList(new Challenge(1, true, new Date())));
        doReturn(Optional.of(expected)).when(mockStudentRepo).findById("email");

        assertEquals(expected, objectToTest.getStudent("email", "name"));
    }

    @Test
    @DisplayName("A new student is created if one cannot be found")
    public void testGetNonexistentStudent() {
        Student expected = new Student("email", "new", "00000");

        doReturn(Optional.empty()).when(mockStudentRepo).findById("email");

        assertEquals(expected, objectToTest.getStudent("email", "new"));
    }

    @Test
    @DisplayName("Student can answer the first question")
    public void testAnswerFirstQuestion() {
        Student input = new Student("email", "new", "00000");
        Student expected = new Student("email","new","10000");

        objectToTest.answerQuestion(input, 1);
        verify(mockStudentRepo).save(expected);
    }

    @Test
    @DisplayName("Student can answer the second question")
    public void testAnswerSecondQuestion() {
        Student input = new Student("email", "new", "10000");
        Student expected = new Student("email","new","11000");

        objectToTest.answerQuestion(input, 2);
        verify(mockStudentRepo).save(expected);
    }

    @Test
    @DisplayName("Student can answer the third question")
    public void testAnswerThirdQuestion() {
        Student input = new Student("email", "new", "00000");
        Student expected = new Student("email","new","00100");

        objectToTest.answerQuestion(input, 3);
        verify(mockStudentRepo).save(expected);
    }

    @Test
    @DisplayName("Student can answer the fourth question")
    public void testAnswerFourthQuestion() {
        Student input = new Student("email", "new", "10000");
        Student expected = new Student("email","new","10010");

        objectToTest.answerQuestion(input, 4);
        verify(mockStudentRepo).save(expected);
    }

    @Test
    @DisplayName("Student can answer the fifth question")
    public void testAnswerFifthQuestion() {
        Student input = new Student("email", "new", "11110");
        Student expected = new Student("email","new","11111");

        objectToTest.answerQuestion(input, 5);
        verify(mockStudentRepo).save(expected);
    }
}