package com.hacking.MemeService.students;

import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public List<Student> getStudents() {
        return repository.findAll();
    }
    
    public List<String> getStudentEmails() {
        return repository.findAll().stream().map(Student::getEmail).collect(Collectors.toList());
    }

    // TODO: Test this!!!!!
    public List<String> getStudentsWhoAnsweredChallenge(int challengeIndex) {
        Stream<Student> students = repository.findAll().stream();

        Stream<String> filtered = students
            .filter(s -> s.getAnsweredChallenges().get(challengeIndex - 1).getSolved())
            .map(Student::getEmail);
        // TODO
        return filtered.collect(Collectors.toList());
    }

    // TODO: Test this PLOX
    public List<String> getWinners() {
        // A "winner" is a student who completed all challenges
        List<Student> students = repository.findAll();
        
        List<String> winners = students.stream()
            .filter(this::studentAnsweredAllChallenges)
            .map(Student::getEmail)
            .collect(Collectors.toList());
        
        return winners;
    }

    private boolean studentAnsweredAllChallenges(Student student) {
        // TODO: Can this be better done functionally
        for (Challenge c: student.getAnsweredChallenges()) {
            if (!c.getSolved()) {
                return false;
            }
        }
        return true;
    }

    public List<String> getParticipants() {
        // A "participant" is a student who completed any challenge
        return repository.findByStudentByAnsweredQuestion()
            .stream().map(Student::getEmail)
            .collect(Collectors.toList());
    }

    public void deleteStudents() {
        repository.deleteAll();
    }

    public Student getOrCreateStudent(final String email, final String name) {

        final Student student = repository.findById(email)
            .orElseGet(() -> {
                Student s = buildNewStudent(email, name); 
                repository.save(s);
                return s; 
            });

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
