package com.example.producer.controller;

import com.example.producer.service.TwitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Slf4j
@RestController
public class SampleController {
    private static final String EXCHANGE_NAME = "sample.exchange";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    TwitterService ts;

    @Scheduled(fixedDelay = 5000) // 메소드 호출이 종료되는 시간에서 10000ms 이후 재 호출
    public void doFixedDelayJob() throws IOException {
        String message = ts.getTweets("Biden");
        System.out.println(message);
        //rabbitTemplate.convertAndSend(EXCHANGE_NAME, "twitter", message);
    }

    @GetMapping("/sample/queue")
    public String samplePublish() {
        log.info("server up !");
        return "message sending!";
    }
}
