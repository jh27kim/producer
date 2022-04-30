package com.example.producer.service;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;
import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

@Service
public class TwitterServiceImpl implements TwitterService {
    public String getTwitterByTag(String tag) throws IOException {
//        BufferedWriter bw = null;
//        try {
//            bw = new BufferedWriter(new FileWriter(tag + ".txt"));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String msg = "";

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("KyL8AiJizcj6aOChuK6x45exb")
                .setOAuthConsumerSecret("L7LG7xquNopWtm2Ndh8z1FLMoBEsiNsAgBTdzGVRyXZp9Hcatp")
                .setOAuthAccessToken("132914463-Q2SuIX8SZekBfqRDDZf2HVD7XcHOzHXlOfmLQfxv")
                .setOAuthAccessTokenSecret("SwVreEE3N8fIUwKyMvLCODvU4DdFGU8I3ExVVBbb0Z6cT");
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        Query query = new Query(tag);
        QueryResult result = null;

        try {
            result = twitter.search(query);
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        for (Status status : result.getTweets()) {
            msg += "@" + status.getUser().getScreenName() + ":"
                    + status.getText() + "|||" + "\r\n";
        }

//        bw.close();

        return msg;

    }
}


