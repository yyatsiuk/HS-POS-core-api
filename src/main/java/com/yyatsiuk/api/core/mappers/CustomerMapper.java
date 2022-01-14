package com.yyatsiuk.api.core.mappers;

import com.yyatsiuk.api.core.dto.CustomerDto;
import com.yyatsiuk.api.core.entities.Customer;
import com.yyatsiuk.api.core.web.request.CustomerCreateUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto fromEntityToDto(Customer entity);

    Customer fromDtoToEntity(CustomerDto dto);

    CustomerDto fromCreateUpdateRequestToDto(CustomerCreateUpdateRequest request, Long id);

    CustomerDto fromCreateUpdateRequestToDto(CustomerCreateUpdateRequest request);

}
