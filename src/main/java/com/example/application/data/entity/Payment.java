package com.example.application.data.entity;

import java.util.LinkedList;
import java.util.List;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

    public Payment (String firstName, String lastName, String phone, String course, String amount) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.course = course;
        this.amount = amount;
    }

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotEmpty
    private String phone = "";

    @NotEmpty
    private String course = "";

    @NotEmpty
    private String amount = "";

    @OneToMany(mappedBy = "payment")
    @JsonIgnoreProperties(value = "students")
    private List<Contact> students = new LinkedList<>();

    @ManyToOne
    @JoinColumn(name = "groupOfStudent_id")
    @JsonIgnoreProperties(value = "groupOfStudents")
    private GroupOfStudents groupOfStudents;

    @Override
    public String toString() {
        return "Payment{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", course='" + course + '\'' +
                ", amount='" + amount + '\'' +
                ", students=" + students +
                ", groupOfStudents=" + groupOfStudents +
                '}';
    }

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public GroupOfStudents getGroupOfStudents() {
        return groupOfStudents;
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
    }
}
