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

        Meme meme1 =  new Meme("0", "Hello There", "Obi-Wan Kanobi", "https://www.youtube.com/watch?v=rEq1Z0bjdwc", 9001);
        Meme meme2 =  new Meme("1", "Hello There", "Obi-Wan Kanobi", "https://www.youtube.com/watch?v=rEq1Z0bjdwc", 9001);
        Meme meme3 =  new Meme("2", "Hello There", "Obi-Wan Kanobi", "https://www.youtube.com/watch?v=rEq1Z0bjdwc", 9001);
        Meme meme4 =  new Meme("3", "Hello There", "Obi-Wan Kanobi", "https://www.youtube.com/watch?v=rEq1Z0bjdwc", 9001);
        Meme meme5 =  new Meme("4", "Hello There", "Obi-Wan Kanobi", "https://www.youtube.com/watch?v=rEq1Z0bjdwc", 9001);

        memeRepository.saveAll(Arrays.asList(meme1,meme2,meme3,meme4,meme5));
    }

    @DeleteMapping("/deleteAllMemes")
    public void deleteMemes(){
        memeRepository.deleteAll();
    }

}
