package com.example.producer.service;

import com.example.producer.processor.*;
import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import twitter4j.JSONArray;
import twitter4j.JSONObject;

@Service
public class TwitterServiceHTTP implements TwitterService{
    @Value("${token}")
    private String bearerToken;
    private final Converter converter;
    private final DateConverter dateConverter;

    @Autowired
    public TwitterServiceHTTP(Converter converter, DateConverter dateConverter) {
        this.converter = converter;
        this.dateConverter = dateConverter;
    }

    @Override
    public JSONObject getTweets(String tag)  {

        String searchResponse = null;
        JSONObject result = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        try {
            URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/recent");
            ArrayList<NameValuePair> queryParameters;
            queryParameters = new ArrayList<>();
            queryParameters.add(new BasicNameValuePair("query", tag));
            queryParameters.add(new BasicNameValuePair("tweet.fields", "created_at,lang"));

            uriBuilder.addParameters(queryParameters);

            HttpGet httpGet = new HttpGet(uriBuilder.build());

            httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
            httpGet.setHeader("Content-Type", "application/json");

            HttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                searchResponse = EntityUtils.toString(entity, "UTF-8");
                result = new JSONObject(searchResponse);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject finalObject = new JSONObject();
        List<String> tweetTexts = new ArrayList<>();
        List<String> tweetDates = new ArrayList<>();
        List<String> tweetIds = new ArrayList<>();

        JSONArray tempObject = result.getJSONArray("data");
        for (int i=0; i<tempObject.length(); i++) {
            JSONObject oj = tempObject.getJSONObject(i);
            if (!oj.getString("lang").equals("en")) {
                continue;
            }

            if(oj.getString("text").contains("RT")){
                continue;
            }

            String tweet = oj.getString("text");
            tweet = converter.convert(tweet);

            String date = oj.getString("created_at");
            date = dateConverter.convert(date);

            tweetTexts.add(tweet);
            tweetDates.add(date);
            tweetIds.add(oj.getString("id"));
        }

        finalObject.put("text", tweetTexts);
        finalObject.put("id", tweetIds);
        finalObject.put("date", tweetDates);

        return finalObject;
    }
}
