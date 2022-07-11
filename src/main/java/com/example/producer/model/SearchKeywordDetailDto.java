package com.example.producer.model;

import com.example.producer.entity.SearchKeywordDetail;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class SearchKeywordDetailDto {
    private int keywordDetailId;
    private String colCode;
    private int keywordId;
    private int quantity;
    private String searchDate;

    public SearchKeywordDetail toEntity() {
        return SearchKeywordDetail.builder()
                .keywordDetailId(keywordDetailId)
                .colCode(colCode)
                .keywordId(keywordId)
                .quantity(quantity)
                .searchDate(searchDate)
                .build();
    }

}
