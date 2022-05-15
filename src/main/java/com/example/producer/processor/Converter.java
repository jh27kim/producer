package com.example.producer.processor;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Converter {
    private final LowerCaseMessageProcessor lp;
    private final RemoveRedundancyMessageProcessor rp;

    public String convert(String message) {
        message = lp.convert(message);
        message = rp.convert(message);
        return message;
    }
}
