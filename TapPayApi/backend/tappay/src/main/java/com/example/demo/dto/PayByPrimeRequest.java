package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayByPrimeRequest {
    private String prime;
    private Integer price;
    private String details;
}