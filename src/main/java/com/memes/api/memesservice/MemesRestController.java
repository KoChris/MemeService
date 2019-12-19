package com.memes.api.memesservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/memes/api")
public class MemesRestController {

    @GetMapping("/hello")
    public String youDidItKid() {
        return "hello there";
    }

}
