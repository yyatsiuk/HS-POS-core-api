package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.models.entities.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

//    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
//            "orders.deliveryInformation", "orders.deliveryInformation.courier", "orders.items"
//    })
//    Optional<Customer> findById(Long id);

}
