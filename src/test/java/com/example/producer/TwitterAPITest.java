package com.example.producer;

import com.example.producer.service.TwitterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.io.IOException;

@SpringBootTest
public class TwitterAPITest {
    @Autowired
    private TwitterService ts;



    @Test
    void APITest() throws IOException {
        String msg = ts.getTweets("biden");
        System.out.println(msg);
        assertEquals(1, 1);
    }
}
