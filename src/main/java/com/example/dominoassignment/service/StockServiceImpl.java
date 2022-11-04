package com.example.dominoassignment.service;

import com.example.dominoassignment.dto.StockResponseDTO;
import com.example.dominoassignment.entity.Stock;
import com.example.dominoassignment.repository.StockNosqlRepository;
import com.example.dominoassignment.repository.StockRDBRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class StockServiceImpl implements StockService{
    private final StockNosqlRepository stockNosqlRepository;

    private final ObjectMapper objectMapper;
    private final StockRDBRepository stockRDBRepository;

    @Transactional
    @Override
    public StockResponseDTO getLastFiveDaysSamsungStockDataSaveToRDB() throws Exception {

        int arraySize = 5;

        String LastFiveDaysSamsungStockDataUrl = "https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d";

        ResponseEntity<JsonNode> LastFiveDaysSamsungStockDataJson = getResponseEntityWithJson(LastFiveDaysSamsungStockDataUrl);

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

        if (arraySize != lowList.size() || arraySize != closeList.size() ||
                arraySize != openList.size() || arraySize != highList.size() ||
                arraySize != volumeList.size() || arraySize != timeStampList.size())
            throw new Exception("배열 사이즈가 전부 동일하지않음");

        for (int i = 0; i < timeStampList.size(); i++) {

            Stock samsungStock = Stock.builder()
                    .company("samsung")
                    .timestamp(timeStampList.get(i))
                    .low(lowList.get(i))
                    .close(closeList.get(i))
                    .open(openList.get(i))
                    .high(highList.get(i))
                    .volume(volumeList.get(i))
                    .build();

            stockRDBRepository.save(samsungStock);
        }

        StockResponseDTO.Quote quote = StockResponseDTO.Quote.builder()
                .close(closeList)
                .low(lowList)
                .volume(volumeList)
                .open(openList)
                .high(highList)
                .build();

        StockResponseDTO.Indicator indicator = StockResponseDTO.Indicator.builder()
                .quote(quote)
                .build();

        return StockResponseDTO.builder()
                .timestamp(timeStampList)
                .indicator(indicator)
                .build();
    }

    @Transactional
    @Override
    public StockResponseDTO getLastFiveDaysSamsungStockDataSaveToNosql() throws Exception {

        String LastFiveDaysSamsungStockDataUrl = "https://query1.finance.yahoo.com/v8/finance/chart/005930.KS?interval=1d&range=5d";

        ResponseEntity<JsonNode> LastFiveDaysSamsungStockDataJson = getResponseEntityWithJson(LastFiveDaysSamsungStockDataUrl);

        List<Integer> timeStampList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("timestamp"), List.class);

        List<Double> lowList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("low"), List.class);

        List<Double> closeList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("close"), List.class);

        List<Double> openList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("open"), List.class);

        List<Double> highList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("high"), List.class);

        List<Integer> volumeList = objectMapper.treeToValue(
                LastFiveDaysSamsungStockDataJson.getBody().get("chart").get("result").get(0)
                        .get("indicators").get("quote").get(0).get("volume"), List.class);

        if (timeStampList.size() != lowList.size() || timeStampList.size() != closeList.size() ||
                timeStampList.size() != openList.size() || timeStampList.size() != highList.size() ||
                timeStampList.size() != volumeList.size()) throw new Exception("배열 사이즈가 전부 동일하지않음");

        StockResponseDTO.Quote quote = StockResponseDTO.Quote.builder()
                .close(closeList)
                .low(lowList)
                .volume(volumeList)
                .open(openList)
                .high(highList)
                .build();

        StockResponseDTO.Indicator indicator = StockResponseDTO.Indicator.builder()
                .quote(quote)
                .build();

        StockResponseDTO stockResponseDTO = StockResponseDTO.builder()
                .timestamp(timeStampList)
                .indicator(indicator)
                .build();

        stockNosqlRepository.insert(stockResponseDTO);

        return stockResponseDTO;
    }

    public ResponseEntity<JsonNode> getResponseEntityWithJson(String url) throws Exception {

        ResponseEntity<JsonNode> reponseEntityWithJson;
        RestTemplate restTemplate = new RestTemplate();
        try {
            reponseEntityWithJson = restTemplate.getForEntity(url, JsonNode.class);
            return reponseEntityWithJson;
        } catch (HttpClientErrorException e) {
            log.info("HttpClientErrorException : " + e.getResponseBodyAsString());
            throw new Exception(e.getResponseBodyAsString());

        } catch (HttpServerErrorException e) {
            log.info("HttpServerErrorException : " + e.getResponseBodyAsString());
            throw new Exception(e.getResponseBodyAsString());
        }catch(Exception e) {
            log.info("Exception : " + Arrays.toString(e.getStackTrace()));
            throw new Exception(Arrays.toString(e.getStackTrace()));
        }
    }
}
