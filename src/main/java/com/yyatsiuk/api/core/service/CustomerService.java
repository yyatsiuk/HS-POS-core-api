package com.yyatsiuk.api.core.service;

import com.yyatsiuk.api.core.models.dto.CustomerDto;
import com.yyatsiuk.api.core.models.dto.CustomerNoteDto;
import com.yyatsiuk.api.core.models.dto.OrderDto;

import java.util.List;

public interface CustomerService {

    Long save(CustomerDto customerDto);

    CustomerDto update(CustomerDto customerDto);

    CustomerDto findById(Long id);

    List<CustomerDto> findAll();

    void delete(Long id);

    List<OrderDto> findAllOrders(Long id);

    List<CustomerNoteDto> findCustomerNotesById(Long customerId);

    CustomerNoteDto createCustomerNote(Long customerId, Long userId, String note);

    void deleteCustomerNote(Long customerId, Long noteId);
}
