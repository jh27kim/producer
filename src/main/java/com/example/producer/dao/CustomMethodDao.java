package com.example.producer.dao;

import com.example.producer.entity.SearchKeywordDetail;
import com.example.producer.model.SentimentDto;
import com.google.common.base.Splitter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class CustomMethodDao {
    private final SearchKeywordDao searchKeywordDao;
    private final SearchKeywordDetailDao searchKeywordDetailDao;
    private final MetaTableDao metaTableDao;

    //  responseData :  {2022-07-11={Strongly Positive=2, Positive=0, Negative=0, Strongly Negative=0, Neutral=1}}
    @Transactional(rollbackOn = NullPointerException.class)
    public List<SentimentDto> saveResponse(Map<String,Object> responseData, String keyword){
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
}
