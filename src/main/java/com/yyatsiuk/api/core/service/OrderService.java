package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.request.LineItemRequest;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderCreateRequest orderCreateRequest);

    List<OrderDto> findAll();

    OrderDto findById(Long id);

    void deleteById(Long id);

    OrderDto updateStatus(Long id, OrderStatus status);

    OrderDto updatePaymentStatus(Long id, PaymentStatus paymentStatus);

    OrderDto updateOrderItems(Long id, List<LineItemRequest> items);

}
