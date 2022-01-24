package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.CustomerDto;

import java.util.List;

public interface CustomerService {

    Long save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    CustomerDto findById(Long id);

    List<CustomerDto> findAll();

    void delete(Long id);

}
