package com.example.producer.model;

import lombok.*;

import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class SentimentDto {
    private String date;
    private Map<String, Integer> sentiment;

}
