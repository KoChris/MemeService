package com.hacking.MemeService.data;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    public Iterable<Student> findByAnsweredChallenges(String challengesAnswered);

    public Student findByName(String name);

    public Student findByEmail(final String email);

    @Query(value = "{ \"answeredChallenges\": { $elemMatch: { solved: true }}}")
    List<Student> findByStudentByAnsweredQuestion();

}