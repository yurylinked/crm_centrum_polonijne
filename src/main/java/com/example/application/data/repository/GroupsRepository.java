package com.example.application.data.repository;

import com.example.application.data.entity.Group;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GroupsRepository extends JpaRepository<Group, Integer> {

}
