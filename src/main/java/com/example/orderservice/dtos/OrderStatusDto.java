package com.example.orderservice.dtos;

import com.example.orderservice.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderStatusDto {
    private long orderId;
    private OrderStatus orderStatus;
}
