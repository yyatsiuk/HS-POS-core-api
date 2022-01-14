package com.yyatsiuk.api.core.mappers;

import com.yyatsiuk.api.core.dto.CustomerDto;
import com.yyatsiuk.api.core.entities.Customer;
import com.yyatsiuk.api.core.web.request.CustomerCreateRequest;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    CustomerDto fromEntityToDto(Customer entity);

    Customer fromDtoToEntity(CustomerDto dto);

    CustomerDto fromCreateRequestToDto(CustomerCreateRequest request);

}
