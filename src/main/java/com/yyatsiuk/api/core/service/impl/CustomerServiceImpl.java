package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.dto.CustomerDto;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.Customer;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.mappers.CustomerMapper;
import com.yyatsiuk.api.core.models.mappers.OrderMapper;
import com.yyatsiuk.api.core.repository.CustomerRepository;
import com.yyatsiuk.api.core.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private static final String NULL_CUSTOMER_ERROR_MESSAGE = "Customer DTO cannot be null";

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final OrderMapper orderMapper;

    @Override
    public Long save(CustomerDto customerDto) {
        Assert.notNull(customerDto, NULL_CUSTOMER_ERROR_MESSAGE);

        Customer customer = customerMapper.fromDtoToEntity(customerDto);
        return customerRepository.save(customer).getId();
    }

    @Override
    public CustomerDto update(CustomerDto customerDto) {
        Assert.notNull(customerDto, NULL_CUSTOMER_ERROR_MESSAGE);

        Customer customer = customerMapper.fromDtoToEntity(customerDto);
        return customerMapper.fromEntityToDto(customerRepository.save(customer));
    }

    @Override
    public CustomerDto findById(Long id) {
        return customerRepository.findById(id)
                .map(customerMapper::fromEntityToDto)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: {0} not found", id));
    }

    @Override
    public List<CustomerDto> findAll() {
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customerMapper::fromEntityToDto).toList();
    }

    @Override
    public void delete(Long id) {
        customerRepository.deleteById(id);
    }

    @Override
    public List<OrderDto> findAllOrders(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: {0} not found", id));
        List<Order> orders = customer.getOrders();


        return orders.stream().map(orderMapper::fromEntityToDto).toList();
    }

}
