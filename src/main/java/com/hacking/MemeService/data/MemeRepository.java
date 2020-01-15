package com.hacking.MemeService.data;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<Meme, String> {

    public Meme findByTitle(String title);

    public void deleteById(String id);

}
