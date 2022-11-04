package com.example.dominoassignment.dto;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "samsung_stock_data")
public class StockResponseDTO {
    private List<Integer> timestamp;
    private Indicator indicator;

    @Builder
    @Getter
    public static class Indicator {
        private Quote quote;
    }

    @Builder
    @Getter
    public static class Quote {
        private List<Double> close;
        private List<Double> low;
        private List<Integer> volume;
        private List<Double> open;
        private List<Double> high;
    }

    @Builder
    public StockResponseDTO(List<Integer> timestamp, Indicator indicator) {
        this.timestamp = timestamp;
        this.indicator = indicator;
    }
}
