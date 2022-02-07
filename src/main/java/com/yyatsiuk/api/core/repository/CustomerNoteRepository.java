package com.yyatsiuk.api.core.repository;

import com.yyatsiuk.api.core.models.entities.CustomerNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CustomerNoteRepository extends JpaRepository<CustomerNote, Long> {

    @Modifying
    @Query("delete from CustomerNote cn where cn.customer.id = :customerId and cn.id = :noteId")
    void deleteByNoteIdAndCustomerId(Long customerId, Long noteId);

}
