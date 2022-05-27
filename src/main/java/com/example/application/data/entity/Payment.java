package com.example.application.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.example.application.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.shared.Registration;
import org.hibernate.annotations.Formula;

@Entity
public class Payment extends AbstractEntity {
    public Payment() {
    }

    public Payment(String firstName, String lastName, String phone, String course, Double amount) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.course = course;
        this.amount = amount;
    }


    private String firstName = "";


    private String lastName = "";


    private String phone = "";

    @Override
    public String toString() {
        return "Payment{" +
                "amount=" + amount +
                '}';
    }

    private String course = "";


    private Double amount=0.0;

    @OneToMany(mappedBy = "payment", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties(value = "students")
    private List<Contact> students = new LinkedList<>();

    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinColumn(name = "groupOfStudent_id")
    @JsonIgnoreProperties(value = "groupOfStudents")
    private GroupOfStudents groupOfStudents;


    public List<Contact> getStudents() {
        return students;
    }

    public void setStudents(List<Contact> students) {
        this.students = students;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public GroupOfStudents getGroupOfStudents() {
        return groupOfStudents;
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
    }
}
