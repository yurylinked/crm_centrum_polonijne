package com.example.application.data.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.application.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
public class Contact extends AbstractEntity {

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotEmpty
    private String phone = "";

   // @NotEmpty
    private String coursePrice = "";

    private String paymentFromAdmin;

    public Contact() {
    }

    public Contact(String firstName, String lastName, String phone, String paymentFromAdmin) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.paymentFromAdmin = paymentFromAdmin;
    }

    @ManyToOne
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({"students"})
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "groupOfStudents_id")
    @JsonIgnoreProperties({"students"})
    private GroupOfStudents groupOfStudents;

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", coursePrice=" + coursePrice +
                '}';
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

    public GroupOfStudents getGroup() {
        return groupOfStudents;
    }

    public void setGroup(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(String coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getPaymentFromAdmin() {//отсюда надо достать int amount
        return paymentFromAdmin;
    }

    public void setPaymentFromAdmin(String payment) {
        this.paymentFromAdmin = payment;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public GroupOfStudents getGroupOfStudents() {
        return groupOfStudents;
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
    }
}
