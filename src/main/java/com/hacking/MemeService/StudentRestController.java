package com.hacking.MemeService;

import com.hacking.MemeService.data.students.Student;
import com.hacking.MemeService.data.students.StudentRepository;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.IterableUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/students")
@AllArgsConstructor
public class StudentRestController {

    private StudentRepository studentRepository;

    @GetMapping
    public List<Student> getAllStudents(){
        return IterableUtils.toList(studentRepository.findAll());
    }

    @PostMapping
    public void saveStudent(@RequestBody Student student){
        studentRepository.save(student);
    }

    @GetMapping("/{answeredQuestion}")
    public List<Student> getStudentsWithAllAnsweredQuestions(
            @PathVariable String answeredQuestion){
        return IterableUtils.toList(studentRepository.findByAnsweredChallenges(answeredQuestion));
    }

    @GetMapping("answer")
    public void answerQuestionForStudent(){

        completeQuestion("AndrewBriggs", 1);
    }

    private void completeQuestion(
            String studentName,
            int questionAnswered){

        Optional<Student> optStudent = studentRepository.findById(studentName);
        Student student = null;

        if(optStudent.isPresent()){
            student = optStudent.get();
            StringBuilder builder = new StringBuilder(student.getAnsweredChallenges());
            builder.setCharAt(questionAnswered, '1');

            student.setAnsweredChallenges(builder.toString());

        } else {
            StringBuilder builder = new StringBuilder("00000");
            builder.setCharAt(questionAnswered, '1');

            student = new Student(studentName, builder.toString());
        }

        studentRepository.save(student);
    }
}
