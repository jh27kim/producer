package com.example.producer.processor;

import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RemoveRedundancyMessageProcessor implements MessageProcessor{
    @Override
    public String convert(String message) {
        Pattern pattern = Pattern.compile("\"'(.*?)'\"");
        Matcher matcher = pattern.matcher(message);
        return message.replace(matcher.group(1), "");
    }
}
