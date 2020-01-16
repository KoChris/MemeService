package com.hacking.MemeService.rest;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.data.StudentRepository;
import com.hacking.MemeService.reddit.RedditMemeTransformer;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminRestController {

    private MemeRepository memeRepository;

    private StudentRepository studentRepository;

    private RedditMemeTransformer memeTransformer;

    @GetMapping("/loadAllMemes")
    public void loadMemes(@RequestParam Integer number, @RequestParam(defaultValue = "false") Boolean includeAds){

        List<Meme> memes = memeTransformer.retrieveMemes(number);
        if (!includeAds) {
            memeRepository.saveAll(memeTransformer.filterAds(memes));
        } else {
            memeRepository.saveAll(memes);
        }
    }

    // get all students
    @GetMapping("/studentInfo")
    public List<Student> returnStudentInfo(){
        return studentRepository.findAll();
    }

    @DeleteMapping("/studentInfo")
    public void deleteStudentInfo(){
        studentRepository.deleteAll();
    }

    @GetMapping("/studentWithAllAnswer")
    public List<Student> returnStudentsWithAllAnswers() {
        return studentRepository.findAll();
    }

    @GetMapping("/studentWithAnyAnswers")
    public List<Student> findByStudentByAnsweredQuestion() {
        return studentRepository.findByStudentByAnsweredQuestion();
    }
}
