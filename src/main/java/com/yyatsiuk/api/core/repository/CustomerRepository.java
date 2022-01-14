package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Long, Customer> {

}
