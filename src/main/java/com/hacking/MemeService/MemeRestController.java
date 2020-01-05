package com.hacking.MemeService;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.reddit.RedditMemeTransformer;
import com.hacking.MemeService.reddit.RedditService;

import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/hello")
    public String helloThere() {
        return "hello there";
    }

    @GetMapping
    public List<Meme> getAllMemes(){
        return IterableUtils.toList(memeRepository.findAll());
    }

    @PostMapping
    public void saveMeme(@RequestBody Meme meme) {
        memeRepository.save(meme);
    }

    @GetMapping("/loadAllMemes")
    public void loadMemes(){
        RedditMemeTransformer memeTransformer = new RedditMemeTransformer(new RedditService());

        memeRepository.saveAll(memeTransformer.retrieveMemes());
    }

    @DeleteMapping("/deleteAllMemes")
    public void deleteMemes(){
        memeRepository.deleteAll();
    }

}
