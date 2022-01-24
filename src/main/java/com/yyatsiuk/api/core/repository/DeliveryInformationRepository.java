package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.models.entities.DeliveryInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryInformationRepository extends JpaRepository<DeliveryInformation, Long> {
}
