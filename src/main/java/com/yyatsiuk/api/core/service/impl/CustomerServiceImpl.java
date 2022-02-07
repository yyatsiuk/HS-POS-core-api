package com.yyatsiuk.api.core.service.impl;

import com.yyatsiuk.api.core.exceptions.EntityNotFoundException;
import com.yyatsiuk.api.core.models.dto.CustomerDto;
import com.yyatsiuk.api.core.models.dto.CustomerNoteDto;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.entities.Customer;
import com.yyatsiuk.api.core.models.entities.CustomerNote;
import com.yyatsiuk.api.core.models.entities.Order;
import com.yyatsiuk.api.core.models.entities.User;
import com.yyatsiuk.api.core.models.mappers.CustomerMapper;
import com.yyatsiuk.api.core.models.mappers.OrderMapper;
import com.yyatsiuk.api.core.repository.CustomerNoteRepository;
import com.yyatsiuk.api.core.repository.CustomerRepository;
import com.yyatsiuk.api.core.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private static final String NULL_CUSTOMER_ERROR_MESSAGE = "Customer DTO cannot be null";

    private final CustomerRepository customerRepository;
    private final CustomerNoteRepository customerNoteRepository;
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

    @Override
    public List<CustomerNoteDto> findCustomerNotesById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: {0} not found", customerId));

        List<CustomerNote> notes = customer.getNotes();
        return notes.stream().map(this::toCustomerNoteDto).collect(Collectors.toList());
    }

    @Override
    public CustomerNoteDto createCustomerNote(Long customerId, Long userId, String note) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id: {0} not found", customerId));

        CustomerNote customerNote = new CustomerNote();
        customerNote.setCustomer(customer);
        customerNote.setContent(note);

        User user = new User();
        user.setId(userId);

        customerNote.setUser(user);

        List<CustomerNote> notes = customer.getNotes();
        notes.add(customerNote);

        customerRepository.save(customer);
        CustomerNote lastNote = customer.getNotes().get(customer.getNotes().size() - 1);
        return toCustomerNoteDto(lastNote);
    }

    @Override
    public void deleteCustomerNote(Long customerId, Long noteId) {
        customerNoteRepository.deleteByNoteIdAndCustomerId(customerId, noteId);
    }

    private CustomerNoteDto toCustomerNoteDto(CustomerNote customerNote) {
        CustomerNoteDto customerNoteDto = new CustomerNoteDto();
        customerNoteDto.setContent(customerNote.getContent());
        customerNoteDto.setId(customerNote.getId());
        customerNoteDto.setSenderAvatar(customerNote.getUser().getAvatarUrl());
        customerNoteDto.setSenderName(customerNote.getUser().getFullName());
        customerNoteDto.setSenderId(customerNote.getUser().getId());
        customerNoteDto.setCreatedAt(customerNote.getCreatedAt());

        return customerNoteDto;
    }

}
