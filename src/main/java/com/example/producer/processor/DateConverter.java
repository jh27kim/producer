package com.example.producer.processor;

import org.springframework.stereotype.Component;

@Component
public class DateConverter implements MessageProcessor{
    @Override
    public String convert(String message) {
        // 2022-05-10T11:58:42.000Z
        return message.replace("T", " ").replace("Z","");
    }
}
