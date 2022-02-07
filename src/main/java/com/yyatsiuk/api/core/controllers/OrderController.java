package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;
import com.yyatsiuk.api.core.models.request.OrderPartialUpdateRequest;
import com.yyatsiuk.api.core.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderCreateRequest payload) {
        OrderDto newOrder = orderService.save(payload);

        return ResponseEntity.ok(newOrder);
    }

    @GetMapping
    public ResponseEntity<List<OrderDto>> getAllOrders() {
        List<OrderDto> orders = orderService.findAll();
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        orderService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<OrderDto> partialUpdate(@PathVariable Long id,
                                                  @Valid @RequestBody OrderPartialUpdateRequest payload) {
        OrderStatus status = payload.getStatus();
        PaymentStatus paymentStatus = payload.getPaymentStatus();

        if (status != null) {
            return ResponseEntity.ok(orderService.updateStatus(id, status));
        }

        if (paymentStatus != null) {
            return ResponseEntity.ok(orderService.updatePaymentStatus(id, paymentStatus));
        }

        if (payload.getItems() != null) {
            OrderDto orderDto = orderService.updateOrderItems(id, payload.getItems());
            return ResponseEntity.ok(orderDto);
        }

        return ResponseEntity.badRequest().build();
    }

}
