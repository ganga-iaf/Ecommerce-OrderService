package com.example.orderservice.dtos;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class OrderItemDto {
    private long productId;
    private String productNameSnapshot;
    private double unitPrice;
    private int quantity;
    private Date orderDate;
}
