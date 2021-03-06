package com.example.producer.processor;

import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RemoveRedundancyMessageProcessor implements MessageProcessor{
    @Override
    public String convert(String message) {
        Pattern pattern = Pattern.compile("&amp;|^rt @\\w+:|https://\\w+\\W+\\w+/\\w|@\\w+|(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+");
        Pattern patternRT = Pattern.compile("^rt");
        Matcher matcher = pattern.matcher(message);
        Matcher matcherRT = patternRT.matcher(message);
        while (matcher.find()) {
            message = matcher.replaceAll("");
        }
        message = EmojiParser.removeAllEmojis(message);
        message = message.replace("\n","");
        return message;
    }
}
