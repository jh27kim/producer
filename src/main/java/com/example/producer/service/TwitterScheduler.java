package com.example.producer.service;

import java.util.*;

import com.example.producer.dao.CustomMethodDao;
import com.example.producer.dao.MetaTableDao;
import com.example.producer.dao.SearchKeywordDao;
import com.example.producer.dao.SearchKeywordDetailDao;
import com.example.producer.entity.MetaTable;
import com.example.producer.entity.SearchKeyword;
import com.example.producer.entity.SearchKeywordDetail;
import com.example.producer.model.SentimentDto;
import com.google.common.base.Splitter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

import javax.transaction.Transactional;


@RequiredArgsConstructor
@Service
@Slf4j
public class TwitterScheduler {
    private final MetaTableDao metaTableDao;
    private final TwitterServiceHTTP twitterServiceHTTP;
    private static final String EXCHANGE_NAME = "sample.exchange";
    private final RabbitTemplate rabbitTemplate;
    private final SearchKeywordDetailDao searchKeywordDetailDao;
    private final SearchKeywordDao searchKeywordDao;
    private final CustomMethodDao customMethodDao;

    @Transactional
    public List<SentimentDto> doFixedDelayJob(String keyword) {
        JSONArray message = twitterServiceHTTP.getTweets(keyword);
        Map<String, ArrayList<String>> bodySentenceByDate = new HashMap<>();
        String message_log = ""; // for logging

        for (int i = 0; i < message.length(); i++) {
            JSONObject obj = message.getJSONObject(i);
            String id = obj.getString("id");
            String text = obj.getString("text");
            String date = obj.getString("date");
            String YYYY_MM_DD = date.split(" ")[0];
            bodySentenceByDate.put(YYYY_MM_DD, addTextToArray(bodySentenceByDate, YYYY_MM_DD, text));
            message_log += (id + "|" + text + "|" + date +"\n");
        }

        log.info(message_log);

        Map<String,Object> responseData=null;

        try {
            responseData = requestSentiment(bodySentenceByDate);
        }
        catch(JsonProcessingException e) {
            e.printStackTrace();
        }

        int keywordId = searchKeywordDao.findByKeyword(keyword)
                .orElseThrow(()-> new NullPointerException())
                .getKeywordId(); // TODO : Exception Handling
        System.out.println(keywordId);

        List<SentimentDto> sentimentDtoList = new ArrayList<>();

        for (Map.Entry<String, Object> entry : responseData.entrySet()) {

            String YYYY_MM_DD = entry.getKey();
            String YYYYMMDD = YYYY_MM_DD.replace("-","");
            SentimentDto sentimentDto = new SentimentDto(YYYYMMDD,new ArrayList<>(), new ArrayList<>());
            Map<String, String> keywordData = Splitter.on(", ")
                    .withKeyValueSeparator("=")
                    .split(entry.getValue().toString()
                            .replace("{","")
                            .replace("}",""));

            for (Map.Entry<String,String> keyEntry : keywordData.entrySet()) {
                String emotion = keyEntry.getKey();
                int quantity = Integer.parseInt(keyEntry.getValue());
                System.out.println(emotion);
                String colCode = metaTableDao.findByColName(emotion)
                        .orElseThrow(()-> new NullPointerException())
                        .getColCode();
                SearchKeywordDetail searchKeywordDetail = SearchKeywordDetail.builder()
                        .colCode(colCode)
                        .keywordId(keywordId)
                        .quantity(quantity)
                        .searchDate(YYYYMMDD)
                        .build();
                sentimentDto.getLabelList().add(emotion);
                sentimentDto.getQuantityList().add(quantity);
                searchKeywordDetailDao.save(searchKeywordDetail);
            }

            sentimentDtoList.add(sentimentDto);
        }
        return sentimentDtoList;
    }


    private ArrayList<String> addTextToArray(Map<String, ArrayList<String>> bodySentenceByDate,String YYYY_MM_DD, String text){
        ArrayList<String> bodySentence = bodySentenceByDate.containsKey(YYYY_MM_DD) ? bodySentenceByDate.get(YYYY_MM_DD) : new ArrayList<>();
        bodySentence.add(text);
        return bodySentence;
    }




    private Map<String,Object> requestSentiment(Map<String, ArrayList<String>> bodySentenceByDate) throws JsonProcessingException {
        System.out.println(bodySentenceByDate.toString());
        String url = "http://localhost:8000/sentiment";

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        ResponseEntity<Object> result = null;
        HttpEntity<String> requestEnty = null;

        Map<String, ArrayList<String>> bodyParamMap = new HashMap<String, ArrayList<String>>();
        Map<String, Object> responseData = new HashMap<>();
        String reqBodyData = null;

        for (Map.Entry<String, ArrayList<String>> entry : bodySentenceByDate.entrySet()) {
            String YYYY_MM_DD = entry.getKey();
            ArrayList<String> sentences = entry.getValue();
            bodyParamMap.put("sentences", sentences);
            reqBodyData = new ObjectMapper().writeValueAsString(bodyParamMap);
            requestEnty = new HttpEntity<>(reqBodyData, headers);
            result = restTemplate.postForEntity(url, requestEnty, Object.class);
            responseData.put(YYYY_MM_DD, result.getBody().toString());   // Change type to Json or Map, not toString()
        }

        return responseData;
    }

}