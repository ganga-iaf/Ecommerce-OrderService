package com.example.orderservice.utils;

import com.example.orderservice.dtos.OrderDto;
import com.example.orderservice.dtos.OrderItemDto;
import com.example.orderservice.dtos.PaymentServiceDtos.PaymentRequestDto;
import com.example.orderservice.dtos.PaymentServiceDtos.PaymentResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentServiceConnector {
    @Autowired
    private RestTemplate restTemplate;

    public String processPayment(OrderDto orderDto) {
        String url="http://PAYMENTSERVICEAPP/payment";
        PaymentRequestDto paymentRequestDto=new PaymentRequestDto();
        paymentRequestDto.setAmount(orderDto.getAmount());
        paymentRequestDto.setOrderId(orderDto.getOrderId());
        List<com.example.orderservice.dtos.PaymentServiceDtos.OrderItemDto> list=new ArrayList<>();
        for(OrderItemDto orderItemDto:orderDto.getOrderItemDtos()){
            com.example.orderservice.dtos.PaymentServiceDtos.OrderItemDto
                    paymentOrderItemDto=new com.example.orderservice.dtos.PaymentServiceDtos.OrderItemDto();
            paymentOrderItemDto.setQuantity(orderItemDto.getQuantity());
            paymentOrderItemDto.setUnitPrice(orderItemDto.getUnitPrice());
            paymentOrderItemDto.setProductName(orderItemDto.getProductNameSnapshot());
            list.add(paymentOrderItemDto);
        }
        paymentRequestDto.setOrderItemDtos(list);

        HttpEntity<PaymentRequestDto> request=new HttpEntity<>(paymentRequestDto);
        String paymentUrl=restTemplate.postForObject(url, request, String.class);
        return paymentUrl;
    }

    public PaymentResponseDto getPaymentStatus(String session_id) {
        try {
            PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
            PaymentResponseDto responseDto = restTemplate.getForObject("http://PAYMENTSERVICEAPP/payment/status/{session_id}", PaymentResponseDto.class, session_id);
            return responseDto;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
