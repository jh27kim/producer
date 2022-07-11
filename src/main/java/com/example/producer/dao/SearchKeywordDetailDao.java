package com.example.producer.dao;

import com.example.producer.entity.SearchKeywordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SearchKeywordDetailDao extends JpaRepository<SearchKeywordDetail, Integer> {
}
