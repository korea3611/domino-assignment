package com.example.dominoassignment.service;

import com.example.dominoassignment.dto.StockResponseDTO;
import com.example.dominoassignment.entity.Stock;
import com.example.dominoassignment.repository.StockNosqlRepository;
import com.example.dominoassignment.repository.StockRDBRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @Mock
    StockRDBRepository stockRDBRepository;

    @Test
    void getLastFiveDaysSamsungStockDataSaveToRDB() {

        Stock testStock = Stock.builder()
                .company("samsung")
                .timestamp(123456789)
                .low(10000.0)
                .close(10001.0)
                .open(10002.0)
                .high(10003.0)
                .volume(12345)
                .build();

        when(stockRDBRepository.save(any())).thenReturn(testStock);

        Stock stock = stockRDBRepository.save(Stock.builder()
                .company("samsung")
                .timestamp(123456789)
                .low(10000.0)
                .close(10001.0)
                .open(10002.0)
                .high(10003.0)
                .volume(12345)
                .build());

        verify(stockRDBRepository, times(1)).save(any());
        assertThat(stock, equalTo(testStock));
    }

}