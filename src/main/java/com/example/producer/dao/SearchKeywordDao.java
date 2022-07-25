package com.example.producer.dao;

import com.example.producer.entity.SearchKeyword;
import com.example.producer.entity.SearchKeywordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchKeywordDao extends JpaRepository<SearchKeyword, Integer> {
    Optional<SearchKeyword> findByKeyword(String keyword);
}
