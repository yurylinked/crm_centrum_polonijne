package com.example.application.data.repository;

import com.example.application.data.entity.Contact;
import com.example.application.data.entity.GroupOfStudents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GroupsRepository extends JpaRepository<GroupOfStudents, Integer> {
    @Query("select c from GroupOfStudents c " +
            "where lower(c.name) like lower(concat('%', :searchTerm, '%')) " +
            "or lower(c.teacher) like lower(concat('%', :searchTerm, '%'))")
    List<GroupOfStudents> search(@Param("searchTerm") String searchTerm);
}

