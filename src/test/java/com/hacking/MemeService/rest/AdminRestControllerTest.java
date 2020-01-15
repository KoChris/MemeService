package com.hacking.MemeService.rest;

import static org.mockito.Mockito.mock;

import java.util.List;

import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.students.StudentService;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AdminRestControllerTest {

    @Mock
    private MemeRepository mockMemeRepository;

    @Mock
    private StudentService mockStudentService;

    private AdminRestController objectToTest;


    @Before
    public void setup() {
        mockStudentService = mock(StudentService.class);

        objectToTest = new AdminRestController(mockMemeRepository, mockStudentService);
    }

    @Test
    @DisplayName("can get student into")
    public void getStudentInfo() {
        // TODO: Actually get and verify
        List<Student> result = objectToTest.returnStudentInfo();
    }

    @Test
    @DisplayName("can delete student info")
    public void deleteStudentInfo() {
        // TODO: Actually delete and verify
        objectToTest.deleteStudentInfo();
    }

    @Test
    @DisplayName("can get students who answered all challenges")
    public void getStudentsWhoAnsweredAll() {
        // TODO
        List<Student> result = objectToTest.getStudentBy("all");
    }

    @Test
    @DisplayName("can get students who answered at least one challenge")
    public void getStudentsWhoAnsweredOne() {
        // TODO
        List<Student> result = objectToTest.getStudentBy("any");
    }

    @Test
    @DisplayName("can get students who answered the filter question")
    public void getStudentsWhoAnsweredFilter() {
        // TODO
        List<Student> result = objectToTest.getStudentBy("filter");
    }

    @Test
    @DisplayName("can get students who answered the sum question")
    public void getStudentsWhoAnsweredSum() {
        // TODO
        List<Student> result = objectToTest.getStudentBy("sum");
    }

    @Test
    @DisplayName("can get students who answered the min question")
    public void getStudentsWhoAnsweredMin() {
        // TODO
        List<Student> result = objectToTest.getStudentBy("min");
    }

}