package com.example.producer.service;

import java.io.IOException;

public interface TwitterService {
    public String getTwitterByTag(String tag) throws IOException;
}
