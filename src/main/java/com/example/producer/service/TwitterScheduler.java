package com.example.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.JSONArray;
import twitter4j.JSONObject;


@RequiredArgsConstructor
@Service
public class TwitterScheduler {
    private final TwitterServiceHTTP twitterServiceHTTP;
    private static final String EXCHANGE_NAME = "sample.exchange";
    private final RabbitTemplate rabbitTemplate;

    @Scheduled(fixedDelay = 2000) // 메소드 호출이 종료되는 시간에서 10000ms 이후 재 호출
    public void doFixedDelayJob() {
        JSONArray message = twitterServiceHTTP.getTweets("Biden");
        String returnString = "";

        for (int i = 0; i < message.length(); i++) {
            JSONObject obj = message.getJSONObject(i);
            String id = obj.getString("id");
            String text = obj.getString("text");
            String date = obj.getString("date");

            returnString += (id + "|" + text + "|" + date);
        }

        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "twitter", returnString);
    }
}
