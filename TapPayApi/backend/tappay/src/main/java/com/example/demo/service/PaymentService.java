package com.example.demo.service;

import com.example.demo.dto.PayByPrimeRequest;
import com.example.demo.dto.TapPayRequest;
import com.example.demo.dto.TapPayResponse;
import com.example.demo.exception.PaymentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    @Value("${tappay.partner-key}")
    private String partnerKey;

    @Value("${tappay.merchant-id}")
    private String merchantId;

    @Value("${tappay.api-url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public TapPayResponse payByPrime(PayByPrimeRequest request) {
        log.info("開始處理訂單付款: {}", request.getDetails());

        TapPayRequest tapPayRequest = TapPayRequest.builder()
                .prime(request.getPrime())
                .partner_key(partnerKey)
                .merchant_id(merchantId)
                .amount(request.getPrice())
                .cardholder(TapPayRequest.CardHolder.builder().email("abc@123").name("王小明").phone_number("09000000").build())
                .details(request.getDetails())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-api-key", partnerKey);

        HttpEntity<TapPayRequest> entity = new HttpEntity<>(tapPayRequest, headers);

        try {
            ResponseEntity<TapPayResponse> response = restTemplate.exchange(
                    apiUrl,
                    HttpMethod.POST,
                    entity,
                    TapPayResponse.class
            );

            TapPayResponse tapPayResponse = response.getBody();
            if (tapPayResponse != null && tapPayResponse.getStatus() == 0) {
                log.info("付款成功，訂單號: {}", request.getDetails());
                return tapPayResponse;
            } else {
                String errorMsg = tapPayResponse != null ? tapPayResponse.getMsg() : "Unknown error";
                log.error("付款失敗: {}", errorMsg);
                throw new PaymentException(errorMsg);
            }
        } catch (RestClientException e) {
            log.error("TapPay API 呼叫失敗", e);
            throw new PaymentException("支付處理失敗: " + e.getMessage());
        }
    }
}