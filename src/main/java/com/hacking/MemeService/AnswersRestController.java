package com.hacking.MemeService;

import java.util.ArrayList;

import com.hacking.MemeService.answers.FilterAnswer;
import com.hacking.MemeService.answers.MinAnswer;
import com.hacking.MemeService.answers.SumAnswer;
import com.hacking.MemeService.data.Meme;
import com.hacking.MemeService.data.MemeRepository;
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
    public void answerFilterQuestion(@RequestHeader String studentName, @RequestHeader String studentEmail,
            @RequestBody final ArrayList<Meme> answer) throws WrongAnswerException {

        FilterAnswer filterAnswer = new FilterAnswer(memeRepository);
        
        if (filterAnswer.isCorrect(answer)) {
            return;
        }

        throw new WrongAnswerException("Filter question was wrong");

    }

    @PostMapping("/sum")
    public void answerSumQuestion(@RequestHeader String studentName, @RequestHeader String studentEmail,
            @RequestBody final int answer) throws WrongAnswerException {

        SumAnswer sumAnswer = new SumAnswer(memeRepository);

        if (sumAnswer.isCorrect(answer)) {
            return;
        }

        throw new WrongAnswerException("Filter question was wrong");

    }

    @PostMapping("/min")
    public void answerMinQuestion(@RequestHeader String studentName, @RequestHeader String studentEmail,
            @RequestBody final int answer) throws WrongAnswerException {

        MinAnswer minAnswer = new MinAnswer(memeRepository);

        if (minAnswer.isCorrect(answer)) {
            return;
        }

        throw new WrongAnswerException("Filter question was wrong");

    }

}