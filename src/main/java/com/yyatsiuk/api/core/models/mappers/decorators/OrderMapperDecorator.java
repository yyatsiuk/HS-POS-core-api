package com.yyatsiuk.api.core.models.mappers.decorators;


import com.yyatsiuk.api.core.models.dto.LineItemDto;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.LineItem;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.mappers.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.List;

public abstract class OrderMapperDecorator implements OrderMapper {

    @Autowired
    @Qualifier("delegate")
    private OrderMapper orderMapper;

    @Override
    public OrderDto fromEntityToDto(Order entity) {
        OrderDto orderDto = orderMapper.fromEntityToDto(entity);
        List<LineItem> items = entity.getItems();

        List<LineItemDto> itemDtoList = getItemDtoList(items);

        orderDto.setLineItems(itemDtoList);

        BigDecimal subtotalAmount = itemDtoList.stream().map(LineItemDto::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add);
        orderDto.setSubtotalAmount(subtotalAmount);
        orderDto.setTotalAmount(subtotalAmount.subtract(entity.getPrepaymentAmount()));

        orderDto.setBranchNumber(entity.getDeliveryInformation().getBranchNumber());
        orderDto.setAddress(entity.getDeliveryInformation().getAddress());
        orderDto.setCourier(entity.getDeliveryInformation().getCourier().getName());
        orderDto.setTrackingCode(entity.getDeliveryInformation().getTrackingCode());

        return orderDto;
    }

    private List<LineItemDto> getItemDtoList(List<LineItem> items) {
        return items.stream().map(item -> {
            LineItemDto lineItemDto = new LineItemDto();
            lineItemDto.setProductId(item.getProduct().getId());
            lineItemDto.setName(item.getProduct().getName());
            lineItemDto.setCode(item.getProduct().getCode());
            lineItemDto.setImageUrl(item.getProduct().getImageUrl());
            lineItemDto.setQuantity(item.getQuantity());
            lineItemDto.setDiscountAmount(item.getDiscountAmount());

            BigDecimal unitAmount = item.getProduct().getPrice();
            BigDecimal totalAmount = unitAmount.multiply(BigDecimal.valueOf(item.getQuantity()));
            lineItemDto.setUnitAmount(unitAmount);
            lineItemDto.setTotalAmount(totalAmount);

            return lineItemDto;
        }).toList();
    }

}
