package com.example.producer.controller;

import com.example.producer.service.TwitterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

@Slf4j
@RestController
public class SampleController {
    private static final String EXCHANGE_NAME = "sample.exchange";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    @Qualifier("twitterServiceHTTP")
    TwitterService ts;

    @Scheduled(fixedDelay = 2000) // 메소드 호출이 종료되는 시간에서 10000ms 이후 재 호출
    public void doFixedDelayJob() throws IOException {
        JSONArray message = ts.getTweets("Biden");
        System.out.println(message);

        FileWriter fw = new FileWriter("C:/Users/jai/IdeaProjects/producer/src/main/java/com/example/producer/controller/sample.txt", true);
        BufferedWriter bw = new BufferedWriter(fw);

        for (int i = 0; i < message.length(); i++) {
            JSONObject obj = message.getJSONObject(i);
            String id = obj.getString("id");
            String text = obj.getString("text");
            String date = obj.getString("date");


            bw.write(id + "|" + text + "|" + date);
            bw.newLine();
        }

        System.out.println("-----------------Executed-----------------");
        bw.close();

        // TODO Enable RabbitMQ
        //rabbitTemplate.convertAndSend(EXCHANGE_NAME, "twitter", message);
    }

    @GetMapping("/sample/queue")
    public String samplePublish() {
        log.info("server up !");
        return "message sending!";
    }
}
