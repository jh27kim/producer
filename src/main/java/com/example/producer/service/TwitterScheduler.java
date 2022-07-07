package com.example.producer.service;

import com.example.producer.service.nlp.NLPService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import twitter4j.JSONObject;

import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class TwitterScheduler {
    private final TwitterServiceHTTP twitterServiceHTTP;
    private static final String EXCHANGE_NAME = "sample.exchange";
    private final RabbitTemplate rabbitTemplate;
    private final NLPService nlpService;

    public String doFixedDelayJob(String keyword) {
        JSONObject message = twitterServiceHTTP.getTweets(keyword);

        System.out.println(message);

        String returnString = "";

        if (((ArrayList)message.get("text")).size() == 0) {
            return "";
        }

        List<String> tweetTexts = ((ArrayList)message.get("text"));
        List<String> tweetDates = ((ArrayList)message.get("date"));
        List<String> tweetIds = ((ArrayList)message.get("id"));

        for (int i = 0; i < tweetTexts.size(); i++) {
            String id = tweetIds.get(i);
            String text = tweetTexts.get(i);
            String date = tweetDates.get(i);

            returnString +=  (id + "|" + text + "|" + date);
        }

        nlpService.getSentiment(message);

        return returnString;

//        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "twitter", returnString);
    }
}
