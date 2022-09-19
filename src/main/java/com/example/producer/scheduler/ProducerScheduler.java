package com.example.producer.scheduler;

import com.example.producer.service.TwitterScheduler;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProducerScheduler {
    private final TwitterScheduler twitterScheduler;

    @Scheduled(fixedRate = 5000)
    public void requestTwitterAPI(String keyword) {
        twitterScheduler.doFixedDelayJob(keyword);
    }

}
