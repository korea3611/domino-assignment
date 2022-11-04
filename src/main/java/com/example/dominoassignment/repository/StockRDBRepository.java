package com.example.dominoassignment.repository;

import com.example.dominoassignment.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRDBRepository extends JpaRepository<Stock, Long> {
}
