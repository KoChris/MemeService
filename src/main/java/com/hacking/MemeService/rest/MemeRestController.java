package com.hacking.MemeService.rest;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/memes")
@AllArgsConstructor
public class MemeRestController {

    private MemeRepository memeRepository;

    @GetMapping
    public List<Meme> getAllMemes(){
        return IterableUtils.toList(memeRepository.findAll());
    }

    @PostMapping
    public void saveMeme(@RequestBody Meme meme) {
        memeRepository.save(meme);
    }

    @DeleteMapping("/{id}")
    public void deleteMeme(@PathVariable String id) {
        memeRepository.deleteById(id);
    }

}
