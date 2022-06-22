package com.example.producer.service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import twitter4j.JSONArray;
import twitter4j.JSONObject;


@RequiredArgsConstructor
@Service
public class TwitterScheduler {
    private final TwitterServiceHTTP twitterServiceHTTP;
    private static final String EXCHANGE_NAME = "sample.exchange";
    private final RabbitTemplate rabbitTemplate;

    public String doFixedDelayJob(String keyword) {
        JSONArray message = twitterServiceHTTP.getTweets(keyword);
        String returnString = "";

        for (int i = 0; i < message.length(); i++) {
            JSONObject obj = message.getJSONObject(i);
            String id = obj.getString("id");
            String text = obj.getString("text");
            String date = obj.getString("date");

            returnString += (id + "|" + text + "|" + date);
        }

        // TODO 준승이 Python model {keyword: xxx, agree: int, disagree: int, neutral: int}

        System.out.println(returnString);

        return returnString;

//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "twitter", returnString);
    }
}
