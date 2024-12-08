package com.example.demo.dto;

import lombok.Data;

@Data
public class TapPayResponse {
    private Integer status;
    private String msg;
    private RecordInfo record;

    @Data
    public static class RecordInfo {
        private String auth_code;
        private String card_info;
        private String transaction_time_millis;
        private String bank_transaction_id;
    }
}
