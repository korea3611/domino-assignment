package com.example.dominoassignment.repository;

import com.example.dominoassignment.dto.StockResponseDTO;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockNosqlRepository extends MongoRepository<StockResponseDTO, String> {
}
