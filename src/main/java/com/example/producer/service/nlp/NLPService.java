package com.example.producer.service.nlp;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import twitter4j.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

@Service
public class NLPService {
    public JSONObject getSentiment(JSONObject tweets) {
        String searchResponse = null;
        JSONObject result = null;

        HttpClient httpClient = HttpClients.custom()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setCookieSpec(CookieSpecs.STANDARD).build())
                .build();

        try {
            URIBuilder uriBuilder = new URIBuilder("http://localhost:8000/sentiment");

            HttpPost httpPost = new HttpPost(uriBuilder.build());

            httpPost.setHeader("Content-Type", "application/json");
            httpPost.setEntity(tweets.toString());

            HttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();

            if (null != entity) {
                searchResponse = EntityUtils.toString(entity, "UTF-8");
                result = new JSONObject(searchResponse);

                System.out.println(result);
            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
}
