package com.example.application.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.example.application.data.AbstractEntity;
import org.hibernate.annotations.Formula;

@Entity
public class Payment extends AbstractEntity {
    @NotBlank
    private String name;


    @OneToMany(mappedBy = "paymentFromData")
    @Nullable
    private List<Contact> students = new LinkedList<>();



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Contact> getStudents() {
        return students;
    }

    public void setStudents(List<Contact> students) {
        this.students = students;
    }


}
