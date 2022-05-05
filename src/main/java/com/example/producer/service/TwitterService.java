package com.example.producer.service;

import java.io.IOException;

public interface TwitterService {
    public String getTweets(String tag) throws IOException;
}
