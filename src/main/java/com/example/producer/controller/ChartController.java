package com.example.producer.controller;

import com.example.producer.model.SentimentDto;
import com.example.producer.scheduler.ProducerScheduler;
import com.example.producer.service.TwitterScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class ChartController {
    private final TwitterScheduler twitterScheduler;
    private final ProducerScheduler producerScheduler;

//    @GetMapping("/chart-data/{keyword}")
//    public List<SentimentDto> samplePublish(@PathVariable String keyword) {
//        // TODO Make this Async
//        log.info("{}", keyword);
//        return twitterScheduler.doFixedDelayJob(keyword);
//    }

    @GetMapping("/chart-data/{keyword}")
    public void samplePublish(@PathVariable String keyword) {
        log.info("{}", keyword);
        producerScheduler.requestTwitterAPI(keyword);
        return;
    }
}