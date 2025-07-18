package com.example.orderservice.controllers;

import com.example.orderservice.dtos.OrderDto;
import com.example.orderservice.dtos.OrderStatusDto;
import com.example.orderservice.exceptions.TokenMissingException;
import com.example.orderservice.models.Order;
import com.example.orderservice.models.OrderStatus;
import com.example.orderservice.services.OrderService;
import com.example.orderservice.utils.JwtTokenValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private JwtTokenValidator jwtTokenValidator;


    @GetMapping()
    public ResponseEntity<List<OrderDto>> getOrders(@RequestHeader(HttpHeaders.AUTHORIZATION) String token) throws TokenMissingException {
           long userId = validateToken(token);
           List<Order> orders=orderService.getOrders(userId);
           return new ResponseEntity<>(OrderDto.from(orders), HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable long orderId) throws TokenMissingException {
        long userId = validateToken(token);
        Order order=orderService.getOrder(userId,orderId);
        return new ResponseEntity<>(OrderDto.convertOrderDto(order), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> createOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @RequestBody OrderDto orderDto) throws TokenMissingException {
        long userId = validateToken(token);
        String url=orderService.createOrder(userId,orderDto);
        //return new ResponseEntity<>(OrderDto.convertOrderDto(order), HttpStatus.CREATED);
        return new ResponseEntity<>(url, HttpStatus.CREATED);
    }

    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDto> cancelOrder(@RequestHeader(HttpHeaders.AUTHORIZATION) String token, @PathVariable("orderId") long orderId) throws TokenMissingException {
        long userId = validateToken(token);
        Order order=orderService.cancelOrder(userId,orderId);
        return new ResponseEntity<>(OrderDto.convertOrderDto(order), HttpStatus.OK);
    }

    @GetMapping("/paymentstatus/{session_id}")
    public ResponseEntity<OrderStatusDto> getOrderStatus(@RequestHeader(HttpHeaders.AUTHORIZATION) String token,@PathVariable String session_id) throws  TokenMissingException {
        validateToken(token);
        OrderStatusDto orderStatusDto=orderService.getOrderStatus(session_id);
        return new ResponseEntity<>(orderStatusDto,HttpStatus.OK);
    }

    private long validateToken(String token) throws TokenMissingException {
        if(token == null || token.isEmpty()){
            throw new TokenMissingException("token is missing");
        }
        return jwtTokenValidator.validateAndGetUserId(token);
    }


}
