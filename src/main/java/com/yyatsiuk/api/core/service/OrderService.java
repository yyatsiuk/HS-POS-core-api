package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.request.OrderCreateRequest;

public interface OrderService {

    OrderDto save(OrderCreateRequest orderCreateRequest);

}
