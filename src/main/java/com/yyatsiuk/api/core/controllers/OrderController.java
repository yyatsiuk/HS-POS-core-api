package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.models.request.OrderCreateRequest;
import com.yyatsiuk.api.core.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Void> createOrder(@RequestBody OrderCreateRequest payload) {
        orderService.save(payload);
        return ResponseEntity.ok().build();
    }
}
