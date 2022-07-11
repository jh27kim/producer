package com.example.producer.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name="search_keyword_detail")
public class SearchKeywordDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int search_keyword_detail;
    private int keywordDetailId;
    private String colCode;
    private int keywordId;
    private int quantity;
    private String searchDate;

    @Builder
    public SearchKeywordDetail(int keywordDetailId,
                               String colCode,
                               int keywordId,
                               int quantity,
                               String searchDate) {
        this.colCode = colCode;
        this.keywordId = keywordId;
        this.searchDate = searchDate;
        this.quantity = quantity;
        this.keywordDetailId = keywordDetailId;

    }
}
