package com.example.producer.processor;

import org.springframework.stereotype.Component;

@Component
public class LowerCaseMessageProcessor implements MessageProcessor{
    @Override
    public String convert(String message) {
        return message.toLowerCase();
    }
}
