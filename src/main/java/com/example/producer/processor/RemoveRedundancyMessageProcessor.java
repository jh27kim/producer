package com.example.producer.processor;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RemoveRedundancyMessageProcessor implements MessageProcessor{
    @Override
    public String convert(String message) {
        // pattern에 regex 추가
        Pattern pattern = Pattern.compile("&amp;|^rt @\\w+:|https://\\w+\\W+\\w+/\\w|@\\w+|(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            message = matcher.replaceAll("");
        }
        message = EmojiParser.removeAllEmojis(message);
        message = message.replace("\n","");
        System.out.println(message);
        return message;
    }
}
