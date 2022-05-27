package com.example.application.data.entity;

import javax.persistence.*;




import com.example.application.data.AbstractEntity;

import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;


@Entity
public class GroupOfStudents extends AbstractEntity implements CourseInterface {
    public GroupOfStudents() {
    }

    public GroupOfStudents(String name) {
        this.name = name;
    }

    //@NotBlank
    private String name = "";

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)//MERGE
    private Course course=new Course();

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate date;

    //@NotBlank
    private String teacher = "";

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "groupOfStudents_id")
    private List<Contact> students = new LinkedList<>();

    @OneToMany(mappedBy = "groupOfStudents")
    private List<Payment> payments = new LinkedList<>();

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments;
    }

    public List<Contact> getStudents() {
        return students;
    }

    public void setStudents(List<Contact> students) {
        this.students = students;
    }
    public void setCourse(Course course) {
        this.course = course;
    }

    /*@Transactional*/
    public Course getCourse() {// почему он null?
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
        this.date = date;
    }


    public static String getNameOfGroup(Contact contact) {
        return contact.getGroupOfStudents().name;
    }

    public static void setNameOfGroup(Contact contact, String s) {
        String nameOfGroup = GroupOfStudents.getNameOfGroup(contact);
        nameOfGroup=s;
    }
}
