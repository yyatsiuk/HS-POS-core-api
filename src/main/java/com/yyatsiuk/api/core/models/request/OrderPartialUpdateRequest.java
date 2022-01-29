package com.yyatsiuk.api.core.models.request;

import com.yyatsiuk.api.core.enumerations.OrderStatus;
import com.yyatsiuk.api.core.enumerations.PaymentStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class OrderPartialUpdateRequest {

    OrderStatus status;

    PaymentStatus paymentStatus;

    private List<LineItemRequest> items;

}
