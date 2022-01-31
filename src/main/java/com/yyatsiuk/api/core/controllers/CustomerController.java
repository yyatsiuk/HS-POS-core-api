package com.yyatsiuk.api.core.controllers;

import com.yyatsiuk.api.core.models.dto.CustomerDto;
import com.yyatsiuk.api.core.models.dto.CustomerNoteDto;
import com.yyatsiuk.api.core.models.dto.OrderDto;
import com.yyatsiuk.api.core.models.mappers.CustomerMapper;
import com.yyatsiuk.api.core.models.request.CustomerCreateRequest;
import com.yyatsiuk.api.core.models.request.CustomerUpdateRequest;
import com.yyatsiuk.api.core.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateRequest payload, HttpServletRequest req) {
        Long id = customerService.save(customerMapper.fromCreateRequestToDto(payload));
        return ResponseEntity.created(URI.create(req.getRequestURL()
                        .append("/")
                        .append(id)
                        .toString()))
                .build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerUpdateRequest payload,
                                                      @PathVariable Long id) {

        CustomerDto dto = customerMapper.fromUpdateRequestToDto(payload, id);
        return ResponseEntity.ok(customerService.update(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/orders")
    public ResponseEntity<List<OrderDto>> getCustomerOrders(@PathVariable Long id) {
        List<OrderDto> orders = customerService.findAllOrders(id);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<List<CustomerNoteDto>> getCustomerNotes() {
        return ResponseEntity.ok(List.of(new CustomerNoteDto()));
    }

}
