package com.example.dominoassignment.service;

import com.example.dominoassignment.dto.StockResponseDTO;
import org.springframework.stereotype.Service;

@Service
public interface StockService {

    StockResponseDTO getLastFiveDaysSamsungStockDataSaveToRDB() throws Exception;

    StockResponseDTO getLastFiveDaysSamsungStockDataSaveToNosql() throws Exception;

}
