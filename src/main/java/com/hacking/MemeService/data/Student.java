package com.hacking.MemeService.data;

import java.util.List;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class Student {

    @Id
    private String email;

    private String name;
    
    private List<Challenge> answeredChallenges;

}