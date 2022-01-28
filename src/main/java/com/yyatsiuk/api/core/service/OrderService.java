package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderCreateRequest orderCreateRequest);

    List<OrderDto> findAll();

    OrderDto findById(Long id);

    void deleteById(Long id);
}
