package com.hacking.MemeService;

import com.hacking.MemeService.exceptions.ForbiddenIndexException;
import com.hacking.MemeService.exceptions.WrongAnswerException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/challenges")
public class AnswersRestController {

    @PostMapping("/{questionIndex}")
    public void answerQuestion(
        @PathVariable final String questionIndex,
        @RequestHeader String studentName,
        @RequestHeader String studentEmail,
        @RequestBody final Object answer) throws WrongAnswerException, ForbiddenIndexException {

            // Student posts an answer
            // We capture their name, email, the current time and their answer

            // We only need their answer to verify it's correct
            // We store their name and email in the student object

            //Student student = new Student(stu);
            // student.setName(studentName);
            // student.setEmail(studentEmail);


            log.info("Student " + studentName + " at " + studentEmail);
            // TODO: Check that the email is valid

            // TODO: Store the student information into the database

            switch (questionIndex) {
                case "1": 
                    // TODO: Match the body with an answer
                    return;
                case "2": break;
                case "3": break;
                case "4": break;
                case "5": break;
                default: 
                    throw new ForbiddenIndexException("Forbidden index", questionIndex);
            }
        // If request body and expected result don't match, throw 400
        throw new WrongAnswerException("Question "+ questionIndex + " was wrong");
    }
}