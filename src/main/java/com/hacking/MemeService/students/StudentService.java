package com.hacking.MemeService.students;

import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.data.StudentRepository;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class StudentService {

    private final StudentRepository repository;

    public Student getStudent(final String email, final String name) {

        final Student student = repository.findById(email)
            .orElse(buildNewStudent(email, name));

        log.info("Student returned: " + student);

        return student;
    }

    public void answerQuestion(final Student student, final int questionIndex) {
        // TODO: include the timestamp

        StringBuilder builder = new StringBuilder(student.getAnsweredChallenges());
        builder.setCharAt(questionIndex - 1, '1');
        student.setAnsweredChallenges(builder.toString());

        log.info("Student " + student.getEmail() + " with answered challenges: " + student.getAnsweredChallenges());

        repository.save(student);
    }

    private Student buildNewStudent(final String email, final String name) {
        return Student.builder().email(email).name(name).answeredChallenges("00000").build();
    }
}
