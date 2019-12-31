package com.hacking.MemeService.data.students;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface StudentRepository extends MongoRepository<Student, String> {

    public Iterable<Student> findByAnsweredChallenges(String challengesAnswered);

    public Student findByName(String name);

}
