package com.yyatsiuk.api.core.models.mappers;

import com.yyatsiuk.api.core.models.dto.CustomerDto;
import com.yyatsiuk.api.core.models.dto.CustomerNoteDto;
import com.yyatsiuk.api.core.models.entities.Customer;
import com.yyatsiuk.api.core.models.request.CustomerCreateRequest;
import com.yyatsiuk.api.core.models.request.CustomerUpdateRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto fromEntityToDto(Customer entity);

    Customer fromDtoToEntity(CustomerDto dto);

    CustomerDto fromUpdateRequestToDto(CustomerUpdateRequest request, Long id);

    CustomerDto fromCreateRequestToDto(CustomerCreateRequest request);

}
