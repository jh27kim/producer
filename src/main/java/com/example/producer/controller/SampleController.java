package com.example.producer.controller;

import com.example.producer.service.TwitterScheduler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SampleController {
    private final TwitterScheduler twitterScheduler;

    @GetMapping("/sample/queue")
    public String samplePublish(String keyword) {
        log.info("server up !");
        // TODO Get Keyword from User
        // TODO Make this Async
        twitterScheduler.doFixedDelayJob();

        return "message sending!";
    }
}
