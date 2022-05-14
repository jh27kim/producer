package com.example.producer.processor;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RemoveRedundancyMessageProcessor implements MessageProcessor{
    @Override
    public String convert(String message) {
        // pattern에 regex 추가
        Pattern pattern = Pattern.compile("&amp;|^RT @\\w+:|https:\\W+\\w+\\W+\\w+\\W+\\w+|@\\w+");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            message = message.replace(matcher.group(), "");
        }
        System.out.println(message);
        return message;
    }
}
