package com.hacking.MemeService.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface StudentRepository extends MongoRepository<Student, String> {

    public Iterable<Student> findByAnsweredChallenges(String challengesAnswered);

    public Student findByName(String name);

}