package com.example.dominoassignment.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stock_id")
    private Long id;

    @Column(name = "company")
    private String company;

    @Column(name = "timestamp")
    private Integer timestamp;

    @Column(name = "low")
    private Double low;

    @Column(name = "close")
    private Double close;

    @Column(name = "volume")
    private Integer volume;

    @Column(name = "open")
    private Double open;

    @Column(name = "high")
    private Double high;

    @Builder
    public Stock(String company, Integer timestamp, Double low, Double close, Integer volume, Double open, Double high) {
        this.company = company;
        this.timestamp = timestamp;
        this.low = low;
        this.close = close;
        this.volume = volume;
        this.open = open;
        this.high = high;
    }
}
