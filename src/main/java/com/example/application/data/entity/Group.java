package com.example.application.data.entity;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import com.example.application.data.AbstractEntity;

import java.util.LinkedList;
import java.util.List;

@Entity
public class Group extends AbstractEntity {

    @NotBlank
    private String name = "";

    @NotBlank
    private Enum course;

    @NotBlank
    private String teacher="";

    @OneToMany(mappedBy = "group")
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

    public Enum getCourse() {
        return course;
    }

    public void setCourse(Enum course) {
        this.course = course;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }
}
