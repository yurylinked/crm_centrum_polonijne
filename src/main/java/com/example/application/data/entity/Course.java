package com.example.application.data.entity;

import com.example.application.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Course extends AbstractEntity {
    private String name;

    public String getName() {
        return name;
    }

    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "groupOfStudentsList_id")
    private List<GroupOfStudents> groupOfStudentsList = new ArrayList<>();


    @OneToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "contact_id")
    private List<Contact> contacts = new ArrayList<>();

    public void setName(String name) {
        this.name = name;
    }

    public Course(String name) {
        this.name = name;
    }

    public Course() {

    }
}
