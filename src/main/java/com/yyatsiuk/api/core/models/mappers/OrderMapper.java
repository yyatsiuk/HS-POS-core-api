package com.yyatsiuk.api.core.models.mappers;

import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.mappers.decorators.OrderMapperDecorator;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
@DecoratedWith(OrderMapperDecorator.class)
public interface OrderMapper {

    OrderDto fromEntityToDto(Order entity);

    Order fromDtoToEntity(OrderDto dto);

}
