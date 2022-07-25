package com.example.producer.dao;

import com.example.producer.entity.MetaTable;
import com.example.producer.entity.SearchKeywordDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MetaTableDao extends JpaRepository<MetaTable, Integer> {
    Optional<MetaTable> findByColName(String colName);
}
