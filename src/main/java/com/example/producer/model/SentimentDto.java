package com.example.producer.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Setter
public class SentimentDto {
    private String date;
    private List<String> labelList;
    private List<Integer> quantityList;
}
