package com.hacking.MemeService.data.students;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Student {

    @Id
    private String name;
    private String answeredChallenges;

}
