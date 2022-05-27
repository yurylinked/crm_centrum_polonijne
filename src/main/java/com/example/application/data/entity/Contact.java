package com.example.application.data.entity;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.example.application.data.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Contact extends AbstractEntity {

    @NotEmpty
    private String firstName = "";

    @NotEmpty
    private String lastName = "";

    @NotEmpty
    private String phone = "";

    @Override
    public String toString() {
        return "Contact{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", coursePrice=" + coursePrice +
                ", course=" + course +
                ", payment=" + payment +
                ", groupOfStudents=" + groupOfStudents +
                '}';
    }

    @NotNull
    private Double coursePrice;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    private Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "payment_id")
    @JsonIgnoreProperties({"students"})//adlistener   //
    private Payment payment=new Payment();//отображение внесеннной суммы тк payment это webhook payment.amount

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "groupOfStudents_id")
    @JsonIgnoreProperties({"students"})
    private GroupOfStudents groupOfStudents= new GroupOfStudents();//2  поля groupOfStudents.name=Ш 203
    // groupOfStudents.getCourse("Карта Поляка")

 /*   public boolean whenMatchesTenDigitsNumberParenthesis_thenCorrect(String phone) {
        Pattern pattern = Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }*/

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

    public Double getCoursePrice() {
        return coursePrice;
    }

    public void setCoursePrice(Double coursePrice) {
        this.coursePrice = coursePrice;
    }

/*    public Double getPaymentFromAdmin() {//отсюда надо достать int amount
        return paymentFromAdmin;
    }

    public void setPaymentFromAdmin(Double paymentFromAdmin) {
        this.paymentFromAdmin = paymentFromAdmin;
    }*/

    public Payment getPayment() {
        return payment;
    }

    public Double getPaymentAmount() {
       return this.payment.getAmount();

    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public void setPaymentAmount(Double paymentAmount) {

        this.payment.setAmount(paymentAmount);
    }

    public GroupOfStudents getGroupOfStudents() {
        return groupOfStudents;
    }

    public void setGroupOfStudents(GroupOfStudents groupOfStudents) {
        this.groupOfStudents = groupOfStudents;
    }
}
