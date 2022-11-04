package com.example.dominoassignment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(MockitoExtension.class)
@DisplayName("삼성 주식 API test")
class SamsungStockApiTest {

    ResponseEntity<JsonNode> LastFiveDaysSamsungStockDataJson;

    @BeforeEach
    void 최근5일치_삼성_주식_API_정상동작확인() {

        String url = "https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d";
        RestTemplate restTemplate = new RestTemplate();
        LastFiveDaysSamsungStockDataJson = restTemplate.getForEntity(url, JsonNode.class);
        assertTrue(LastFiveDaysSamsungStockDataJson.getStatusCode().is2xxSuccessful());

    }

    @Test
    void 최근5일치_삼성_주식_API_스펙테스트() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();

        List<Integer> timeStampList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("timestamp"), Integer[].class)).toList();

        List<Double> lowList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("low"), Double[].class)).toList();

        List<Double> closeList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("close"), Double[].class)).toList();

        List<Double> openList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("open"), Double[].class)).toList();

        List<Double> highList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("high"), Double[].class)).toList();

        List<Integer> volumeList = Arrays.stream(objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("volume"), Integer[].class)).toList();

        System.out.println(timeStampList);

        assertThat(timeStampList, hasSize(5));
        assertThat(lowList, hasSize(5));
        assertThat(closeList, hasSize(5));
        assertThat(openList, hasSize(5));
        assertThat(highList, hasSize(5));
        assertThat(volumeList, hasSize(5));
    }

}