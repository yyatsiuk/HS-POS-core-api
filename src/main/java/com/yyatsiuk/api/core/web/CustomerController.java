package com.yyatsiuk.api.core.web;

import com.yyatsiuk.api.core.dto.CustomerDto;
import com.yyatsiuk.api.core.mappers.CustomerMapper;
import com.yyatsiuk.api.core.service.CustomerService;
import com.yyatsiuk.api.core.web.request.CustomerCreateUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
public class CustomerController {

    private final CustomerMapper customerMapper;
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<Void> createCustomer(@RequestBody CustomerCreateUpdateRequest payload, HttpServletRequest req) {
        Long id = customerService.save(customerMapper.fromCreateUpdateRequestToDto(payload));
        return ResponseEntity.created(URI.create(req.getRequestURL()
                        .append("/")
                        .append(id)
                        .toString()))
                .build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@RequestBody CustomerCreateUpdateRequest payload,
                                                      @PathVariable Long id) {

        CustomerDto dto = customerMapper.fromCreateUpdateRequestToDto(payload, id);
        return ResponseEntity.ok(customerService.update(dto));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDto>> getAllCustomers() {
        return ResponseEntity.ok(customerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> getCukstomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.findById(id));
    }

}
