package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.models.entities.Order;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "deliveryInformation", "deliveryInformation.courier", "customer", "items", "items.product"
    })
    List<Order> findAll();

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, attributePaths = {
            "deliveryInformation", "deliveryInformation.courier", "customer", "items", "items.product"
    })
    Optional<Order> findById(Long id);

}
