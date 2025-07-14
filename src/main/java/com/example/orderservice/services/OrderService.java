package com.example.orderservice.services;

import com.example.orderservice.dtos.OrderDto;
import com.example.orderservice.dtos.OrderStatusDto;
import com.example.orderservice.models.Order;

import java.util.List;

public interface OrderService {
    List<Order>  getOrders(long userId);
    Order getOrder(long userId,long orderId);
    String createOrder(long userId,OrderDto order);
    Order cancelOrder(long userId,long orderId);
    OrderStatusDto getOrderStatus(String session_id);
}
