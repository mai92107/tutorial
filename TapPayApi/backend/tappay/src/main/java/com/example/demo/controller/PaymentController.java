package com.example.demo.controller;

import com.example.demo.dto.PayByPrimeRequest;
import com.example.demo.dto.TapPayResponse;
import com.example.demo.exception.PaymentException;
import com.example.demo.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payment")
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<?> pay(@RequestBody PayByPrimeRequest request) {
        try {
            TapPayResponse response = paymentService.payByPrime(request);
            return ResponseEntity.ok(response);
        } catch (PaymentException e) {
            log.error("付款失敗: {}", e.getMessage());
            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());
        }
    }
}