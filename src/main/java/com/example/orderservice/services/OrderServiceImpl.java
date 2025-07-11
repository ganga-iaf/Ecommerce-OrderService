package com.example.orderservice.services;

import com.example.orderservice.dtos.OrderDto;
import com.example.orderservice.dtos.OrderItemDto;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import com.example.orderservice.models.OrderStatus;
import com.example.orderservice.repositories.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepo orderRepo;



    @Override
    public List<Order> getOrders(long userId) {
        return orderRepo.findAllByUserId(userId);
    }

    @Override
    public Order getOrder(long userId,long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("Order Not Found");
        }
        return order.get();

    }

    @Override
    public Order createOrder(long userId,OrderDto orderDto) {
        Order order = new Order();
        order.setOrderStatus(OrderStatus.Created);
        order.setUserId(userId);
        order.setCreatedAt(new Date());
        List<OrderItem>  orderItems = new ArrayList<>();
        double totalPrice = 0;
        for (OrderItemDto orderItemDto : orderDto.getOrderItemDtos()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setProductId(orderItemDto.getProductId());
            orderItem.setQuantity(orderItemDto.getQuantity());
            orderItem.setUnitPrice(orderItemDto.getUnitPrice());
            totalPrice+=orderItemDto.getUnitPrice()*orderItemDto.getQuantity();
            orderItem.setProductNameSnapshot(orderItemDto.getProductNameSnapshot());
            orderItems.add(orderItem);
            orderItem.setOrder(order);
        }
        order.setOrderItems(orderItems);
        order.setAmount(totalPrice);
        return orderRepo.save(order);
    }

    @Override
    public Order cancelOrder(long userId,long orderId) {
        Optional<Order> order = orderRepo.findById(orderId);
        if(order.isEmpty()){
            throw new RuntimeException("Order Not Found");
        }
        Order cancelOrder = order.get();
        if(cancelOrder.getUserId()==userId){
            cancelOrder.setOrderStatus(OrderStatus.Cancelled);
            orderRepo.save(cancelOrder);
            return cancelOrder;
        }
        throw new RuntimeException("");
    }

}
