package com.hacking.MemeService;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

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
        RedditMemeTransformer memeTransformer = new RedditMemeTransformer(new RedditApiHandler());

        memeRepository.saveAll(memeTransformer.retrieveMemes());
    }

    @DeleteMapping("/deleteAllMemes")
    public void deleteMemes(){
        memeRepository.deleteAll();
    }

}
