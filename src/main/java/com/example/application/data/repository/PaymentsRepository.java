package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;

@Component
public interface PaymentsRepository extends JpaRepository<Payment, Integer> {
    @Query("select c from Payment c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")
    List<Payment> search(@Param("searchTerm") String searchTerm);



  /*  @Query("select c from Payment c " +
            "where lower(c.firstName) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.lastName) like lower(concat('%', :searchTerm, '%'))")*/

   List <Payment> findByPhoneAndLastNameAndFirstName(String phone, String lastName, String firstName);





    @Query("select c from Contact c " +
            "where (c.phone) like (concat('%', :searchTerm, '%')) ")
    Contact searchPayFromContact(@Param("searchTerm") String searchTerm);
}
