package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PaymentsRepository extends JpaRepository<Payment, Integer> {
    @Query("select c from Payment c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Payment> search(@Param("searchTerm") String searchTerm);
}
