package com.example.dominoassignment.controller;

import com.example.dominoassignment.dto.StockResponseDTO;
import com.example.dominoassignment.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @GetMapping("/RDB/samsung")
    public ResponseEntity<StockResponseDTO> getSamsungStockDataByRDB() throws Exception {
        StockResponseDTO lastFiveDaysSamsungStockData = stockService.getLastFiveDaysSamsungStockDataSaveToRDB();
        return new ResponseEntity<>(lastFiveDaysSamsungStockData, HttpStatus.OK);
    }

    @GetMapping("/noSQL/samsung")
    public ResponseEntity<StockResponseDTO> getSamsungStockDataByNosql() throws Exception {
        StockResponseDTO lastFiveDaysSamsungStockDataByNosql = stockService.getLastFiveDaysSamsungStockDataSaveToNosql();
        return new ResponseEntity<>(lastFiveDaysSamsungStockDataByNosql, HttpStatus.OK);
    }

}
