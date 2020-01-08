package com.hacking.MemeService;

import com.hacking.MemeService.data.Student;
import com.hacking.MemeService.exceptions.ForbiddenIndexException;
import com.hacking.MemeService.exceptions.WrongAnswerException;
import com.hacking.MemeService.students.StudentService;

import org.springframework.web.bind.annotation.PathVariable;
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

    @PostMapping("/{questionIndex}")
    public void answerQuestion(
        @PathVariable final String questionIndex,
        @RequestHeader String studentName,
        @RequestHeader String studentEmail,
        @RequestBody final Object answer) throws WrongAnswerException, ForbiddenIndexException {


            Student student = students.getStudent(studentEmail, studentName);
            // TODO: Check that the email is valid


            switch (questionIndex) {
                case "1": 
                    // TODO: Match the body with an answer
                    // If success, save the student information
                    students.answerQuestion(student, 1);
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