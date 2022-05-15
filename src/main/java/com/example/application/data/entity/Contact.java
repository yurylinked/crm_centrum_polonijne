package com.example.application.data.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.example.application.data.AbstractEntity;

@Entity
public class Contact extends AbstractEntity {

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotEmpty
    private String phone = "";

    @NotEmpty
    private int coursePrice;

    private String payment;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "paymentFromData_id")
    @NotNull
   // @JsonIgnoreProperties({"students"})
    private Payment paymentFromData;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_id")
    @NotNull
   // @JsonIgnoreProperties({"students"})
    private Group group;

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", coursePrice=" + coursePrice +
                '}';
    }
    public Payment getPaymentFromData() {
        return paymentFromData;
    }

    public void setPaymentFromData(Payment paymentFromData) {
        this.paymentFromData = paymentFromData;
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

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(int coursePrice) {
        this.coursePrice = coursePrice;
    }

    public String getPayment() {//отсюда надо достать int amount
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }


}
