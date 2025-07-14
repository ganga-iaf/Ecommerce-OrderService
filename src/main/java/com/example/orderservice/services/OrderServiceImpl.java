package com.example.orderservice.services;

import com.example.orderservice.dtos.OrderDto;
import com.example.orderservice.dtos.OrderItemDto;
import com.example.orderservice.dtos.OrderStatusDto;
import com.example.orderservice.dtos.PaymentServiceDtos.PaymentResponseDto;
import com.example.orderservice.dtos.PaymentServiceDtos.PaymentStatus;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import com.example.orderservice.models.OrderStatus;
import com.example.orderservice.repositories.OrderRepo;
import com.example.orderservice.utils.PaymentServiceConnector;
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

    @Autowired
    private PaymentServiceConnector paymentServiceConnector;



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
    public String createOrder(long userId,OrderDto orderDto) {
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
        orderRepo.save(order);

        return paymentServiceConnector.processPayment(OrderDto.convertOrderDto(order));
        //return orderRepo.save(order);
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

    @Override
    public OrderStatusDto getOrderStatus(String session_id) {
        PaymentResponseDto responseDto=paymentServiceConnector.getPaymentStatus(session_id);
        Optional<Order> orderOptional=orderRepo.findById(responseDto.getOrderId());
        if(orderOptional.isEmpty()){
            throw new RuntimeException("Order Not Found");
        }
        Order order=orderOptional.get();
        if(responseDto.getPaymentStatus().equals(PaymentStatus.Completed)){
            order.setOrderStatus(OrderStatus.Confirmed);
        }else if(responseDto.getPaymentStatus().equals(PaymentStatus.Failed)){
            order.setOrderStatus(OrderStatus.PaymentFailed);
        }
        orderRepo.save(order);
        OrderStatusDto orderStatusDto=new OrderStatusDto();
        orderStatusDto.setOrderId(order.getId());
        orderStatusDto.setOrderStatus(order.getOrderStatus());
        return orderStatusDto;
    }

}
