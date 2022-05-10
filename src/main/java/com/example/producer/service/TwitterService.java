package com.example.producer.service;

import twitter4j.JSONArray;
import twitter4j.JSONObject;

import java.io.IOException;

public interface TwitterService {
    public JSONArray getTweets(String tag) throws IOException;
}
