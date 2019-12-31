package com.hacking.MemeService.data.memes;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MemeRepository extends MongoRepository<Meme, String> {

    public Meme findByTitle(String title);

}
