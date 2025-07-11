package com.example.orderservice.repositories;

import com.example.orderservice.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order,Long> {
    List<Order> findAllByUserId(long userId);
}
