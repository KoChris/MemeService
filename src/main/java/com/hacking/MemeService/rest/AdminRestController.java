package com.hacking.MemeService.rest;

import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.data.StudentRepository;
import com.hacking.MemeService.reddit.RedditMemeTransformer;
import com.hacking.MemeService.reddit.RedditService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminRestController {

    private MemeRepository memeRepository;

    private StudentRepository studentRepository;

    @GetMapping("/loadAllMemes")
    public void loadMemes(){
        RedditMemeTransformer memeTransformer = new RedditMemeTransformer(new RedditService());
        memeRepository.saveAll(memeTransformer.retrieveMemes());
    }

    // get all students
    @GetMapping("/studentInfo")
    public List<Student> returnStudentInfo(){
        return studentRepository.findAll();
    }

    @GetMapping("/studentWithAllAnswer")
    public List<Student> returnStudentsWithAllAnswers() {
        return studentRepository.findAll();
    }

    @GetMapping("/studentWithAnyAnswers")
    public List<Student> returnStudentsWithAnyAnswer() {
        return studentRepository.findAll();
    }
}
