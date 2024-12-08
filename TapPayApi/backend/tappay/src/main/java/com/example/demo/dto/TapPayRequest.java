package com.example.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TapPayRequest {
    private String prime;
    private String partner_key;
    private String merchant_id;
    private Integer amount;
    private String details;
    private CardHolder cardholder;

    @Data
    @Builder
    public static class CardHolder {
        private String phone_number;
        private String name;
        private String email;
    }
}