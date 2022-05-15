package com.example.application.data.repository;

import com.example.application.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentsRepository extends JpaRepository<Payment, Integer> {

}
