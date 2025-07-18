package com.example.orderservice.dtos;

import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderItem;
import com.example.orderservice.models.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class OrderDto {
    private Long orderId;
    private Date orderDate;
    private Double amount;
    private OrderStatus orderStatus;
    private List<OrderItemDto> orderItemDtos;

    public static OrderDto convertOrderDto(Order order) {
        OrderDto orderDto = convertOrderDto(order.getOrderItems());
        orderDto.setOrderId(order.getId());
        orderDto.setAmount(order.getAmount());
        orderDto.setOrderDate(order.getCreatedAt());
        orderDto.setOrderStatus(order.getOrderStatus());
        return orderDto;
    }

    public static List<OrderDto> from(List<Order> orders){
        List<OrderDto> orderDtos = new ArrayList<>();
        for(Order order:orders){
            OrderDto orderDto = OrderDto.convertOrderDto(order);
            orderDto.setOrderId(order.getId());
            orderDto.setAmount(order.getAmount());
            orderDto.setOrderDate(order.getCreatedAt());
            orderDto.setOrderStatus(order.getOrderStatus());
            orderDtos.add(orderDto);
        }
        return orderDtos;
    }

    private static OrderDto convertOrderDto(List<OrderItem> orderItems) {
        OrderDto orderDto = new OrderDto();
        List<OrderItemDto> orderItemDtos = new ArrayList<>();
        for (OrderItem orderItem : orderItems) {
            OrderItemDto orderItemDto = new OrderItemDto();
            orderItemDto.setProductId(orderItem.getProductId());
            orderItemDto.setQuantity(orderItem.getQuantity());
            orderItemDto.setUnitPrice(orderItem.getUnitPrice());
            orderItemDto.setProductNameSnapshot(orderItem.getProductNameSnapshot());
            orderItemDtos.add(orderItemDto);
        }
        orderDto.setOrderItemDtos(orderItemDtos);
        return orderDto;
    }
}
