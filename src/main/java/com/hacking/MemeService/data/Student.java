package com.hacking.MemeService.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Student {

    @Id
    private String email;

    private String answeredChallenges;

    private String name;

    public void answerQuestion(final int index) {
        final StringBuilder builder = new StringBuilder(answeredChallenges);
        builder.setCharAt(index, '1');
    }
}