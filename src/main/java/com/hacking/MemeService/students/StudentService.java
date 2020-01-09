package com.hacking.MemeService.students;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.hacking.MemeService.data.Challenge;
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

        List<Challenge> challenges = student.getAnsweredChallenges();

        Iterator<Challenge> iterator = challenges.iterator();
        while (iterator.hasNext()) {
            Challenge c = iterator.next();
            if (questionIndex == c.getQuestionIndex()) {
                c.setSolved(true);
                c.setWhen(new Date());
            }
        }

        student.setAnsweredChallenges(challenges);

        //log.info("Student " + student.getEmail() + " with answered challenges: " + student.getAnsweredChallenges());

        repository.save(student);
    }

    private Student buildNewStudent(final String email, final String name) {
        List<Challenge> challenges = Arrays.asList(
            new Challenge(1, false, new Date()),
            new Challenge(2, false, new Date()),
            new Challenge(3, false, new Date()),
            new Challenge(4, false, new Date()),
            new Challenge(5, false, new Date()));
        return Student.builder().email(email).name(name).answeredChallenges(challenges).build();
    }
}
