package com.example.producer.processor;

import org.springframework.stereotype.Component;

@Component
public class DateConverter implements MessageProcessor{
    @Override
    public String convert(String message) {
        return message.replace("T", " ").replace("Z","");
    }
}
