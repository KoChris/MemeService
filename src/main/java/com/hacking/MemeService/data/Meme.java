package com.hacking.MemeService.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
public class Meme {

    @Id
    private String id;
    private String title;
    private String author;
    private String link;
    private Integer points;

}
