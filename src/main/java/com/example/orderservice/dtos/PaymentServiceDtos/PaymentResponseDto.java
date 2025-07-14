package com.example.orderservice.dtos.PaymentServiceDtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentResponseDto {
    private long orderId;
    private double amount;
    private PaymentStatus paymentStatus;
}
