package com.hacking.MemeService.rest;

import java.util.List;

import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.reddit.RedditMemeTransformer;
import com.hacking.MemeService.students.StudentService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@AllArgsConstructor
public class AdminRestController {

    private MemeRepository memeRepository;

    private RedditMemeTransformer memeTransformer;

    private StudentService studentService;


    @GetMapping("/loadAllMemes")
    public void loadMemes(
        @RequestParam Integer number, 
        @RequestParam(defaultValue = "false") Boolean includeAds){

        List<Meme> memes = memeTransformer.retrieveMemes(number);
        if (!includeAds) {
            memeRepository.saveAll(memeTransformer.filterAds(memes));
        } else {
            memeRepository.saveAll(memes);
        }
    }

    @DeleteMapping("/memes")
    public void deleteAllMemes() {
        memeRepository.deleteAll();
    }

    // get all students
    @GetMapping("/studentInfo")
    public List<Student> returnStudentInfo(){
        return studentService.getStudents();
    }

    @DeleteMapping("/studentInfo")
    public void deleteStudentInfo(){
       studentService.deleteStudents();
    }

    @GetMapping("students")
    public List<String> getStudentBy(@RequestParam String answered) {
        switch (answered) {
            case "all": 
                return studentService.getWinners();
            case "any": 
                return studentService.getParticipants();
            case "filter": 
                return studentService.getStudentsWhoAnsweredChallenge(1);
            case "sum": 
                return studentService.getStudentsWhoAnsweredChallenge(2);
            case "min": 
                return studentService.getStudentsWhoAnsweredChallenge(3);
            default:
                return studentService.getStudentEmails();
        }
    }

    @GetMapping("/studentWithAllAnswer")
    public List<String> returnStudentsWithAllAnswers() {
        // TODO: deprecate this endpoint
        return studentService.getWinners();
    }

    @GetMapping("/studentWithAnyAnswers")
    public List<String> findByStudentByAnsweredQuestion() {
        // TODO: deprecate this endpoint
        return studentService.getParticipants();
    }
}
