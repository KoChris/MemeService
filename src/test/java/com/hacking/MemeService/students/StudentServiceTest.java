package com.hacking.MemeService.students;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.hacking.MemeService.data.Challenge;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.data.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import lombok.AllArgsConstructor;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

    @AllArgsConstructor
    public class StudentMatcher implements ArgumentMatcher<Student> {

        private Student left;

        // TODO: We might be able to replace this if we use Java CLOCK
        // instead of Date
        @Override
        public boolean matches(Student right) {

            if (!right.getEmail().equals(left.getEmail())) {
                return false;
            }

            if (!right.getName().equals(left.getName())) {
                return false;
            }

            if (right.getAnsweredChallenges().size() != left.getAnsweredChallenges().size()) {
                return false;
            }

            for (int i = 0; i < right.getAnsweredChallenges().size(); i++) {
                Challenge rightChallenge = right.getAnsweredChallenges().get(i);
                Challenge leftChallenge = left.getAnsweredChallenges().get(i);
                if (!rightChallenge.getQuestionIndex().equals(leftChallenge.getQuestionIndex())) {
                    return false;
                }

                if (!rightChallenge.getSolved().equals(leftChallenge.getSolved())) {
                    return false;
                }
            }

            return true;
        }
        
    }

    @MockBean
    StudentRepository mockStudentRepo;

    StudentService objectToTest;

    final Date date = new Date(0l); 

    @BeforeEach
    public void setup() {

        mockStudentRepo = mock(StudentRepository.class);

        objectToTest = new StudentService(mockStudentRepo);
    }

    @Test
    @DisplayName("All students can be retrieved")
    public void testGetAllStudents() {
        // TODO
        List<Student> result = objectToTest.getStudents();
    }

    @Test
    @DisplayName("Students who answered a particular challenge can be retrieved")
    public void something () {
        // TODO
        //objectToTest.getStudentsWhoAnsweredChallenge(1);
    }

    @Test
    @DisplayName("Students who answered all challenges can be retrieved")
    public void testGetWinners() {
        // TODO
        List<String> result = objectToTest.getWinners();
    }

    @Test
    @DisplayName("Students who answered any challenge can be retrieved")
    public void testGetParticipants() {
        // TODO
        List<String> result = objectToTest.getParticipants();
    }

    @Test
    @DisplayName("All students can be deleted")
    public void testDeleteAllStudents() {
        // TODO
    }

    @Test
    @DisplayName("Existing student can be retrieved")
    public void testGetStudent() {
        List<Challenge> challenges = Arrays.asList(
            new Challenge(1, true, date));

        Student expected = new Student("email", "name", challenges);
        doReturn(Optional.of(expected)).when(mockStudentRepo).findById("email");

        assertEquals(expected, objectToTest.getOrCreateStudent("email", "name"));

        // Assert that we don't save an existing student to the database on retrieval
        verify(mockStudentRepo, never()).save(any(Student.class));
    }

    @Test
    @DisplayName("A new student is created if one cannot be found")
    public void testGetNonexistentStudent() {
        doReturn(Optional.empty()).when(mockStudentRepo).findById("email");

        // Test
        Student result = objectToTest.getOrCreateStudent("email", "new");

        // Verify the database was called to find and then save the student
        verify(mockStudentRepo).findById("email");
        verify(mockStudentRepo).save(any(Student.class));

        assertEquals("email", result.getEmail());
        assertEquals("new", result.getName());
    }

    @Test
    @DisplayName("Student can answer the first question")
    public void testAnswerFirstQuestion() {
        List<Challenge> initialChallenges = Arrays.asList(
            new Challenge(1, false, date)
        );

        List<Challenge> expectedChallenges = Arrays.asList(
            new Challenge(1, true, date)
        );

        Student input = new Student("email", "new", initialChallenges);
        Student expected = new Student("email","new",expectedChallenges);

        objectToTest.answerQuestion(input, 1);
        verify(mockStudentRepo).save(argThat(new StudentMatcher(expected)));
    }

    @Test
    @DisplayName("Student can answer the second question")
    public void testAnswerSecondQuestion() {
        List<Challenge> initialChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date)
        );

        List<Challenge> expectedChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, true, date)
        );

        Student input = new Student("email", "new", initialChallenges);
        Student expected = new Student("email","new", expectedChallenges);

        objectToTest.answerQuestion(input, 2);
        verify(mockStudentRepo).save(argThat(new StudentMatcher(expected)));
    }

    @Test
    @DisplayName("Student can answer the third question")
    public void testAnswerThirdQuestion() {
        List<Challenge> initialChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, false, date)
        );

        List<Challenge> expectedChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, true, date)
        );

        Student input = new Student("email", "new", initialChallenges);
        Student expected = new Student("email","new", expectedChallenges);

        objectToTest.answerQuestion(input, 3);
        verify(mockStudentRepo).save(argThat(new StudentMatcher(expected)));
    }

    @Test
    @DisplayName("Student can answer the fourth question")
    public void testAnswerFourthQuestion() {
        List<Challenge> initialChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, false, date),
            new Challenge(4, false, date)
        );

        List<Challenge> expectedChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, false, date),
            new Challenge(4, true, date)
        );

        Student input = new Student("email", "new", initialChallenges);
        Student expected = new Student("email","new", expectedChallenges);

        objectToTest.answerQuestion(input, 4);
        verify(mockStudentRepo).save(argThat(new StudentMatcher(expected)));
    }

    @Test
    @DisplayName("Student can answer the fifth question")
    public void testAnswerFifthQuestion() {
        List<Challenge> initialChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, true, date),
            new Challenge(4, false, date),
            new Challenge(5, false, date)
        );

        List<Challenge> expectedChallenges = Arrays.asList(
            new Challenge(1, true, date),
            new Challenge(2, false, date),
            new Challenge(3, true, date),
            new Challenge(4, false, date),
            new Challenge(5, true, date)
        );

        Student input = new Student("email", "new", initialChallenges);
        Student expected = new Student("email","new", expectedChallenges);

        objectToTest.answerQuestion(input, 5);
        verify(mockStudentRepo).save(argThat(new StudentMatcher(expected)));
    }
}