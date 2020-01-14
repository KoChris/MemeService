package com.hacking.MemeService.rest;

import java.util.List;

import com.hacking.MemeService.answers.FilterAnswer;
import com.hacking.MemeService.answers.MinAnswer;
import com.hacking.MemeService.answers.SumAnswer;
import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.exceptions.WrongAnswerException;
import com.hacking.MemeService.students.StudentService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/challenges")
@AllArgsConstructor
public class AnswersRestController {

    private final StudentService students;

    private final MemeRepository memeRepository;

    @PostMapping("/filter")
    public void answerFilterQuestion(@RequestHeader final String studentName, @RequestHeader final String studentEmail,
            @RequestBody final List<Meme> answer) throws WrongAnswerException {

        FilterAnswer filterAnswer = new FilterAnswer(memeRepository);

        Student student = students.getOrCreateStudent(studentEmail, studentName);

        if (!filterAnswer.isCorrect(answer)) {
            throw new WrongAnswerException("lol no");
        }

        students.answerQuestion(student, 1);
    }

    @PostMapping("/sum")
    public void answerSumQuestion(@RequestHeader String studentName, @RequestHeader String studentEmail,
            @RequestBody final int answer) throws WrongAnswerException {

        SumAnswer sumAnswer = new SumAnswer(memeRepository);

        Student student = students.getOrCreateStudent(studentEmail, studentName);

        if (!sumAnswer.isCorrect(answer)) {
            throw new WrongAnswerException("lol no");
        }

        students.answerQuestion(student, 2);

    }

    @PostMapping("/min")
    public void answerMinQuestion(@RequestHeader String studentName, @RequestHeader String studentEmail,
            @RequestBody final int answer) throws WrongAnswerException {

        MinAnswer minAnswer = new MinAnswer(memeRepository);

        Student student = students.getOrCreateStudent(studentEmail, studentName);

        if (!minAnswer.isCorrect(answer)) {
            throw new WrongAnswerException("lol no");
        }

        students.answerQuestion(student, 3);

    }

}