package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import com.example.application.data.AbstractEntity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
public class GroupOfStudents extends AbstractEntity {

    @NotBlank
    private String name = "";

    @Transient
    private List<String> course=new ArrayList<>();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;
    // LocalDate date = LocalDate.ofInstant(input.toInstant(), ZoneId.systemDefault());

    @NotBlank
    private String teacher = "";

    @OneToMany(mappedBy = "groupOfStudents")
    private List<Contact> students = new LinkedList<>();

    @OneToMany(mappedBy = "groupOfStudents")
    private List<Payment> payments = new LinkedList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }
    public List<Contact> getStudents() {;
        return students;
    }

    public void setStudents(List<Contact> students) {
        this.students = students;
    }

    public List<String> getCourse() {
        List<String> course = setCourseAllGroup();
        return course;
    }

    public void setCourse(List<String> course) {
        this.course = course;
    }

    public List<String> setCourseAllGroup() {
        course.add("Минск - Курс с 0 до А1");
        course.add("Минск - Курс с 0 до А2");
        course.add("Минск - Курс с А1 до В1");
        course.add("Минск - Курс с А2 до В1");
        course.add("Минск - Курс с В1 до В2");
        course.add("Минск - Интенсивный курс с 0 до В1");
        course.add("Минск - Интенсивный курс с А2 до В2");
        course.add("Минск - Курс для Карты Поляка");
        course.add("Минск - Дошколята");
        course.add("Минск - 1-2 класс");
        course.add("Минск - 3-5 класс");
        course.add("Минск - 6-8 класс");
        course.add("Онлайн - Общий курс с 0 до А1");
        course.add("Онлайн - Общий курс с 0 до А2");
        course.add("Онлайн - Общий курс с А2 до В1");
        course.add("Онлайн - Общий курс с В2 до C1");
        course.add("Онлайн - интенсив с А2 до В2");
        course.add("Онлайн - интенсив с 0 до В1");
        course.add("Онлайн - Детские группы");
        course.add("Онлайн - курс После Карты Поляка");
        course.add("Онлайн - курс для Карты Поляка");
        course.add("Гродно - Интенсив с  0 до В1");
        course.add("Гродно - Курс с В1 до В2");
        course.add("Гродно - Курс с А2 до В1");
        course.add("Гродно - Курс с 0 до А2");
        course.add("Гродно - Курс с 0 до А1");
        course.add("Гродно - 6-8 класс");
        course.add("Медицинский Польский");
        course.add("Информационные услуги");
        course.add("Летний лагерь");
        return course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        // LocalDate newDate = LocalDate.ofInstant(date.toInstant(), ZoneId.systemDefault());
        this.date = date;
    }

}
